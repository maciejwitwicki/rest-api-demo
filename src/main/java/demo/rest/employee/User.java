package demo.rest.employee;

import java.util.UUID;

record User(UUID id, String name, String designation, double salary) {
    public static User create(String name,
                              String role,
                              double salary) {
        return new User(UUID.randomUUID(), name, role, salary);
    }
}
