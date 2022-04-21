package com.bitsamericas.backend.educativo.models.DAO;

import com.bitsamericas.backend.educativo.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
    public Usuario findByUsername(String username);
}
