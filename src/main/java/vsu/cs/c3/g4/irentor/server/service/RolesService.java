package vsu.cs.c3.g4.irentor.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vsu.cs.c3.g4.irentor.server.model.CategoriesModel;
import vsu.cs.c3.g4.irentor.server.model.RolesModel;
import vsu.cs.c3.g4.irentor.server.repository.CategoriesRepository;
import vsu.cs.c3.g4.irentor.server.repository.RolesRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RolesService {
    private final RolesRepository repository;

    @Autowired
    public RolesService(RolesRepository repository) {
        this.repository = repository;
    }

    public void add(RolesModel entity) {
        this.repository.save(entity);
    }

    public void remove(RolesModel entity) {
        this.repository.delete(entity);
    }

    public Iterable<RolesModel> findAll() {
        return this.repository.findAll();
    }

    public RolesModel findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ArrayList<RolesModel> findByIdToList(Long id) {
        Optional<RolesModel> op = repository.findById(id);
        ArrayList<RolesModel> list = new ArrayList<>();
        op.ifPresent(list::add);
        return list;
    }
}
