package vsu.cs.c3.g4.irentor.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vsu.cs.c3.g4.irentor.server.model.OrdersModel;
import vsu.cs.c3.g4.irentor.server.model.RolesModel;
import vsu.cs.c3.g4.irentor.server.model.UsersModel;
import vsu.cs.c3.g4.irentor.server.repository.RolesRepository;
import vsu.cs.c3.g4.irentor.server.repository.UsersRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

@Service
public class UsersService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;


    @Autowired
    public UsersService(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    public boolean changePassword(UsersModel entity, String oldPassword, String newPassword) {
        if (bCryptPasswordEncoder.matches(oldPassword, entity.getPassword())
                && !oldPassword.equals(newPassword)) {
            entity.setPassword(bCryptPasswordEncoder.encode(newPassword));
            usersRepository.save(entity);
            return true;
        }
        return false;
    }

    public boolean add(UsersModel entity) {
        UsersModel userFromDB = findByLogin(entity.getUsername());

        if (userFromDB != null) {
            return false;
        }

        entity.setRoles(Collections.singleton(new RolesModel(1L, "ROLE_USER")));
        entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        usersRepository.save(entity);
        return true;
    }

    public void addExist(UsersModel entity) {
        usersRepository.save(entity);
    }

    public void remove(UsersModel entity) {
        this.usersRepository.delete(entity);
    }

    public boolean removeById(Long userId) {
        if (usersRepository.findById(userId).isPresent()) {
            usersRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public Iterable<UsersModel> findAll() {
        return this.usersRepository.findAll();
    }

    public Iterable<UsersModel> findAllByRole(Long role) {
        Iterable<UsersModel> it = this.usersRepository.findAll();
        ArrayList<UsersModel> list = new ArrayList<>();
        for (UsersModel model : it) {
            for (RolesModel currRole : model.getRoles()) {
                if (currRole.getId().equals(role)) {
                    list.add(model);
                }
            }
        }
        return list;
    }

    public UsersModel findById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public ArrayList<UsersModel> findByIdToList(Long id) {
        Optional<UsersModel> op = usersRepository.findById(id);
        ArrayList<UsersModel> list = new ArrayList<>();
        op.ifPresent(list::add);
        return list;
    }

    public UsersModel findByLogin(String login) {
        Iterable<UsersModel> it = this.usersRepository.findAll();
        for (UsersModel model : it) {
            if (model.getLogin().equals(login)) {
                return model;
            }
        }
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UsersModel user = findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
