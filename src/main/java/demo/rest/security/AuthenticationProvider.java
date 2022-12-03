package demo.rest.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        //
    }

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        Object token = usernamePasswordAuthenticationToken.getCredentials();
        return Optional
                .ofNullable(token)
                .map(String::valueOf)
                .map(str -> {
                    var roles = getRoles(str);
                    return new AuthUser("user", str, roles);
                })
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
    }

    private List<String> getRoles(String tokenValue) {
        if (tokenValue.equals("1234")) {
            return List.of(SecurityRoles.PERFORMANCE_VIEWER);
        }

        if (tokenValue.equals("9999")) {
            return List.of(SecurityRoles.PERFORMANCE_ADMIN);
        }

        return List.of();
    }
}