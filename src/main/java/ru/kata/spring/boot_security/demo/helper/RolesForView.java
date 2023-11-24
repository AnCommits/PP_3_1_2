package ru.kata.spring.boot_security.demo.helper;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RolesForView {
    String firstRole;
    List<String> otherRoles;

    public RolesForView(List<String> roles) {
        firstRole = roles.isEmpty() ? "-" : roles.get(0);
        otherRoles = roles.isEmpty() ? new ArrayList<>() : roles.stream().skip(1).toList();
    }
}
