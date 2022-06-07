package vsu.cs.c3.g4.irentor.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vsu.cs.c3.g4.irentor.server.model.CategoriesModel;

@Repository
public interface CategoriesRepository extends CrudRepository<CategoriesModel, Long> {
}
