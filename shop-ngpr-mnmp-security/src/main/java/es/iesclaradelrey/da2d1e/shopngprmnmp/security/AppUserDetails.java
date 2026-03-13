package es.iesclaradelrey.da2d1e.shopngprmnmp.security;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Usuario;

import org.jspecify.annotations.NullMarked;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NullMarked
public  class AppUserDetails implements UserDetails {

    private final Usuario usuario;

    public AppUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword(){
        System.out.println("Devolviendo password hash desde AppUserDetails");

    return this.usuario.getPassword();
    }

    @Override
    public @Nullable String getUsername() {
        return this.usuario.getEmail();
    }
    public String getFullName(){
        return String.join(" ", this.usuario.getFullName());
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
