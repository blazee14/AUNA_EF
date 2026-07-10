package com.auna.citas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auna.citas.interfaces.services.ITurnosAtencionCitasService;
import com.auna.citas.models.TurnosAtencionCitas;
import com.auna.citas.repositorys.TurnosAtencionCitaRepository;

@Service
public class TurnosAtencionCitasService implements ITurnosAtencionCitasService{
	@Autowired
	private TurnosAtencionCitaRepository data;
	
	@Override
	public List<TurnosAtencionCitas> listar() {
		List<TurnosAtencionCitas> listar = (List<TurnosAtencionCitas>) data.findAll();
		return listar;
	}

	@Override
	public Optional<TurnosAtencionCitas> listarId(int idTurnosAtencionCitas) {
		return data.findById(idTurnosAtencionCitas);
	}

	@Override
	public TurnosAtencionCitas guardar(TurnosAtencionCitas t) {		
		return data.save(t);
	}

	@Override
	public boolean eliminar(int idTurnosAtencionCitas) {
		Optional<TurnosAtencionCitas> turnosAtencionCitasOpt = listarId(idTurnosAtencionCitas);
		if (turnosAtencionCitasOpt.isPresent()) {
			data.deleteById(idTurnosAtencionCitas);
			return true;
		}
		return false;
	}

}
