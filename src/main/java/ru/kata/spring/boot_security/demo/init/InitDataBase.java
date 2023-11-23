package ru.kata.spring.boot_security.demo.init;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

@Component
public class InitDataBase {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InitDataBase(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method creates and puts in the table an admin in case there are no entries in the table.
     */
    @PostConstruct
    public void initUsers() {
        if (userService.countUsers() == 0) {
            initSuperAdmin();
            initAdmin();
            initSuperUser();
            initUser();
        }
    }

    public void initSuperAdmin() {
        Set<Role> roles = Role.getSetOfRoles(4);
        User user = new User(null, null,
                "1", passwordEncoder.encode("1"),
                null, roles, false);
        userService.saveUser(user);
    }

    public void initAdmin() {
        Set<Role> roles = Role.getSetOfRoles(3);
        User user = new User(null, null,
                "2", passwordEncoder.encode("2"),
                null, roles, false);
        userService.saveUser(user);
    }

    public void initSuperUser() {
        Set<Role> roles = Role.getSetOfRoles(2);
        User user = new User("Альберт", "Эйнштейн",
                "3", passwordEncoder.encode("3"),
                new GregorianCalendar(1879, Calendar.MARCH, 14), roles, false);
        userService.saveUser(user);
    }

    public void initUser() {
        Set<Role> roles = Role.getSetOfRoles(1);
        User user = new User("Мария", "Кюри",
                "4", passwordEncoder.encode("4"),
                new GregorianCalendar(1867, Calendar.NOVEMBER, 7), roles, false);
        userService.saveUser(user);
    }
}
