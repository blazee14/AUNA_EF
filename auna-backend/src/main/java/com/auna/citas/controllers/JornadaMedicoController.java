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

import com.auna.citas.models.JornadaMedicos;
import com.auna.citas.services.JornadaMedicoService;

@RestController
@RequestMapping("/api/v1")
public class JornadaMedicoController {
	@Autowired
	private JornadaMedicoService service;
	
	@GetMapping("/jornadaMedicos")
	public List<JornadaMedicos> listar(){
		return service.listar();
	}
	
	@PostMapping("/jornadaMedicos")
	public JornadaMedicos guardar(@RequestBody JornadaMedicos j) {
		return service.guardar(j);
	}
	
	@PutMapping("/jornadaMedicos/{idJornadaMedico}")
	public ResponseEntity<JornadaMedicos> actualizar(@PathVariable int idJornadaMedico, @RequestBody JornadaMedicos j) {
	    Optional<JornadaMedicos> jornadaMedicosExistenteOpt = service.listarId(idJornadaMedico);
	    
	    if (!jornadaMedicosExistenteOpt.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    JornadaMedicos jornadaMedicosExistente = jornadaMedicosExistenteOpt.get();
	    
	    jornadaMedicosExistente.setDiaSemana(j.getDiaSemana());
	    jornadaMedicosExistente.setMedico(j.getMedico());

	    JornadaMedicos jornadaMedicosActualizado = service.guardar(jornadaMedicosExistente);
	    return ResponseEntity.ok(jornadaMedicosActualizado);
	}

	
	@DeleteMapping("/jornadaMedicos/{idJornadaMedico}")
	public ResponseEntity<Map<String,Boolean>> eliminar(@PathVariable int idJornadaMedico) {
		boolean eliminado = service.eliminar(idJornadaMedico);
		Map<String, Boolean> response = new HashMap<>();
		
		if (eliminado) {
			response.put("deleted", true);
			return ResponseEntity.ok(response);
		} else {
			response.put("deleted", false);
			return ResponseEntity.status(404).body(response); // 404 Not Found
		}
	}
	
	@GetMapping("/jornadaMedicos/{idJornadaMedico}")
	public ResponseEntity<JornadaMedicos> listarPorId(@PathVariable int idJornadaMedico) {
		Optional<JornadaMedicos> jor = service.listarId(idJornadaMedico);
	    return jor.map(ResponseEntity::ok)
	              .orElseGet(() -> ResponseEntity.notFound().build());
	}
}
