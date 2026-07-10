package com.auna.citas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auna.citas.interfaces.services.IPacienteService;
import com.auna.citas.models.Paciente;
import com.auna.citas.repositorys.PacienteRepository;

@Service
public class PacienteService implements IPacienteService{
	@Autowired
	private PacienteRepository data;
	
	@Override
	public Paciente guardar(Paciente p) {
		return data.save(p);
	}

	@Override
	public List<Paciente> listar() {
		List<Paciente> listar = (List<Paciente>) data.findAll();
		return listar;
	}
}
