package com.bitsamericas.backend.educativo.models.DAO;

import com.bitsamericas.backend.educativo.models.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfesorDAO extends JpaRepository<Profesor, Long> {
}
