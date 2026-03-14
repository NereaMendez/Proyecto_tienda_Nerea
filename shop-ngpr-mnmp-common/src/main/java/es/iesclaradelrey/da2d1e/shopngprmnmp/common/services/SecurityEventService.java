package es.iesclaradelrey.da2d1e.shopngprmnmp.common.services;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.SecurityEvent;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.enums.EventType;
import org.springframework.stereotype.Service;
import java.util.Optional;

public interface SecurityEventService {
    SecurityEvent save(SecurityEvent securityEvent);
    Optional<SecurityEvent> findById(long id);
    void delete (SecurityEvent securityEvent);
}
