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

import com.auna.citas.models.Sede;
import com.auna.citas.services.SedeService;

@RestController
@RequestMapping("/api/v1")
public class SedeController {
	@Autowired
	private SedeService service;
	
	@GetMapping("/sedes")
	public List<Sede> listar(){
		return service.listar();
	}
	
	@PostMapping("/sedes")
	public Sede guardar(@RequestBody Sede s) {
		return service.guardar(s);
	}
	
	@PutMapping("/sedes/{idSede}")
	public ResponseEntity<Sede> actualizar(@PathVariable int idSede, @RequestBody Sede s) {
	    Optional<Sede> sedeExistenteOpt = service.listarId(idSede);
	    
	    if (!sedeExistenteOpt.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    Sede sedeExistente = sedeExistenteOpt.get();
	    
	    sedeExistente.setNombreDistrito(s.getNombreDistrito());
	    sedeExistente.setNombreSede(s.getNombreSede());

	    Sede sedeActualizado = service.guardar(sedeExistente);
	    return ResponseEntity.ok(sedeActualizado);
	}

	
	@DeleteMapping("/sedes/{idSede}")
	public ResponseEntity<Map<String,Boolean>> eliminar(@PathVariable int idSede) {
		boolean eliminado = service.eliminar(idSede);
		Map<String, Boolean> response = new HashMap<>();
		
		if (eliminado) {
			response.put("deleted", true);
			return ResponseEntity.ok(response);
		} else {
			response.put("deleted", false);
			return ResponseEntity.status(404).body(response); // 404 Not Found
		}
	}
	
	@GetMapping("/sedes/{idSede}")
	public ResponseEntity<Sede> listarPorId(@PathVariable int idSede) {
		Optional<Sede> sede = service.listarId(idSede);
	    return sede.map(ResponseEntity::ok)
	              .orElseGet(() -> ResponseEntity.notFound().build());
	}
}
