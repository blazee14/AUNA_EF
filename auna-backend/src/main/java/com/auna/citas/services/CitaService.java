package com.auna.citas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auna.citas.interfaces.services.ICitaService;
import com.auna.citas.models.Cita;
import com.auna.citas.repositorys.CitaRepository;

@Service
public class CitaService implements ICitaService{
	@Autowired
	private CitaRepository data;
	
	@Override
	public List<Cita> listar() {
		List<Cita> listar = (List<Cita>) data.findAll();
		return listar;
	}

	@Override
	public List<Cita> listarPorPaciente(int idPaciente) {
		return data.findByPacienteIdPaciente(idPaciente);
	}

	@Override
	public Optional<Cita> listarId(int idCita) {
		return data.findById(idCita);
	}

	@Override
	public Cita guardar(Cita c) {
		return data.save(c);
	}

	@Override
	public boolean eliminar(int idCita) {
		Optional<Cita> citaOpt = listarId(idCita);
		if (citaOpt.isPresent()) {
			data.deleteById(idCita);
			return true;
		}
		return false;
	}

}
