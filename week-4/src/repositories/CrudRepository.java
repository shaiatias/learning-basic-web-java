package repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by shai on 11/15/2017.
 */
public interface CrudRepository<T extends Entity<ID>, ID> {

    Optional<T> getOne(Predicate<T> filter);

    List<T> getAll();

    void save(T entity);

    void save(List<T> entities);

    void delete(Predicate<T> filter);

    CrudRepository truncateData();
}
