package com.auna.citas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auna.citas.interfaces.services.IEspecialidadService;
import com.auna.citas.models.Especialidad;
import com.auna.citas.repositorys.EspecialidadRepository;

@Service
public class EspecialidadService implements IEspecialidadService{
	@Autowired
	private EspecialidadRepository data;
	
	@Override
	public List<Especialidad> listar() {
		List<Especialidad> listar = (List<Especialidad>) data.findAll();
		return listar;
	}

	@Override
	public Optional<Especialidad> listarId(int idEspecialidad) {
		return data.findById(idEspecialidad);
	}

	@Override
	public Especialidad guardar(Especialidad e) {
		return data.save(e);
	}

	@Override
	public boolean eliminar(int idEspecialidad) {
		Optional<Especialidad> especialidadOpt = listarId(idEspecialidad);
		if (especialidadOpt.isPresent()) {
			data.deleteById(idEspecialidad);
			return true;
		}
		return false;
	}

}
