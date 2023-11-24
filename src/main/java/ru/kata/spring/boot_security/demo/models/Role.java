package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;
import java.util.stream.IntStream;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private Set<User> user;

    @Transient
    public final static RolesType[] allRolesTypes = RolesType.values();

    public Role(String name) {
        setName(name);
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        String nameInUpperCase = name.toUpperCase();
        boolean correctName = Arrays.stream((allRolesTypes))
                .anyMatch(r -> r.name().equals(nameInUpperCase));
        this.name = correctName ? nameInUpperCase : allRolesTypes[0].name();
    }

    /**
     * Roles preferably start with the USER,
     * since if the role is specified incorrectly, the first one is installed.
     */
    public enum RolesType {
        USER,           // can read
        SUPER_USER,     // + can update
        ADMIN,          // controls users
        SUPER_ADMIN     // + controls admins, can't be removed or locked
    }

    // todo delete

    public static Set<Role> getSetOfRoles(int numberOfRoles) {
        Set<Role> roles = new HashSet<>();
        IntStream.range(0, numberOfRoles).mapToObj(n -> new Role(allRolesTypes[n].name())).forEach(roles::add);
        return roles;
    }

    // todo delete

    public static List<Role> getListOfRoles(int numberOfRoles) {
        List<Role> roles = new ArrayList<>();
        IntStream.range(0, numberOfRoles).mapToObj(n -> new Role(allRolesTypes[n].name())).forEach(roles::add);
        return roles;
    }

    // todo delete

    public static Comparator<Role> roleComparator =
            Comparator.comparingInt(r -> RolesType.valueOf(r.getName()).ordinal());

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
