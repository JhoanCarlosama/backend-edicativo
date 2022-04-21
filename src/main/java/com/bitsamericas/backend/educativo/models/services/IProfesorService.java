package com.bitsamericas.backend.educativo.models.services;

import com.bitsamericas.backend.educativo.models.entity.Profesor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProfesorService {

    public List<Profesor> findAll();

    public Page<Profesor> findAll(Pageable pageable);

    public Profesor save(Profesor profesor);

    public void delete(Long id);

    public Profesor findById(Long id);
}
