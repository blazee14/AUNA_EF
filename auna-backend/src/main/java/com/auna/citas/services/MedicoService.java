package com.auna.citas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auna.citas.interfaces.services.IMedicoService;
import com.auna.citas.models.Medico;
import com.auna.citas.repositorys.MedicoRepository;

@Service
public class MedicoService implements IMedicoService{
	@Autowired
	private MedicoRepository data;

	@Override
	public List<Medico> listar() {
		List<Medico> listar = (List<Medico>) data.findAll();
		return listar;
	}

	@Override
	public Optional<Medico> listarId(int idMedico) {
		return data.findById(idMedico);
	}

	@Override
	public Medico guardar(Medico m) {
		return data.save(m);
	}

	@Override
	public boolean eliminar(int idMedico) {
		Optional<Medico> medicoOpt = listarId(idMedico);
		if (medicoOpt.isPresent()) {
			data.deleteById(idMedico);
			return true;
		}
		return false;
	}
	
}
