package vsu.cs.c3.g4.irentor.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vsu.cs.c3.g4.irentor.server.model.CategoriesModel;
import vsu.cs.c3.g4.irentor.server.repository.CategoriesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {
    private final CategoriesRepository repository;

    @Autowired
    public CategoriesService(CategoriesRepository repository) {
        this.repository = repository;
    }

    public void add(CategoriesModel entity) {
        this.repository.save(entity);
    }

    public void remove(CategoriesModel entity) {
        this.repository.delete(entity);
    }

    public Iterable<CategoriesModel> findAll() {
        return this.repository.findAll();
    }

    public CategoriesModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ArrayList<CategoriesModel> findByIdToList(Long id) {
        Optional<CategoriesModel> op = repository.findById(id);
        ArrayList<CategoriesModel> list = new ArrayList<>();
        op.ifPresent(list::add);
        return list;
    }
}
