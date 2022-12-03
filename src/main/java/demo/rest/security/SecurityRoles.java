package demo.rest.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityRoles {

    public static final String PERFORMANCE_VIEWER = "performance_viewer";
    public static final String PERFORMANCE_ADMIN = "performance_admin";
}
