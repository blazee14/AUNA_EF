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

import com.auna.citas.models.DetalleSede;
import com.auna.citas.services.DetalleSedeService;

@RestController
@RequestMapping("/api/v1")
public class DetalleSedeController {
	@Autowired
	private DetalleSedeService service;
	
	@GetMapping("/detalleSedes")
	public List<DetalleSede> listar(){
		return service.listar();
	}
	
	@PostMapping("/detalleSedes")
	public DetalleSede guardar(@RequestBody DetalleSede d) {
		return service.guardar(d);
	}
	
	@PutMapping("/detalleSedes/{idDetalleSede}")
	public ResponseEntity<DetalleSede> actualizar(@PathVariable int idDetalleSede, @RequestBody DetalleSede d) {
	    Optional<DetalleSede> detalleSedeExistenteOpt = service.listarId(idDetalleSede);
	    
	    if (!detalleSedeExistenteOpt.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }

	    DetalleSede detalleSedeExistente = detalleSedeExistenteOpt.get();
	    
	    detalleSedeExistente.setSede(d.getSede());
	    detalleSedeExistente.setEspecialidad(d.getEspecialidad());

	    DetalleSede detalleSedeActualizado = service.guardar(detalleSedeExistente);
	    return ResponseEntity.ok(detalleSedeActualizado);
	}

	
	@DeleteMapping("/detalleSedes/{idDetalleSede}")
	public ResponseEntity<Map<String,Boolean>> eliminar(@PathVariable int idDetalleSede) {
		boolean eliminado = service.eliminar(idDetalleSede);
		Map<String, Boolean> response = new HashMap<>();
		
		if (eliminado) {
			response.put("deleted", true);
			return ResponseEntity.ok(response);
		} else {
			response.put("deleted", false);
			return ResponseEntity.status(404).body(response); // 404 Not Found
		}
	}
	
	@GetMapping("/detalleSedes/{idDetalleSede}")
	public ResponseEntity<DetalleSede> listarPorId(@PathVariable int idDetalleSede) {
		Optional<DetalleSede> deta = service.listarId(idDetalleSede);
	    return deta.map(ResponseEntity::ok)
	              .orElseGet(() -> ResponseEntity.notFound().build());
	}
}
