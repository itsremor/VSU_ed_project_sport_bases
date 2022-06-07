package vsu.cs.c3.g4.irentor.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vsu.cs.c3.g4.irentor.server.model.OrdersModel;

@Repository
public interface OrdersRepository extends CrudRepository<OrdersModel, Long> {
}
