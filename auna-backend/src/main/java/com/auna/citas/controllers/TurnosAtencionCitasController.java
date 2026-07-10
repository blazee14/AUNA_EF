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

import com.auna.citas.models.TurnosAtencionCitas;
import com.auna.citas.services.TurnosAtencionCitasService;

@RestController
@RequestMapping("/api/v1")
public class TurnosAtencionCitasController {
	@Autowired
	private TurnosAtencionCitasService service;
	
	@GetMapping("/turnosAtencionCitas")
	public List<TurnosAtencionCitas> listar(){
		return service.listar();
	}
	
	@PostMapping("/turnosAtencionCitas")
	public TurnosAtencionCitas guardar(@RequestBody TurnosAtencionCitas t) {
		return service.guardar(t);
	}
	
	@PutMapping("/turnosAtencionCitas/{idTurnosAtencionCitas}")
	public ResponseEntity<TurnosAtencionCitas> actualizar(@PathVariable int idTurnosAtencionCitas, @RequestBody TurnosAtencionCitas t) {
	    Optional<TurnosAtencionCitas> turnosAtencionCitasExistenteOpt = service.listarId(idTurnosAtencionCitas);
	    
	    if (!turnosAtencionCitasExistenteOpt.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    TurnosAtencionCitas turnosAtencionCitasExistente = turnosAtencionCitasExistenteOpt.get();
	    
	    turnosAtencionCitasExistente.setFecha(t.getFecha());
	    turnosAtencionCitasExistente.setHoraInicio(t.getHoraInicio());
	    turnosAtencionCitasExistente.setHoraFin(t.getHoraFin());
	    turnosAtencionCitasExistente.setNumCupos(t.getNumCupos());
	    turnosAtencionCitasExistente.setMedico(t.getMedico());
	    turnosAtencionCitasExistente.setDetalleSede(t.getDetalleSede());

	    TurnosAtencionCitas turnosAtencionCitasActualizado = service.guardar(turnosAtencionCitasExistente);
	    return ResponseEntity.ok(turnosAtencionCitasActualizado);
	}

	
	@DeleteMapping("/turnosAtencionCitas/{idTurnosAtencionCitas}")
	public ResponseEntity<Map<String,Boolean>> eliminar(@PathVariable int idTurnosAtencionCitas) {
		boolean eliminado = service.eliminar(idTurnosAtencionCitas);
		Map<String, Boolean> response = new HashMap<>();
		
		if (eliminado) {
			response.put("deleted", true);
			return ResponseEntity.ok(response);
		} else {
			response.put("deleted", false);
			return ResponseEntity.status(404).body(response); // 404 Not Found
		}
	}
	
	@GetMapping("/turnosAtencionCitas/{idTurnosAtencionCitas}")
	public ResponseEntity<TurnosAtencionCitas> listarPorId(@PathVariable int idTurnosAtencionCitas) {
		Optional<TurnosAtencionCitas> tur = service.listarId(idTurnosAtencionCitas);
	    return tur.map(ResponseEntity::ok)
	              .orElseGet(() -> ResponseEntity.notFound().build());
	}
}
