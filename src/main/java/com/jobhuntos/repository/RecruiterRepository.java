package com.jobhuntos.repository;
import com.jobhuntos.model.Recruiter;
import com.jobhuntos.model.enums.ConnectionStatus;
import com.jobhuntos.database.DatabaseManager;
import com.jobhuntos.exception.RepositoryException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecruiterRepository implements BaseRepository<Recruiter, Long> {
    @Override
    public Recruiter save(Recruiter r) {
        String sql = "INSERT INTO Recruiters (company_id, name, linkedin, email, phone, connection_status, last_contact, follow_up_date, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(pstmt, r);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) r.setId(rs.getLong(1));
            }
            return r;
        } catch (SQLException e) { throw new RepositoryException("Save failed", e); }
    }

    @Override
    public boolean update(Recruiter r) {
        String sql = "UPDATE Recruiters SET company_id=?, name=?, linkedin=?, email=?, phone=?, connection_status=?, last_contact=?, follow_up_date=?, notes=? WHERE id=?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParameters(pstmt, r);
            pstmt.setLong(10, r.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { throw new RepositoryException("Update failed", e); }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Recruiters WHERE id=?")) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { throw new RepositoryException("Delete failed", e); }
    }

    @Override
    public Optional<Recruiter> findById(Long id) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Recruiters WHERE id=?")) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) { throw new RepositoryException("Find failed", e); }
        return Optional.empty();
    }

    @Override
    public List<Recruiter> findAll(int limit, int offset) {
        return queryList("SELECT * FROM Recruiters ORDER BY name LIMIT ? OFFSET ?", limit, offset);
    }
    
    public List<Recruiter> searchByCompany(Long companyId, int limit, int offset) {
        return queryListArgs("SELECT * FROM Recruiters WHERE company_id=? ORDER BY name LIMIT ? OFFSET ?", companyId, limit, offset);
    }
    
    public List<Recruiter> searchByName(String name, int limit, int offset) {
        String sql = "SELECT * FROM Recruiters WHERE name LIKE ? ORDER BY name LIMIT ? OFFSET ?";
        List<Recruiter> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
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
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Recruiters")) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) { throw new RepositoryException("Count failed", e); }
        return 0;
    }

    private void setParameters(PreparedStatement pstmt, Recruiter r) throws SQLException {
        pstmt.setLong(1, r.getCompanyId());
        pstmt.setString(2, r.getName());
        pstmt.setString(3, r.getLinkedin());
        pstmt.setString(4, r.getEmail());
        pstmt.setString(5, r.getPhone());
        pstmt.setString(6, r.getConnectionStatus() != null ? r.getConnectionStatus().name() : null);
        pstmt.setString(7, r.getLastContact() != null ? r.getLastContact().toString() : null);
        pstmt.setString(8, r.getFollowUpDate() != null ? r.getFollowUpDate().toString() : null);
        pstmt.setString(9, r.getNotes());
    }

    private Recruiter mapRow(ResultSet rs) throws SQLException {
        Recruiter r = new Recruiter();
        r.setId(rs.getLong("id"));
        r.setCompanyId(rs.getLong("company_id"));
        r.setName(rs.getString("name"));
        r.setLinkedin(rs.getString("linkedin"));
        r.setEmail(rs.getString("email"));
        r.setPhone(rs.getString("phone"));
        String cs = rs.getString("connection_status");
        if (cs != null && !cs.isEmpty()) r.setConnectionStatus(ConnectionStatus.valueOf(cs));
        String lc = rs.getString("last_contact");
        if (lc != null && !lc.isEmpty()) r.setLastContact(LocalDate.parse(lc));
        String fd = rs.getString("follow_up_date");
        if (fd != null && !fd.isEmpty()) r.setFollowUpDate(LocalDate.parse(fd));
        r.setNotes(rs.getString("notes"));
        return r;
    }
    
    private List<Recruiter> queryList(String sql, int limit, int offset) {
        List<Recruiter> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);
            try (ResultSet rs = pstmt.executeQuery()) { while (rs.next()) list.add(mapRow(rs)); }
        } catch (SQLException e) { throw new RepositoryException("Query failed", e); }
        return list;
    }
    
    private List<Recruiter> queryListArgs(String sql, Long id, int limit, int offset) {
        List<Recruiter> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.setInt(2, limit);
            pstmt.setInt(3, offset);
            try (ResultSet rs = pstmt.executeQuery()) { while (rs.next()) list.add(mapRow(rs)); }
        } catch (SQLException e) { throw new RepositoryException("Query failed", e); }
        return list;
    }
}
