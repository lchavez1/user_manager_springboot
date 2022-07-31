package com.zel.curso_spring.controllers;

import com.zel.curso_spring.dao.UsuarioDao;
import com.zel.curso_spring.models.Usuario;
import com.zel.curso_spring.utils.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWT jwt;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){
        Usuario usuarioIdentificado = usuarioDao.obtenerUsuarioPorCredenciales(usuario);
        if(usuarioIdentificado != null){
            //return token was created by jwt
            return jwt.create(String.valueOf(usuarioIdentificado.getId()), usuarioIdentificado.getEmail());
        }
        return "FAIL";
    }
}
