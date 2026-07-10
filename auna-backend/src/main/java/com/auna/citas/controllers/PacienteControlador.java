package com.auna.citas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auna.citas.models.Paciente;
import com.auna.citas.services.PacienteService;

@RestController
@RequestMapping("/api/v1")
public class PacienteControlador {
	@Autowired
	private PacienteService service;
	
	@GetMapping("/pacientes")
	public List<Paciente> listar(){
		return service.listar();
	}
}
