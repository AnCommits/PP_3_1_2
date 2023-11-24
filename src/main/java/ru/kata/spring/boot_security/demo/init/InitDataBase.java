package ru.kata.spring.boot_security.demo.init;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.*;

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
            initAdmin();
            initUser();
            initManager1();
            initManager2();
            initHr1();
            initHr2();
            initBoss();
        }
    }

    public void initAdmin() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        roles.add(new Role("USER"));
        User user = new User(null, null,
                "1", passwordEncoder.encode("1"),
                null, roles, false);
        userService.saveUser(user);
    }

    public void initUser() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));
        User user = new User("Уильям", "Остин Берт",
                "2", passwordEncoder.encode("2"),
                new GregorianCalendar(1792, Calendar.JUNE, 13), roles, false);
        userService.saveUser(user);
    }

    public void initManager1() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("MANAGER"));
        roles.add(new Role("USER"));
        User user = new User("Вася", "Менеджер1",
                "m", passwordEncoder.encode("m"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initManager2() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("MANAGER"));
        roles.add(new Role("USER"));
        User user = new User("Петр", "Менеджер2",
                "mm", passwordEncoder.encode("mm"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initHr1() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("HR"));
        roles.add(new Role("USER"));
        User user = new User("Катя", "Катерина",
                "h", passwordEncoder.encode("h"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initHr2() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("HR"));
        roles.add(new Role("USER"));
        User user = new User("Маша", "Решает_все",
                "hh", passwordEncoder.encode("hh"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initBoss() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("MANAGER"));
        roles.add(new Role("HR"));
        roles.add(new Role("USER"));
        User user = new User("Кот", "Матроскин",
                "k", passwordEncoder.encode("k"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }
}
