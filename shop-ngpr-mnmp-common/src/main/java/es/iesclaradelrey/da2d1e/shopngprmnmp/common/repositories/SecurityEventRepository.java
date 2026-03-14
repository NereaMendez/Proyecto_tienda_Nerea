package es.iesclaradelrey.da2d1e.shopngprmnmp.common.repositories;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.SecurityEvent;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Long> {
    List<SecurityEvent> findByEventType(EventType eventType);
}
