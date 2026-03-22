package es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.dto.NewUserDto;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Usuario;

import java.util.Objects;

public class UsuarioMapper {

    //de dto a user
    public static NewUserDto map(Usuario usuario) {

        return NewUserDto.builder()
                .fullName(usuario.getFullName())
                .email(usuario.getEmail())
                .phoneNumber(Objects.requireNonNullElse(usuario.getPhoneNumber(), null))
                .birthDate(Objects.requireNonNullElse(usuario.getBirthDate(), null))
                .password(usuario.getPassword())
                .dateTimeRegistration(usuario.getDateTimeRegistration())
                .build();

    }

    //de user a dto

    public static Usuario map (NewUserDto newUserDto) {


        if (newUserDto.getBirthDate() == null) {
        return Usuario.builder()
                .fullName(newUserDto.getFullName())
                .email(newUserDto.getEmail())
                .phoneNumber(Objects.requireNonNullElse(newUserDto.getPhoneNumber(), null))
                .password(newUserDto.getPassword())
                .build();
    } else {
        return Usuario.builder()
                .fullName(newUserDto.getFullName())
                .email(newUserDto.getEmail())
                .phoneNumber(Objects.requireNonNullElse(newUserDto.getPhoneNumber(), null))
                .password(newUserDto.getPassword())
                .birthDate(newUserDto.getBirthDate())
                .build();
        }
    }

}
