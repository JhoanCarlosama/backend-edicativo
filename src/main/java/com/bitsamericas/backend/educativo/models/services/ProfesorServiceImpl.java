package com.bitsamericas.backend.educativo.models.services;

import com.bitsamericas.backend.educativo.models.DAO.IProfesorDAO;
import com.bitsamericas.backend.educativo.models.entity.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfesorServiceImpl implements IProfesorService {

    @Autowired
    private IProfesorDAO profesorDao;

    @Override
    @Transactional(readOnly = true)
    public List<Profesor> findAll() {
        return profesorDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Profesor> findAll(Pageable pageable) {
        return profesorDao.findAll(pageable);
    }

    @Override
    @Transactional
    public Profesor save(Profesor profesor) {
        return profesorDao.save(profesor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        profesorDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Profesor findById(Long id) {
        return profesorDao.findById(id).orElse(null);
    }
}
