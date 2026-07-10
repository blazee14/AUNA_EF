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

import com.auna.citas.models.Especialidad;
import com.auna.citas.services.EspecialidadService;

@RestController
@RequestMapping("/api/v1")
public class EspecialidadController {
	@Autowired
	private EspecialidadService service;
	
	@GetMapping("/especialidades")
	public List<Especialidad> listar(){
		return service.listar();
	}
	
	@PostMapping("/especialidades")
	public Especialidad guardar(@RequestBody Especialidad e) {
		return service.guardar(e);
	}
	
	@PutMapping("/especialidades/{idEspecialidad}")
	public ResponseEntity<Especialidad> actualizar(@PathVariable int idEspecialidad, @RequestBody Especialidad e) {
	    Optional<Especialidad> especialidadExistenteOpt = service.listarId(idEspecialidad);
	    
	    if (!especialidadExistenteOpt.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    Especialidad especialidadExistente = especialidadExistenteOpt.get();
	    
	    especialidadExistente.setDescripcion(e.getDescripcion());
	    especialidadExistente.setNombreEspecialidad(e.getNombreEspecialidad());

	    Especialidad especialidadActualizado = service.guardar(especialidadExistente);
	    return ResponseEntity.ok(especialidadActualizado);
	}

	
	@DeleteMapping("/especialidades/{idEspecialidad}")
	public ResponseEntity<Map<String,Boolean>> eliminar(@PathVariable int idEspecialidad) {
		boolean eliminado = service.eliminar(idEspecialidad);
		Map<String, Boolean> response = new HashMap<>();
		
		if (eliminado) {
			response.put("deleted", true);
			return ResponseEntity.ok(response);
		} else {
			response.put("deleted", false);
			return ResponseEntity.status(404).body(response); // 404 Not Found
		}
	}
	
	@GetMapping("/especialidades/{idEspecialidad}")
	public ResponseEntity<Especialidad> listarPorId(@PathVariable int idEspecialidad) {
		Optional<Especialidad> espe = service.listarId(idEspecialidad);
	    return espe.map(ResponseEntity::ok)
	              .orElseGet(() -> ResponseEntity.notFound().build());
	}
}
