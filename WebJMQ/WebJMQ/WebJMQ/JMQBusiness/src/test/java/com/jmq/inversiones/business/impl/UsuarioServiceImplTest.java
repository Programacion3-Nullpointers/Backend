/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.UsuarioService;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.jmqpersistencia.daoimpl.UsuarioDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author LUIS
 */
public class UsuarioServiceImplTest {
    UsuarioService usuarioService;
    
    @BeforeEach
    public void setUp() {
        this.usuarioService = new UsuarioServiceImpl();
    }
   @Test
   public void ObtenerCliente() throws Exception{
       Usuario usuario = usuarioService.buscarUsuario(4);
        if(usuario == null){
            System.out.println("El usuario es vacio");
        }
        else{
            System.out.println("El usuario esta lleno");
            System.out.println(usuario);
        }
   }
    
}
