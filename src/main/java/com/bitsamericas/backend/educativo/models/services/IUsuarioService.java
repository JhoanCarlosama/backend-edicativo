package com.bitsamericas.backend.educativo.models.services;

import com.bitsamericas.backend.educativo.models.entity.Usuario;

public interface IUsuarioService {
    public Usuario findByUsername(String username);
}
