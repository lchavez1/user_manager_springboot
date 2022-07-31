package com.zel.curso_spring.dao;

import com.zel.curso_spring.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(int id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        if(usuario != null){
            entityManager.remove(usuario);
        } else {
            System.out.println("No se encontro usuario con el id: " + id);
        }
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email";
        List<Usuario> list = entityManager.createQuery(query).setParameter("email", usuario.getEmail()).getResultList();

        if(list.isEmpty()){
            return  null;
        }

        String hash = list.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        //If password provided equals to hash return true else false
        if(argon2.verify(hash, usuario.getPassword())){
            return list.get(0);
        }
        return null;
    }
}
