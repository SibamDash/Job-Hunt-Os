package com.jobhuntos.repository;
import com.jobhuntos.model.Task;
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

public class TaskRepository implements BaseRepository<Task, Long> {
    private static final Logger logger = LoggerFactory.getLogger(TaskRepository.class);

    @Override
    public Task save(Task task) {
        String sql = "INSERT INTO Tasks (application_id, title, description, due_date, priority, completed) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(pstmt, task);
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getLong(1));
                }
            }
            return task;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to save task", e);
        }
    }

    @Override
    public boolean update(Task task) {
        String sql = "UPDATE Tasks SET application_id = ?, title = ?, description = ?, due_date = ?, priority = ?, completed = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setParameters(pstmt, task);
            pstmt.setLong(7, task.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to update task", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM Tasks WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException("Failed to delete task", e);
        }
    }

    @Override
    public Optional<Task> findById(Long id) {
        String sql = "SELECT * FROM Tasks WHERE id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to find task", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Task> findAll(int limit, int offset) {
        String sql = "SELECT * FROM Tasks ORDER BY due_date ASC LIMIT ? OFFSET ?";
        return queryList(sql, limit, offset);
    }
    
    public List<Task> searchByApplication(Long appId, int limit, int offset) {
        String sql = "SELECT * FROM Tasks WHERE application_id = ? ORDER BY due_date ASC LIMIT ? OFFSET ?";
        List<Task> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, appId);
            pstmt.setInt(2, limit);
            pstmt.setInt(3, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to search tasks by app", e);
        }
        return list;
    }

    @Override
    public boolean exists(Long id) { return findById(id).isPresent(); }

    @Override
    public long count() {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Tasks")) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) { throw new RepositoryException("Count failed", e); }
        return 0;
    }

    private void setParameters(PreparedStatement pstmt, Task task) throws SQLException {
        if (task.getApplicationId() != null) pstmt.setLong(1, task.getApplicationId());
        else pstmt.setNull(1, Types.INTEGER);
        
        pstmt.setString(2, task.getTitle());
        pstmt.setString(3, task.getDescription());
        pstmt.setString(4, task.getDueDate() != null ? task.getDueDate().toString() : null);
        pstmt.setString(5, task.getPriority() != null ? task.getPriority().name() : null);
        pstmt.setInt(6, task.isCompleted() ? 1 : 0);
    }

    private Task mapRow(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        long appId = rs.getLong("application_id");
        if (!rs.wasNull()) task.setApplicationId(appId);
        
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        
        String dueDate = rs.getString("due_date");
        if (dueDate != null && !dueDate.isEmpty()) task.setDueDate(LocalDate.parse(dueDate));
        
        String priority = rs.getString("priority");
        if (priority != null && !priority.isEmpty()) task.setPriority(Priority.valueOf(priority));
        
        task.setCompleted(rs.getInt("completed") == 1);
        return task;
    }
    
    private List<Task> queryList(String sql, int limit, int offset) {
        List<Task> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to query tasks", e);
        }
        return list;
    }
}
