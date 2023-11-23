package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        if (user != null) {
            user.setRecordDateTime(new Date());
            userDao.saveUser(user);
        }
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        if (user != null) {
            user.setRecordDateTime(getUserById(user.getId()).getRecordDateTime());
            userDao.updateUser(user);
        }
    }

    @Transactional
    @Override
    public void removeUserById(Long id) {
        userDao.removeUserById(id);
    }

    @Override
    public long countUsers() {
        return userDao.countUsers();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email + " not found");
        }
        return user;
    }
}
