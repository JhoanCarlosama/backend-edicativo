package com.bitsamericas.backend.educativo.models.services;

import com.bitsamericas.backend.educativo.models.DAO.IExamenDAO;
import com.bitsamericas.backend.educativo.models.entity.Examen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamenServiceImpl implements IExamenService {

    @Autowired
    private IExamenDAO examenDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Examen> findAll() {
        return (List<Examen>) examenDAO.findAll();
    }

    @Override
    @Transactional
    public Examen save(Examen curso) {
        return examenDAO.save(curso);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        examenDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Examen findById(Long id) {
        return examenDAO.findById(id).orElse(null);
    }
}
