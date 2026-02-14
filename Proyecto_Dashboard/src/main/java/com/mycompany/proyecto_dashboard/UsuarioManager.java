/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_dashboard;
import com.mycompany.proyecto_dashboard.Usuario;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Hp
 */
public class UsuarioManager {
    private static Map<String, Usuario> usuarios = new HashMap<>();
    
    // Usuario de prueba por defecto
    static {
        usuarios.put("admin", new Usuario("Administrador", "admin", "admin@panaderia.com", "admin123"));
        usuarios.put("raul", new Usuario("Raúl", "raul", "raul@panaderia.com", "123456"));
    }
    
    /**
     * Valida las credenciales de un usuario
     */
    public Usuario validarUsuario(String usuario, String password) {
        Usuario user = usuarios.get(usuario);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    /**
     * Registra un nuevo usuario
     */
    public boolean registrarUsuario(String nombre, String usuario, String email, String password) {
        if (usuarios.containsKey(usuario)) {
            return false; // Usuario ya existe
        }
        
        Usuario nuevoUsuario = new Usuario(nombre, usuario, email, password);
        usuarios.put(usuario, nuevoUsuario);
        return true;
    }
    
    /**
     * Verifica si un usuario existe
     */
    public boolean usuarioExiste(String usuario) {
        return usuarios.containsKey(usuario);
    }
    
    /**
     * Obtiene un usuario por su nombre de usuario
     */
    public Usuario obtenerUsuario(String usuario) {
        return usuarios.get(usuario);
    }
}
