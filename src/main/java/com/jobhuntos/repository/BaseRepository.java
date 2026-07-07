package com.jobhuntos.repository;
import java.util.List;
import java.util.Optional;
public interface BaseRepository<T, ID> {
    T save(T entity);
    boolean update(T entity);
    boolean delete(ID id);
    Optional<T> findById(ID id);
    List<T> findAll(int limit, int offset);
    boolean exists(ID id);
    long count();
}
