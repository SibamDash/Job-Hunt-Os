package com.jobhuntos.repository;
import com.jobhuntos.model.Resume;
import com.jobhuntos.model.enums.ResumeType;
import com.jobhuntos.database.DatabaseManager;
import com.jobhuntos.exception.RepositoryException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResumeRepository implements BaseRepository<Resume, Long> {
    @Override
    public Resume save(Resume r) {
        String sql = "INSERT INTO Resumes (name, path, type) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, r.getName());
            pstmt.setString(2, r.getPath());
            pstmt.setString(3, r.getType() != null ? r.getType().name() : null);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) { if (rs.next()) r.setId(rs.getLong(1)); }
            return r;
        } catch (SQLException e) { throw new RepositoryException("Save failed", e); }
    }

    @Override
    public boolean update(Resume r) {
        String sql = "UPDATE Resumes SET name=?, path=?, type=? WHERE id=?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, r.getName());
            pstmt.setString(2, r.getPath());
            pstmt.setString(3, r.getType() != null ? r.getType().name() : null);
            pstmt.setLong(4, r.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { throw new RepositoryException("Update failed", e); }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Resumes WHERE id=?")) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { throw new RepositoryException("Delete failed", e); }
    }

    @Override
    public Optional<Resume> findById(Long id) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Resumes WHERE id=?")) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) { if (rs.next()) return Optional.of(mapRow(rs)); }
        } catch (SQLException e) { throw new RepositoryException("Find failed", e); }
        return Optional.empty();
    }

    @Override
    public List<Resume> findAll(int limit, int offset) {
        List<Resume> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Resumes ORDER BY created_at DESC LIMIT ? OFFSET ?")) {
            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);
            try (ResultSet rs = pstmt.executeQuery()) { while (rs.next()) list.add(mapRow(rs)); }
        } catch (SQLException e) { throw new RepositoryException("Query failed", e); }
        return list;
    }

    @Override
    public boolean exists(Long id) { return findById(id).isPresent(); }

    @Override
    public long count() {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Resumes")) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) { throw new RepositoryException("Count failed", e); }
        return 0;
    }

    private Resume mapRow(ResultSet rs) throws SQLException {
        Resume r = new Resume();
        r.setId(rs.getLong("id"));
        r.setName(rs.getString("name"));
        r.setPath(rs.getString("path"));
        String type = rs.getString("type");
        if (type != null && !type.isEmpty()) r.setType(ResumeType.valueOf(type));
        return r;
    }
}
