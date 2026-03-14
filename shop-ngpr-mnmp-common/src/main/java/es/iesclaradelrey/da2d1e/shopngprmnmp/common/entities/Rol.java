package es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincremental
    @Column(name = "id", length = 6)
    private String id; // Ejemplo: "ADMIN", "USER", "GESTOR"

    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;

    // Relación inversa
    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios = new HashSet<>();
}