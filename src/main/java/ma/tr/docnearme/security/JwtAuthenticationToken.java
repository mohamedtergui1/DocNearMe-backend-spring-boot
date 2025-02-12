package ma.tr.docnearme.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken implements Authentication {

    private  final String token;

    private Object principal;

    private boolean authenticated;

    private Collection<? extends GrantedAuthority> authorities;

    public JwtAuthenticationToken(String token) {
        this.token = token;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }




    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return JwtAuthenticationToken.class.getSimpleName();
    }

}
