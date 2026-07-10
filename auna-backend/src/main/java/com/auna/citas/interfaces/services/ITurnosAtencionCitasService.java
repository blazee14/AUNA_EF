package com.auna.citas.interfaces.services;

import java.util.List;
import java.util.Optional;

import com.auna.citas.models.TurnosAtencionCitas;

public interface ITurnosAtencionCitasService {
	public List<TurnosAtencionCitas> listar();
	public Optional<TurnosAtencionCitas> listarId(int idTurnosAtencionCitas);
	public TurnosAtencionCitas guardar(TurnosAtencionCitas t);
	public boolean eliminar(int idTurnosAtencionCitas);
}
