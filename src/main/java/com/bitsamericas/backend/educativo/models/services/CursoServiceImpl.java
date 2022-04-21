package com.bitsamericas.backend.educativo.models.services;

import com.bitsamericas.backend.educativo.models.DAO.ICursoDAO;
import com.bitsamericas.backend.educativo.models.entity.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoServiceImpl implements ICursoService {

    @Autowired
    private ICursoDAO cursoDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        return (List<Curso>) cursoDAO.findAll();
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        return cursoDAO.save(curso);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cursoDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Curso findById(Long id) {
        return cursoDAO.findById(id).orElse(null);
    }
}
