package com.jobhuntos.repository;
import com.jobhuntos.model.Company;
import com.jobhuntos.database.DatabaseManager;
import com.jobhuntos.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyRepository implements BaseRepository<Company, Long> {
    private static final Logger logger = LoggerFactory.getLogger(CompanyRepository.class);

    @Override
    public Company save(Company company) {
        String sql = "INSERT INTO Companies (name, website, glassdoor, package, tech_stack, notes) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, company.getName());
            pstmt.setString(2, company.getWebsite());
            pstmt.setString(3, company.getGlassdoor());
            pstmt.setString(4, company.getPackageInfo());
            pstmt.setString(5, company.getTechStack());
            pstmt.setString(6, company.getNotes());
            
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    company.setId(generatedKeys.getLong(1));
                }
            }
            logger.info("Inserted Company: {}", company.getName());
            return company;
        } catch (SQLException e) {
            logger.error("Failed to save company", e);
            throw new RepositoryException("Failed to save company", e);
        }
    }

    @Override
    public boolean update(Company company) {
        String sql = "UPDATE Companies SET name = ?, website = ?, glassdoor = ?, package = ?, tech_stack = ?, notes = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, company.getName());
            pstmt.setString(2, company.getWebsite());
            pstmt.setString(3, company.getGlassdoor());
            pstmt.setString(4, company.getPackageInfo());
            pstmt.setString(5, company.getTechStack());
            pstmt.setString(6, company.getNotes());
            pstmt.setLong(7, company.getId());
            
            int affected = pstmt.executeUpdate();
            logger.info("Updated Company ID {}: {}", company.getId(), affected > 0);
            return affected > 0;
        } catch (SQLException e) {
            logger.error("Failed to update company", e);
            throw new RepositoryException("Failed to update company", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM Companies WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int affected = pstmt.executeUpdate();
            logger.info("Deleted Company ID {}: {}", id, affected > 0);
            return affected > 0;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to delete company", e);
        }
    }

    @Override
    public Optional<Company> findById(Long id) {
        String sql = "SELECT * FROM Companies WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to find company by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Company> findAll(int limit, int offset) {
        String sql = "SELECT * FROM Companies ORDER BY name ASC LIMIT ? OFFSET ?";
        List<Company> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to find all companies", e);
        }
        return list;
    }

    @Override
    public boolean exists(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Companies";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            throw new RepositoryException("Failed to count companies", e);
        }
        return 0;
    }
    
    public List<Company> searchByName(String name, int limit, int offset) {
        String sql = "SELECT * FROM Companies WHERE name LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?";
        List<Company> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            pstmt.setInt(2, limit);
            pstmt.setInt(3, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to search companies by name", e);
        }
        return list;
    }
    
    public List<Company> searchByTechStack(String tech, int limit, int offset) {
        String sql = "SELECT * FROM Companies WHERE tech_stack LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?";
        List<Company> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + tech + "%");
            pstmt.setInt(2, limit);
            pstmt.setInt(3, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to search companies by tech stack", e);
        }
        return list;
    }

    private Company mapRow(ResultSet rs) throws SQLException {
        Company company = new Company();
        company.setId(rs.getLong("id"));
        company.setName(rs.getString("name"));
        company.setWebsite(rs.getString("website"));
        company.setGlassdoor(rs.getString("glassdoor"));
        company.setPackageInfo(rs.getString("package"));
        company.setTechStack(rs.getString("tech_stack"));
        company.setNotes(rs.getString("notes"));
        return company;
    }
}
