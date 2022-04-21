package com.bitsamericas.backend.educativo.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "profesores")
@Getter
@Setter
public class Profesor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "No puede estar vacío")
    @Size(min = 4, max = 12, message = "El tamaño debe estar en 4 y 12")
    @Column(nullable = false)
    private String nombre;

    @NotEmpty(message = "No puede estar vacío")
    @Column(nullable = false)
    private String apellido;

    @NotEmpty(message = "No puede estar vacío")
    @Email(message = "No es un direccion de correo valido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "No puede estar vacío")
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    private String foto;

    @JsonIgnoreProperties(value = { "profesor", "hibernateLazyInitializer", "handler" }, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profesor", cascade = CascadeType.ALL)
    private List<Curso> cursos;

    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }

    private static final long serialVersionUID = 1L;
}
