package demo.rest.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class AuthUser extends User {

    public AuthUser(String username, String password, List<String> authoriesList) {
        super(username, password, authoriesList.stream().map(SimpleGrantedAuthority::new).toList());
    }
}
