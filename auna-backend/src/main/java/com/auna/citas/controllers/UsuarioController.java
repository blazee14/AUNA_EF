package com.auna.citas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auna.citas.models.Administrador;
import com.auna.citas.models.Paciente;
import com.auna.citas.models.Usuario;
import com.auna.citas.models.Usuario.tipoUsuario;
import com.auna.citas.services.AdministradorService;
import com.auna.citas.services.PacienteService;
import com.auna.citas.services.UsuarioService;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private AdministradorService serviceA;
	
	@Autowired
	private PacienteService serviceP;
	
	@GetMapping("/usuarios")
	public List<Usuario> listar(){
		return service.listar();
	}
	
	@PostMapping("/usuarios")
	public Usuario guardar(@RequestBody Usuario u) {
		if (u.getIdUsuario() != 0) {
			Usuario usuarioExistente = service.listarId(u.getIdUsuario()).get();
			
			if (usuarioExistente != null) {
		        if (u.getContrasena() == null || u.getContrasena().trim().isEmpty()) {
		        	u.setContrasena(usuarioExistente.getContrasena());
		        }
		            
		    }
		}  
		
		Usuario usuMandar = service.guardar(u);
		
		if (usuMandar.getTipoUsuario() == tipoUsuario.ADMINISTRADOR) {
		    Administrador admin = new Administrador();
		    admin.setUsuario(usuMandar);
		    serviceA.guardar(admin);
		} else if (usuMandar.getTipoUsuario() == tipoUsuario.PACIENTE) {
		    Paciente paci = new Paciente();
		    paci.setUsuario(usuMandar);
		    serviceP.guardar(paci);
		}
		
		return usuMandar;
	}
	
	@PutMapping("/usuarios/{idUsuario}")
	public ResponseEntity<Usuario> actualizar(@PathVariable int idUsuario, @RequestBody Usuario u) {
	    Optional<Usuario> usuarioExistenteOpt = service.listarId(idUsuario);
	    
	    if (!usuarioExistenteOpt.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    Usuario usuarioExistente = usuarioExistenteOpt.get();
	    
	    usuarioExistente.setNombre(u.getNombre());
	    usuarioExistente.setApellido(u.getApellido());
	    usuarioExistente.setTipoDocumento(u.getTipoDocumento());
	    usuarioExistente.setNumDocumento(u.getNumDocumento());
	    usuarioExistente.setNumeroCelular(u.getNumeroCelular());
	    usuarioExistente.setCorreoElectronico(u.getCorreoElectronico());
	    usuarioExistente.setTipoUsuario(u.getTipoUsuario());
	    usuarioExistente.setContrasena(u.getContrasena());

	    Usuario usuarioActualizado = service.guardar(usuarioExistente);
	    return ResponseEntity.ok(usuarioActualizado);
	}

	
	@DeleteMapping("/usuarios/{idUsuario}")
	public ResponseEntity<Map<String,Boolean>> eliminar(@PathVariable int idUsuario) {
		boolean eliminado = service.eliminar(idUsuario);
		Map<String, Boolean> response = new HashMap<>();
		
		if (eliminado) {
			response.put("deleted", true);
			return ResponseEntity.ok(response);
		} else {
			response.put("deleted", false);
			return ResponseEntity.status(404).body(response); // 404 Not Found
		}
	}
	
	@GetMapping("/usuarios/{idUsuario}")
	public ResponseEntity<Usuario> listarPorId(@PathVariable int idUsuario) {
		Optional<Usuario> usu = service.listarId(idUsuario);
	    return usu.map(ResponseEntity::ok)
	              .orElseGet(() -> ResponseEntity.notFound().build());
	}
}
