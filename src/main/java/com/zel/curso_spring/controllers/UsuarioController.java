package com.zel.curso_spring.controllers;

import com.zel.curso_spring.dao.UsuarioDao;
import com.zel.curso_spring.models.Usuario;
import com.zel.curso_spring.utils.JWT;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWT jwt;

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> obtenerUsuarios(@RequestHeader(value = "Authorization") String token){
        //If validateToken returns true means the token is correct, so in this case if validateToken returns false we will return null (no data).
        if(!validateToken(token)){
            return null;
        }
        return usuarioDao.getUsuarios();
    }

    private boolean validateToken(String token){
        String idUsusario = jwt.getKey(token);
        //If idUsuario isn't equals to null means the token is correct else token isn't correct
        return idUsusario != null;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void elimiarUsuarios(@RequestHeader(value = "Authorization") String token, @PathVariable int id){
        //The meaning of this if-block is in the ObtenerUsuarios function
        if(!validateToken(token)){
            return;
        }
        usuarioDao.eliminar(id);
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuarios(@RequestBody Usuario usuario){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        //parameters: (iterations, memory, threads, value)
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);
    }

}
