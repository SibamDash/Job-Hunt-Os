package com.jobhuntos.repository;
import com.jobhuntos.model.Interview;
import com.jobhuntos.model.enums.InterviewType;
import com.jobhuntos.model.enums.InterviewResult;
import com.jobhuntos.database.DatabaseManager;
import com.jobhuntos.exception.RepositoryException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InterviewRepository implements BaseRepository<Interview, Long> {
    @Override
    public Interview save(Interview i) {
        String sql = "INSERT INTO Interviews (application_id, type, date, result, feedback, questions, mistakes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(pstmt, i);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) { if (rs.next()) i.setId(rs.getLong(1)); }
            return i;
        } catch (SQLException e) { throw new RepositoryException("Save failed", e); }
    }

    @Override
    public boolean update(Interview i) {
        String sql = "UPDATE Interviews SET application_id=?, type=?, date=?, result=?, feedback=?, questions=?, mistakes=? WHERE id=?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParameters(pstmt, i);
            pstmt.setLong(8, i.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { throw new RepositoryException("Update failed", e); }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Interviews WHERE id=?")) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { throw new RepositoryException("Delete failed", e); }
    }

    @Override
    public Optional<Interview> findById(Long id) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Interviews WHERE id=?")) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) { if (rs.next()) return Optional.of(mapRow(rs)); }
        } catch (SQLException e) { throw new RepositoryException("Find failed", e); }
        return Optional.empty();
    }

    @Override
    public List<Interview> findAll(int limit, int offset) {
        return queryList("SELECT * FROM Interviews ORDER BY date DESC LIMIT ? OFFSET ?", limit, offset);
    }
    
    public List<Interview> searchByResult(InterviewResult result, int limit, int offset) {
        String sql = "SELECT * FROM Interviews WHERE result=? ORDER BY date DESC LIMIT ? OFFSET ?";
        List<Interview> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, result.name());
            pstmt.setInt(2, limit);
            pstmt.setInt(3, offset);
            try (ResultSet rs = pstmt.executeQuery()) { while (rs.next()) list.add(mapRow(rs)); }
        } catch (SQLException e) { throw new RepositoryException("Search failed", e); }
        return list;
    }

    @Override
    public boolean exists(Long id) { return findById(id).isPresent(); }

    @Override
    public long count() {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Interviews")) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) { throw new RepositoryException("Count failed", e); }
        return 0;
    }

    private void setParameters(PreparedStatement pstmt, Interview i) throws SQLException {
        pstmt.setLong(1, i.getApplicationId());
        pstmt.setString(2, i.getType() != null ? i.getType().name() : null);
        pstmt.setString(3, i.getDate() != null ? i.getDate().toString() : null);
        pstmt.setString(4, i.getResult() != null ? i.getResult().name() : null);
        pstmt.setString(5, i.getFeedback());
        pstmt.setString(6, i.getQuestions());
        pstmt.setString(7, i.getMistakes());
    }

    private Interview mapRow(ResultSet rs) throws SQLException {
        Interview i = new Interview();
        i.setId(rs.getLong("id"));
        i.setApplicationId(rs.getLong("application_id"));
        String type = rs.getString("type");
        if (type != null && !type.isEmpty()) i.setType(InterviewType.valueOf(type));
        String date = rs.getString("date");
        if (date != null && !date.isEmpty()) i.setDate(LocalDateTime.parse(date));
        String result = rs.getString("result");
        if (result != null && !result.isEmpty()) i.setResult(InterviewResult.valueOf(result));
        i.setFeedback(rs.getString("feedback"));
        i.setQuestions(rs.getString("questions"));
        i.setMistakes(rs.getString("mistakes"));
        return i;
    }
    
    private List<Interview> queryList(String sql, int limit, int offset) {
        List<Interview> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);
            try (ResultSet rs = pstmt.executeQuery()) { while (rs.next()) list.add(mapRow(rs)); }
        } catch (SQLException e) { throw new RepositoryException("Query failed", e); }
        return list;
    }
}
