package com.bitsamericas.backend.educativo.models.DAO;

import com.bitsamericas.backend.educativo.models.entity.Examen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExamenDAO extends JpaRepository<Examen, Long> {
}
