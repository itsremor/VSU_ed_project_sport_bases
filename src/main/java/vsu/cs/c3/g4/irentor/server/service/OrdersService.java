package vsu.cs.c3.g4.irentor.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vsu.cs.c3.g4.irentor.server.model.OrdersModel;
import vsu.cs.c3.g4.irentor.server.model.UsersModel;
import vsu.cs.c3.g4.irentor.server.repository.OrdersRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrdersService {
    private final OrdersRepository repository;

    @Autowired
    public OrdersService(OrdersRepository repository) {
        this.repository = repository;
    }

    public void add(OrdersModel entity) {
        this.repository.save(entity);
    }

    public void remove(OrdersModel entity) {
        this.repository.delete(entity);
    }

    public Iterable<OrdersModel> findAll() {
        return this.repository.findAll();
    }

    public Iterable<OrdersModel> findAllByUser(Long user) {
        Iterable<OrdersModel> it = this.repository.findAll();
        ArrayList<OrdersModel> list = new ArrayList<>();
        for (OrdersModel model : it) {
            if (model.getUser().equals(user)) {
                list.add(model);
            }
        }
        return list;
    }

    public Iterable<OrdersModel> findAllByStatus(Long status) {
        Iterable<OrdersModel> it = this.repository.findAll();
        ArrayList<OrdersModel> list = new ArrayList<>();
        for (OrdersModel model : it) {
            if (model.getStatus().equals(status)) {
                list.add(model);
            }
        }
        return list;
    }

    public OrdersModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ArrayList<OrdersModel> findByIdToList(Long id) {
        Optional<OrdersModel> op = repository.findById(id);
        ArrayList<OrdersModel> list = new ArrayList<>();
        op.ifPresent(list::add);
        return list;
    }
}
