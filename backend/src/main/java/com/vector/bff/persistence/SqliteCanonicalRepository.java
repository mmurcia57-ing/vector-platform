package com.vector.bff.persistence;

import com.vector.bff.canonical.CanonicalEntity;
import com.vector.bff.canonical.CanonicalEntityCatalog;
import com.vector.bff.canonical.CanonicalSerializer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Local SQLite persistence adapter for canonical records; it does not project graph relationships. */
public final class SqliteCanonicalRepository implements CanonicalRepository {
	private final Connection connection;

	public SqliteCanonicalRepository(String jdbcUrl) {
		try {
			connection = DriverManager.getConnection(requireJdbcUrl(jdbcUrl));
			initialize();
		}
		catch (SQLException exception) {
			throw new IllegalStateException("Cannot open canonical SQLite repository", exception);
		}
	}

	@Override
	public CanonicalRecord save(CanonicalEntity entity) {
		if (!CanonicalEntityCatalog.v1Types().contains(entity.canonicalType())) {
			throw new IllegalArgumentException("Only approved V1 canonical entities may be persisted");
		}
		try (var statement = connection.prepareStatement("""
			INSERT INTO canonical_record
			(canonical_type, canonical_id, payload, identity_state, occurred_at, observed_at, ingested_at,
			 effective_from, effective_to, source_reference_ids, authority_reference, authority_explicit)
			VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
			ON CONFLICT(canonical_type, canonical_id) DO UPDATE SET
			payload=excluded.payload, identity_state=excluded.identity_state, occurred_at=excluded.occurred_at,
			observed_at=excluded.observed_at, ingested_at=excluded.ingested_at, effective_from=excluded.effective_from,
			effective_to=excluded.effective_to, source_reference_ids=excluded.source_reference_ids,
			authority_reference=excluded.authority_reference, authority_explicit=excluded.authority_explicit
			""")) {
			var metadata = entity.metadata();
			var temporal = metadata.temporal();
			statement.setString(1, entity.canonicalType());
			statement.setString(2, metadata.canonicalId());
			statement.setString(3, CanonicalSerializer.serialize(entity));
			statement.setString(4, metadata.identityState().name());
			setInstant(statement, 5, temporal.occurredAt());
			setInstant(statement, 6, temporal.observedAt());
			setInstant(statement, 7, temporal.ingestedAt());
			setInstant(statement, 8, temporal.effectiveFrom());
			setInstant(statement, 9, temporal.effectiveTo());
			statement.setString(10, String.join(",", metadata.provenance().sourceReferenceIds()));
			statement.setString(11, metadata.sourceAuthority().authorityReference());
			statement.setBoolean(12, metadata.sourceAuthority().explicitlyDeclared());
			statement.executeUpdate();
			return find(entity.canonicalType(), metadata.canonicalId()).orElseThrow();
		}
		catch (SQLException exception) {
			throw new IllegalStateException("Cannot save canonical record", exception);
		}
	}

	@Override
	public Optional<CanonicalRecord> find(String canonicalType, String canonicalId) {
		try (var statement = connection.prepareStatement("SELECT * FROM canonical_record WHERE canonical_type = ? AND canonical_id = ?")) {
			statement.setString(1, canonicalType);
			statement.setString(2, canonicalId);
			try (var result = statement.executeQuery()) {
				return result.next() ? Optional.of(read(result)) : Optional.empty();
			}
		}
		catch (SQLException exception) {
			throw new IllegalStateException("Cannot read canonical record", exception);
		}
	}

	@Override
	public List<CanonicalRecord> findAll(String canonicalType) {
		try (var statement = connection.prepareStatement("SELECT * FROM canonical_record WHERE canonical_type = ? ORDER BY canonical_id")) {
			statement.setString(1, canonicalType);
			try (var result = statement.executeQuery()) {
				var records = new ArrayList<CanonicalRecord>();
				while (result.next()) records.add(read(result));
				return List.copyOf(records);
			}
		}
		catch (SQLException exception) {
			throw new IllegalStateException("Cannot list canonical records", exception);
		}
	}

	@Override
	public void close() {
		try {
			connection.close();
		}
		catch (SQLException exception) {
			throw new IllegalStateException("Cannot close canonical SQLite repository", exception);
		}
	}

	private void initialize() throws SQLException {
		try (var statement = connection.createStatement()) {
			statement.executeUpdate("""
			CREATE TABLE IF NOT EXISTS canonical_record (
				canonical_type TEXT NOT NULL,
				canonical_id TEXT NOT NULL,
				payload TEXT NOT NULL,
				identity_state TEXT NOT NULL,
				occurred_at TEXT,
				observed_at TEXT,
				ingested_at TEXT,
				effective_from TEXT,
				effective_to TEXT,
				source_reference_ids TEXT NOT NULL,
				authority_reference TEXT,
				authority_explicit INTEGER NOT NULL,
				PRIMARY KEY (canonical_type, canonical_id)
			)
			""");
		}
	}

	private static CanonicalRecord read(ResultSet result) throws SQLException {
		return new CanonicalRecord(result.getString("canonical_type"), result.getString("canonical_id"),
			result.getString("payload"), result.getString("identity_state"), instant(result, "occurred_at"),
			instant(result, "observed_at"), instant(result, "ingested_at"), instant(result, "effective_from"),
			instant(result, "effective_to"), result.getString("source_reference_ids"),
			result.getString("authority_reference"), result.getBoolean("authority_explicit"));
	}

	private static void setInstant(java.sql.PreparedStatement statement, int index, Instant value) throws SQLException {
		if (value == null) statement.setNull(index, java.sql.Types.VARCHAR);
		else statement.setString(index, value.toString());
	}

	private static Instant instant(ResultSet result, String column) throws SQLException {
		var value = result.getString(column);
		return value == null ? null : Instant.parse(value);
	}

	private static String requireJdbcUrl(String jdbcUrl) {
		if (jdbcUrl == null || jdbcUrl.isBlank() || !jdbcUrl.startsWith("jdbc:sqlite:")) {
			throw new IllegalArgumentException("A jdbc:sqlite URL is required");
		}
		return jdbcUrl;
	}
}
