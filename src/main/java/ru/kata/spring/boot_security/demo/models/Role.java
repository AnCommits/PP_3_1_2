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
    public final static RolesType[] rolesTypes = RolesType.values();

    public Role(String name) {
        setName(name);
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        String nameInUpperCase = name.toUpperCase();
        boolean correctName = Arrays.stream((rolesTypes))
                .anyMatch(r -> r.name().equals(nameInUpperCase));
        this.name = correctName ? nameInUpperCase : rolesTypes[0].name();
    }

    /**
     * roles preferably start with the lowest,
     * since if the role is specified incorrectly, the first one is installed
     */
    public enum RolesType {
        USER,           // can read
        SUPER_USER,     // + can update
        ADMIN,          // controls users
        SUPER_ADMIN     // + controls admins, can't be removed or locked
    }

    public static Set<Role> getSetOfRoles(int numberOfRoles) {
        Set<Role> roles = new HashSet<>();
//        for (int i = 0; i < numberOfRoles; i++) {
//            roles.add(new Role(rolesTypes[i].name()));
//        }
        IntStream.range(0, numberOfRoles).mapToObj(n -> new Role(rolesTypes[n].name())).forEach(roles::add);
        return roles;
    }

    public static List<Role> getListOfRoles(int numberOfRoles) {
        List<Role> roles = new ArrayList<>();
        IntStream.range(0, numberOfRoles).mapToObj(n -> new Role(rolesTypes[n].name())).forEach(roles::add);
        return roles;
    }

    public static Comparator<Role> roleComparator =
            Comparator.comparingInt(r -> RolesType.valueOf(r.getName()).ordinal());

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((id == 0) ? 0 : Long.valueOf(id).hashCode());
//        return result;
//    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        Role other = (Role) obj;
//        return id == 0 ? other.id == 0 : id == other.id;
//    }
}
