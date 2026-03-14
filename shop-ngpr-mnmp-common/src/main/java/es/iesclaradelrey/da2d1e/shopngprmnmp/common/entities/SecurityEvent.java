package es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.enums.EventType;
import javax.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "security_events")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SecurityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType eventType;


}
