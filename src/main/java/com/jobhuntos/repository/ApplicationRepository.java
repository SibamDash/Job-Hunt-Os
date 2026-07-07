package com.jobhuntos.repository;
import com.jobhuntos.model.Application;
import com.jobhuntos.model.enums.ApplicationStatus;
import com.jobhuntos.model.enums.Priority;
import com.jobhuntos.database.DatabaseManager;
import com.jobhuntos.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApplicationRepository implements BaseRepository<Application, Long> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRepository.class);

    @Override
    public Application save(Application app) {
        String sql = "INSERT INTO Applications (company_id, role, salary, location, portal, apply_date, status, priority, deadline, resume_id, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(pstmt, app);
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    app.setId(generatedKeys.getLong(1));
                }
            }
            logger.info("Inserted Application: {}", app.getId());
            return app;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to save application", e);
        }
    }

    @Override
    public boolean update(Application app) {
        String sql = "UPDATE Applications SET company_id = ?, role = ?, salary = ?, location = ?, portal = ?, apply_date = ?, status = ?, priority = ?, deadline = ?, resume_id = ?, notes = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParameters(pstmt, app);
            pstmt.setLong(12, app.getId());
            int affected = pstmt.executeUpdate();
            logger.info("Updated Application ID {}: {}", app.getId(), affected > 0);
            return affected > 0;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to update application", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM Applications WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to delete application", e);
        }
    }

    @Override
    public Optional<Application> findById(Long id) {
        String sql = "SELECT * FROM Applications WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to find application by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Application> findAll(int limit, int offset) {
        return queryList("SELECT * FROM Applications ORDER BY updated_at DESC LIMIT ? OFFSET ?", limit, offset);
    }

    @Override
    public boolean exists(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public long count() {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Applications")) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new RepositoryException("Failed to count applications", e);
        }
        return 0;
    }
    
    // Custom Searches
    public List<Application> searchByCompany(Long companyId, int limit, int offset) {
        String sql = "SELECT * FROM Applications WHERE company_id = ? ORDER BY apply_date DESC LIMIT ? OFFSET ?";
        List<Application> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, companyId);
            pstmt.setInt(2, limit);
            pstmt.setInt(3, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to search applications by company", e);
        }
        return list;
    }

    public List<Application> searchByStatus(ApplicationStatus status, int limit, int offset) {
        String sql = "SELECT * FROM Applications WHERE status = ? ORDER BY apply_date DESC LIMIT ? OFFSET ?";
        List<Application> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.name());
            pstmt.setInt(2, limit);
            pstmt.setInt(3, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to search applications by status", e);
        }
        return list;
    }

    private void setParameters(PreparedStatement pstmt, Application app) throws SQLException {
        pstmt.setLong(1, app.getCompanyId());
        pstmt.setString(2, app.getRole());
        pstmt.setString(3, app.getSalary());
        pstmt.setString(4, app.getLocation());
        pstmt.setString(5, app.getPortal());
        pstmt.setString(6, app.getApplyDate() != null ? app.getApplyDate().toString() : null);
        pstmt.setString(7, app.getStatus() != null ? app.getStatus().name() : null);
        pstmt.setString(8, app.getPriority() != null ? app.getPriority().name() : null);
        pstmt.setString(9, app.getDeadline() != null ? app.getDeadline().toString() : null);
        if (app.getResumeId() != null) {
            pstmt.setLong(10, app.getResumeId());
        } else {
            pstmt.setNull(10, Types.INTEGER);
        }
        pstmt.setString(11, app.getNotes());
    }

    private Application mapRow(ResultSet rs) throws SQLException {
        Application app = new Application();
        app.setId(rs.getLong("id"));
        app.setCompanyId(rs.getLong("company_id"));
        app.setRole(rs.getString("role"));
        app.setSalary(rs.getString("salary"));
        app.setLocation(rs.getString("location"));
        app.setPortal(rs.getString("portal"));
        
        String applyDate = rs.getString("apply_date");
        if (applyDate != null && !applyDate.isEmpty()) app.setApplyDate(LocalDate.parse(applyDate));
        
        String status = rs.getString("status");
        if (status != null && !status.isEmpty()) app.setStatus(ApplicationStatus.valueOf(status));
        
        String priority = rs.getString("priority");
        if (priority != null && !priority.isEmpty()) app.setPriority(Priority.valueOf(priority));
        
        String deadline = rs.getString("deadline");
        if (deadline != null && !deadline.isEmpty()) app.setDeadline(LocalDate.parse(deadline));
        
        long resumeId = rs.getLong("resume_id");
        if (!rs.wasNull()) app.setResumeId(resumeId);
        
        app.setNotes(rs.getString("notes"));
        return app;
    }
    
    private List<Application> queryList(String sql, int limit, int offset) {
        List<Application> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to query applications", e);
        }
        return list;
    }
}
