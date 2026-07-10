package com.auna.citas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auna.citas.interfaces.services.ISedeService;
import com.auna.citas.models.Sede;
import com.auna.citas.repositorys.SedeRepository;

@Service
public class SedeService implements ISedeService{
	@Autowired
	private SedeRepository data;

	@Override
	public List<Sede> listar() {
		List<Sede> listar = (List<Sede>) data.findAll();
		return listar;
	}

	@Override
	public Optional<Sede> listarId(int idSede) {
		return data.findById(idSede);
	}

	@Override
	public Sede guardar(Sede s) {
		return data.save(s);
	}

	@Override
	public boolean eliminar(int idSede) {
		Optional<Sede> sedeOpt = listarId(idSede);
		if (sedeOpt.isPresent()) {
			data.deleteById(idSede);
			return true;
		}
		return false;
	}

}
