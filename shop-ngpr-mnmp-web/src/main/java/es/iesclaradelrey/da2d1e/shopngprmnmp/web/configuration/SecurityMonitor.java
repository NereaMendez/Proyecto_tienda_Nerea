package es.iesclaradelrey.da2d1e.shopngprmnmp.web.configuration;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.SecurityEvent;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.enums.EventType;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.SecurityEventService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.lang.Nullable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class SecurityMonitor implements LogoutSuccessHandler {

    private final SecurityEventService securityEventService;

    public SecurityMonitor(SecurityEventService securityEventService) {
        this.securityEventService = securityEventService;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        System.out.println("LOGIN EXITOSO!!!");
        Authentication auth = event.getAuthentication();

        SecurityEvent securityEvent = SecurityEvent.builder()
                .username(auth.getName())
                .eventType(EventType.LOGIN_EXITOSO)
                .fechaHora(LocalDateTime.now())
                .build();

        securityEventService.save(securityEvent);
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent event) {
        System.out.println("Fallo en LOGIN");
        Authentication auth = event.getAuthentication();

        SecurityEvent securityEvent = SecurityEvent.builder()
                .username(auth.getName())
                .eventType(EventType.LOGIN_ERROR)
                .fechaHora(LocalDateTime.now())
                .build();

        securityEventService.save(securityEvent);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, @Nullable Authentication authentication)
            throws IOException, ServletException {

        if (authentication != null) {
            System.out.println("LOGOUT DE " + authentication.getName());
            SecurityEvent securityEvent = SecurityEvent.builder()
                    .username(authentication.getName())
                    .eventType(EventType.LOGOUT)
                    .fechaHora(LocalDateTime.now())
                    .build();

            securityEventService.save(securityEvent);
        }
        // redirigir al inicio
        response.sendRedirect("/");
    }
}