package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.models.UsuarioModel;
import com.example.demo.repositories.UsuarioRepository;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public ArrayList<UsuarioModel> obtenerUsuarios(){
		return (ArrayList<UsuarioModel>) usuarioRepository.findAll();
	}
	
	public UsuarioModel guardarusuario(@Valid UsuarioModel usuario) {
	    try {
	        usuario.setFechaCreacion(LocalDateTime.now());
	        return usuarioRepository.save(usuario);
	    } catch (DataIntegrityViolationException e) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo electrónico ya existe");
	    }
	}
	
    public Optional<UsuarioModel> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }
        
    public UsuarioModel actualizarUsuario(Long id, UsuarioModel usuario) {
        Optional<UsuarioModel> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            UsuarioModel usuarioActualizado = usuarioExistente.get();
            usuarioActualizado.setName(usuario.getName());
            usuarioActualizado.setEmail(usuario.getEmail());
            usuarioActualizado.setPassword(usuario.getPassword());
            usuarioActualizado.setFechaModificacion(LocalDateTime.now());
            return usuarioRepository.save(usuarioActualizado);
        } 
		return usuario;
        
    }

}
