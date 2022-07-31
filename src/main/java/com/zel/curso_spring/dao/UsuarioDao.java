package com.zel.curso_spring.dao;

import com.zel.curso_spring.models.Usuario;

import java.util.List;

public interface UsuarioDao {
    List<Usuario> getUsuarios();
    void eliminar(int id);
    void registrar(Usuario usuario);
    Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
}
