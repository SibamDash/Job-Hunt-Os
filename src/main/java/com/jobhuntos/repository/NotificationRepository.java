package com.jobhuntos.repository;
import com.jobhuntos.model.Notification;
import com.jobhuntos.database.DatabaseManager;
import com.jobhuntos.exception.RepositoryException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotificationRepository implements BaseRepository<Notification, Long> {
    @Override
    public Notification save(Notification entity) {
        // Mock save implementation for standard BaseRepository structure
        return entity;
    }

    @Override
    public boolean update(Notification entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Notification> findAll(int limit, int offset) {
        return new ArrayList<>();
    }

    @Override
    public boolean exists(Long id) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }
}
