package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.helper.RolesForView;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    /**
     * Used such as login
     */
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar birthDate;

    @Column(name = "record_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordDateTime;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Transient
    private RolesForView rolesForViews;

    private boolean locked;

    public User(String firstName, String lastName, String email, String password,
                Calendar birthDate, Set<Role> roles, boolean locked) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.roles = roles;
        this.locked = locked;
    }

    public boolean hasRole(String role) {
        return roles.stream().anyMatch(r -> r.getName().equals(role));
    }

    public List<String> getRolesNames() {
        return roles.stream().map(Role::getName).toList();
    }

    // todo delete

    public String getMainRole() {
        Optional<Role> main = roles.stream().max(Role.roleComparator);
        return main.isPresent() ? main.get().getName() : Role.allRolesTypes[0].name();
    }

    public String birthDateToString() {
        return birthDate != null
                ? new SimpleDateFormat("yyyy-MM-dd").format(birthDate.getTime())
                : "";
    }

    public String recordDateTimeToString() {
        return recordDateTime != null
                ? new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss").format(recordDateTime)
                : "";
    }

    @Override
    public String toString() {
        return "User{" + id + ' ' + firstName + ' ' + lastName + ' ' + email + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
