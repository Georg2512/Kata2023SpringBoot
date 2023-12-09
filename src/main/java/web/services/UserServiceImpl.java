package web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import web.dao.UserDao;
import web.model.User;
import web.repository.UserJpaRepository;

import java.util.List;
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;

    @Autowired
    public UserServiceImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userJpaRepository.getById(id);
    }

    @Override
    public void saveUser(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userJpaRepository.deleteById(id);
    }




//    @Autowired
//    UserDao userDao;
//
//    @Override
//    public List<User> getAllUsers() {
//        return userDao.getAllUsers();
//    }
//
//    @Override
//    public User getUserById(int id) {
//        return userDao.getUserById(id);
//    }
//
//    @Override
//    public void saveUser(User user) {
//        userDao.saveUser(user);
//    }
//
//    @Override
//    public void removeUserById(int id) {
//        userDao.removeUserById(id);
//    }
//
//
}
