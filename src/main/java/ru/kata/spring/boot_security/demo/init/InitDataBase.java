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
            initSuperAdmin();
            initAdmin();

            initSuperUser();
            initUser();

            initSuperManager();
            initManager();

            initSuperHr();
            initHr();
        }
    }

    public void initSuperAdmin() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("SUPER_ADMIN"));
        roles.add(new Role("USER"));
        roles.add(new Role("CAN_WRITE"));
        User user = new User(null, null,
                "1", passwordEncoder.encode("1"),
                null, roles, false);
        userService.saveUser(user);
    }

    public void initAdmin() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ADMIN"));
        roles.add(new Role("USER"));
        roles.add(new Role("CAN_WRITE"));
        User user = new User(null, null,
                "2", passwordEncoder.encode("2"),
                null, roles, false);
        userService.saveUser(user);
    }

    public void initSuperUser() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));
        roles.add(new Role("CAN_WRITE"));
        User user = new User("Уильям", "Остин Берт",
                "3", passwordEncoder.encode("3"),
                new GregorianCalendar(1792, Calendar.JUNE, 13), roles, false);
        userService.saveUser(user);
    }

    public void initUser() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));
        User user = new User("Мария", "Кюри",
                "4", passwordEncoder.encode("4"),
                new GregorianCalendar(1867, Calendar.NOVEMBER, 7), roles, false);
        userService.saveUser(user);
    }

    public void initSuperManager() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("MANAGER"));
        roles.add(new Role("USER"));
        roles.add(new Role("CAN_WRITE"));
        User user = new User("Петр", "Супер_менеджер",
                "sm", passwordEncoder.encode("sm"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initManager() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("MANAGER"));
        roles.add(new Role("USER"));
        User user = new User("Вася", "Менеджер",
                "m", passwordEncoder.encode("m"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initSuperHr() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("HR"));
        roles.add(new Role("USER"));
        roles.add(new Role("CAN_WRITE"));
        User user = new User("Мария", "Решает_все",
                "sh", passwordEncoder.encode("sh"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initHr() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("HR"));
        roles.add(new Role("USER"));
        User user = new User("Катя", "Катерина",
                "h", passwordEncoder.encode("h"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }
}
