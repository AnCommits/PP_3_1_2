package ru.kata.spring.boot_security.demo.helper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;

@Component
public class RoleConvertor implements Converter<String, Role> {
    @Override
    public Role convert(String name) {
        return new Role(name);
    }
}
