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

import com.auna.citas.models.Cita;
import com.auna.citas.services.CitaService;

@RestController
@RequestMapping("/api/v1")
public class CitaController {
	@Autowired
	private CitaService service;
	
	@GetMapping("/citas")
	public List<Cita> listar(){
		return service.listar();
	}
	
	@PostMapping("/citas")
	public Cita guardar(@RequestBody Cita c) {
		return service.guardar(c);
	}
	
	@PutMapping("/citas/{idCita}")
	public ResponseEntity<Cita> actualizar(@PathVariable int idCita, @RequestBody Cita c) {
	    Optional<Cita> citaExistenteOpt = service.listarId(idCita);
	    
	    if (!citaExistenteOpt.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    Cita citaExistente = citaExistenteOpt.get();
	    
	    citaExistente.setHoraCita(c.getHoraCita());
	    citaExistente.setEstado(c.getEstado());
	    citaExistente.setPaciente(c.getPaciente());
	    citaExistente.setTurnoAtencion(c.getTurnoAtencion());

	    Cita citaActualizado = service.guardar(citaExistente);
	    return ResponseEntity.ok(citaActualizado);
	}

	
	@DeleteMapping("/citas/{idCita}")
	public ResponseEntity<Map<String,Boolean>> eliminar(@PathVariable int idCita) {
		boolean eliminado = service.eliminar(idCita);
		Map<String, Boolean> response = new HashMap<>();
		
		if (eliminado) {
			response.put("deleted", true);
			return ResponseEntity.ok(response);
		} else {
			response.put("deleted", false);
			return ResponseEntity.status(404).body(response); // 404 Not Found
		}
	}
	
	@GetMapping("/citas/{idCita}")
	public ResponseEntity<Cita> listarPorId(@PathVariable int idCita) {
		Optional<Cita> cita = service.listarId(idCita);
	    return cita.map(ResponseEntity::ok)
	              .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/citas/paciente/{idPaciente}")
	public List<Cita> listarPorPaciente(@PathVariable int idPaciente) {
	    return service.listarPorPaciente(idPaciente);
	}
}
