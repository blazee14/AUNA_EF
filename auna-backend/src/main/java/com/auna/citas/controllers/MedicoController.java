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

import com.auna.citas.models.Medico;
import com.auna.citas.services.MedicoService;

@RestController
@RequestMapping("/api/v1")
public class MedicoController {
	@Autowired
	private MedicoService service;
	
	@GetMapping("/medicos")
	public List<Medico> listar(){
		return service.listar();
	}
	
	@PostMapping("/medicos")
	public Medico guardar(@RequestBody Medico m) {
		return service.guardar(m);
	}
	
	@PutMapping("/medicos/{idMedico}")
	public ResponseEntity<Medico> actualizar(@PathVariable int idMedico, @RequestBody Medico m) {
	    Optional<Medico> medicoExistenteOpt = service.listarId(idMedico);
	    
	    if (!medicoExistenteOpt.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    Medico medicoExistente = medicoExistenteOpt.get();
	    
	    medicoExistente.setNombreMedico(m.getNombreMedico());
	    medicoExistente.setEspecialidad(m.getEspecialidad());

	    Medico medicoActualizado = service.guardar(medicoExistente);
	    return ResponseEntity.ok(medicoActualizado);
	}

	
	@DeleteMapping("/medicos/{idMedico}")
	public ResponseEntity<Map<String,Boolean>> eliminar(@PathVariable int idMedico) {
		boolean eliminado = service.eliminar(idMedico);
		Map<String, Boolean> response = new HashMap<>();
		
		if (eliminado) {
			response.put("deleted", true);
			return ResponseEntity.ok(response);
		} else {
			response.put("deleted", false);
			return ResponseEntity.status(404).body(response); // 404 Not Found
		}
	}
	
	@GetMapping("/medicos/{idMedico}")
	public ResponseEntity<Medico> listarPorId(@PathVariable int idMedico) {
		Optional<Medico> medi = service.listarId(idMedico);
	    return medi.map(ResponseEntity::ok)
	              .orElseGet(() -> ResponseEntity.notFound().build());
	}
}
