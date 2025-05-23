package ro.festival.dao;

import java.util.Optional;

public abstract class BaseDAO<T> {
    public abstract void create(T obj);
    public abstract Optional<T> read(int id);
    public abstract void update(T obj);
    public abstract void delete(int id);
}
