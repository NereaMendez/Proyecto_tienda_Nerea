package es.iesclaradelrey.da2d1e.shopngprmnmp.security;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Usuario;

import org.jspecify.annotations.NullMarked;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NullMarked
public  class AppUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final String fullName;
    private final Collection<? extends GrantedAuthority> authorities;

    public AppUserDetails(Usuario usuario) {
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();
        this.fullName = usuario.getFullName();

        // convertir los roles de la BD en authorities
        this.authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getId()))
                .collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; /* el profe tiene List.of() */
    }

    @Override
    public String getPassword(){
        System.out.println("Devolviendo password hash desde AppUserDetails");
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    //IMPLEMENTADOS:...
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
        return true;
    }
}
