package com.bitsamericas.backend.educativo.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "examenes")
@Getter
@Setter
public class Examen implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "No puede estar vacío")
    @Size(min = 4, message = "El texto debe tener al menos 4 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotNull(message = "No puede estar vacío")
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @NotNull(message = "No puede estar vacío")
    @Column(nullable = false)
    private String inicio;

    @NotNull(message = "No puede estar vacío")
    @Column(nullable = false)
    private String fin;

    @JsonIgnoreProperties(value = { "examenes", "hibernateLazyInitializer", "handler" }, allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;

    private static final long serialVersionUID = 1L;
}
