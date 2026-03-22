package es.iesclaradelrey.da2d1e.shopngprmnmp.web.configuration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final SecurityMonitor securityMonitor;

    public SecurityConfiguration(SecurityMonitor securityMonitor) {
        this.securityMonitor = securityMonitor;
    }

    @Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher(
            ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http


                //permisos para..
                .authorizeHttpRequests(auth -> auth

                        //panel de administraion
                        .antMatchers("/admin/**").hasRole("ADMIN") //authenticated?
                        //h2: solo autenticado
                        .antMatchers("/h2-console/**").authenticated()
                        //perfil de usuario
                        .antMatchers("/users/profile").authenticated()
                        //logout
                        .antMatchers("/users/logout").authenticated()
                        //todos lo demas esta permitido
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                //Form de login: Nerea yo
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .loginProcessingUrl("/login")
                        //a dónde va después del login exitoso:
                        .defaultSuccessUrl("/", true)
                        //para mostrar mensaje de error
                        .failureUrl("/users/login?error")
                        .permitAll()
                )

                //configuramos el logout para que use monitor?
                //redireccion
                .logout(logout -> logout
                        .logoutUrl("/logout")

                        //Registra el logout:
                        .logoutSuccessHandler(securityMonitor)
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)//limpiar sesión
                        .clearAuthentication(true)//limpiar autentificación
                        .permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}