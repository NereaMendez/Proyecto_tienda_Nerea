package es.iesclaradelrey.da2d1e.shopngprmnmp.common.services;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.SecurityEvent;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.repositories.SecurityEventRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityEventServiceImpl implements SecurityEventService{

    private final SecurityEventRepository securityEventRepository;

    public SecurityEventServiceImpl(SecurityEventRepository securityEventRepository) {
        this.securityEventRepository = securityEventRepository;
    }

    @Override
    public SecurityEvent save(SecurityEvent securityEvent) {
        return securityEventRepository.save(securityEvent);
    }

    @Override
    public Optional<SecurityEvent> findById(long id) {
        return securityEventRepository.findById(id);
    }

    @Override
    public void delete(SecurityEvent securityEvent) {
        securityEventRepository.delete(securityEvent);
    }
}
