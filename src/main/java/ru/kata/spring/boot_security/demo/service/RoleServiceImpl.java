package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role showUserById(Long id) {
        return roleRepository.getById(id);
    }
}
//    @Override
//    public Set<Role> getRoleByName(String[] roleName) {
//        Set<Role> roleSet = new HashSet<>();
//        for (String role : roleName) {
//            roleSet.add(roleRepository.findRoleByName(role));
//        }
//        return roleSet;
//    }