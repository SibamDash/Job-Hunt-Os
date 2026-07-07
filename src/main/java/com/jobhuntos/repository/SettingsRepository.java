package com.jobhuntos.repository;
import com.jobhuntos.model.Settings;
import com.jobhuntos.database.DatabaseManager;
import com.jobhuntos.exception.RepositoryException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SettingsRepository implements BaseRepository<Settings, String> {
    @Override
    public Settings save(Settings s) {
        String sql = "INSERT OR REPLACE INTO Settings (key, value) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, s.getKey());
            pstmt.setString(2, s.getValue());
            pstmt.executeUpdate();
            return s;
        } catch (SQLException e) { throw new RepositoryException("Save failed", e); }
    }

    @Override
    public boolean update(Settings s) { return save(s) != null; }

    @Override
    public boolean delete(String id) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Settings WHERE key=?")) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { throw new RepositoryException("Delete failed", e); }
    }

    @Override
    public Optional<Settings> findById(String id) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Settings WHERE key=?")) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Settings s = new Settings();
                    s.setKey(rs.getString("key"));
                    s.setValue(rs.getString("value"));
                    return Optional.of(s);
                }
            }
        } catch (SQLException e) { throw new RepositoryException("Find failed", e); }
        return Optional.empty();
    }

    @Override
    public List<Settings> findAll(int limit, int offset) {
        List<Settings> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Settings LIMIT ? OFFSET ?")) {
            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Settings s = new Settings();
                    s.setKey(rs.getString("key"));
                    s.setValue(rs.getString("value"));
                    list.add(s);
                }
            }
        } catch (SQLException e) { throw new RepositoryException("Query failed", e); }
        return list;
    }

    @Override
    public boolean exists(String id) { return findById(id).isPresent(); }

    @Override
    public long count() {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Settings")) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) { throw new RepositoryException("Count failed", e); }
        return 0;
    }
}
