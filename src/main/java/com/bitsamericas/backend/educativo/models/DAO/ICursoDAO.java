package com.bitsamericas.backend.educativo.models.DAO;

import com.bitsamericas.backend.educativo.models.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICursoDAO extends JpaRepository<Curso, Long> {
}
