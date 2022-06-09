package vsu.cs.c3.g4.irentor.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vsu.cs.c3.g4.irentor.server.model.ItemsModel;
import vsu.cs.c3.g4.irentor.server.repository.ItemsRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ItemsService {
    private final ItemsRepository repository;

    @Autowired
    public ItemsService(ItemsRepository repository) {
        this.repository = repository;
    }

    public void add(ItemsModel entity) {
        this.repository.save(entity);
    }

    public void remove(ItemsModel entity) {
        this.repository.delete(entity);
    }

    public Iterable<ItemsModel> findAll() {
        return this.repository.findAll();
    }

    public ItemsModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ArrayList<ItemsModel> findByIdToList(Long id) {
        Optional<ItemsModel> op = repository.findById(id);
        ArrayList<ItemsModel> list = new ArrayList<>();
        op.ifPresent(list::add);
        return list;
    }

    public ArrayList<ItemsModel> findByCategory(Long category) {
        Iterable<ItemsModel> it = repository.findAll();
        ArrayList<ItemsModel> list = new ArrayList<>();
        for (ItemsModel item : it) {
            if (item.getCategory().equals(category)) {
                list.add(item);
            }
        }
        return list;
    }

    public ArrayList<ItemsModel> findByPrice(Float min, Float max) {
        Iterable<ItemsModel> it = repository.findAll();
        ArrayList<ItemsModel> list = new ArrayList<>();
        for (ItemsModel item : it) {
            if (item.getPrice() >= min && item.getPrice() <= max) {
                list.add(item);
            }
        }
        return list;
    }

    public ArrayList<ItemsModel> findByNoStatus(Long status) {
        Iterable<ItemsModel> it = repository.findAll();
        ArrayList<ItemsModel> list = new ArrayList<>();
        for (ItemsModel item : it) {
            if (!item.getStatus().equals(status)) {
                list.add(item);
            }
        }
        return list;
    }
}
