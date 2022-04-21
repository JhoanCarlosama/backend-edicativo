package com.bitsamericas.backend.educativo.models.services;

import com.bitsamericas.backend.educativo.models.entity.Examen;

import java.util.List;

public interface IExamenService {
    public List<Examen> findAll();

    public Examen save(Examen curso);

    public void delete(Long id);

    public Examen findById(Long id);
}
