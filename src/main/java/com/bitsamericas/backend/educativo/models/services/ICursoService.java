package com.bitsamericas.backend.educativo.models.services;

import com.bitsamericas.backend.educativo.models.entity.Curso;

import java.util.List;

public interface ICursoService {
    public List<Curso> findAll();

    public Curso save(Curso curso);

    public void delete(Long id);

    public Curso findById(Long id);
}
