package ua.pp.fairwind.favorid.internalDB.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.pp.fairwind.favorid.internalDB.model.Person;
import ua.pp.fairwind.favorid.internalDB.model.administrative.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ������ on 04.08.2015.
 */
public class UserDetailsAdapter implements UserDetails{
    private User user;

    public UserDetailsAdapter(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = user.getUserRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    public boolean hasRole(String role) {
        Collection<? extends GrantedAuthority> authorities = getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                hasRole=true;
                break;
            }
        }
        return hasRole;
    }

    public Person getUserPerson(){
        return user.getPerson();
    }


    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }


    public long getUserId() {
        return user.getUserID();
    }

    public User getUserP() {
        return user;
    }
}
