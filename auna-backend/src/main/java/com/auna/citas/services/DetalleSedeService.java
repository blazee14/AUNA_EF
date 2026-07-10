package com.auna.citas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auna.citas.interfaces.services.IDetalleSedeService;
import com.auna.citas.models.DetalleSede;
import com.auna.citas.repositorys.DetalleSedeRepository;

@Service
public class DetalleSedeService implements IDetalleSedeService{
	@Autowired
	private DetalleSedeRepository data;

	@Override
	public List<DetalleSede> listar() {
		List<DetalleSede> listar = (List<DetalleSede>) data.findAll();
		return listar;
	}

	@Override
	public Optional<DetalleSede> listarId(int idDetalleSede) {
		return data.findById(idDetalleSede);
	}

	@Override
	public DetalleSede guardar(DetalleSede d) {
		return data.save(d);
	}

	@Override
	public boolean eliminar(int idDetalleSede) {
		Optional<DetalleSede> detalleSedeOpt = listarId(idDetalleSede);
		if (detalleSedeOpt.isPresent()) {
			data.deleteById(idDetalleSede);
			return true;
		}
		return false;
	}
}
