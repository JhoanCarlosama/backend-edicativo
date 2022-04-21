package com.bitsamericas.backend.educativo.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cursos")
@Getter
@Setter
public class Curso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "No puede estar vacío")
    @Size(min = 4, max = 12, message = "El tamaño debe estar en 4 y 12")
    @Column(nullable = false)
    private String nombre;

    @JsonIgnoreProperties(value = { "cursos", "hibernateLazyInitializer", "handler" }, allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Profesor profesor;

    @JsonIgnoreProperties(value = { "curso", "hibernateLazyInitializer", "handler" }, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Examen> examenes;

    private static final long serialVersionUID = 1L;
}
