package com.auna.citas.interfaces.services;

import java.util.List;
import java.util.Optional;

import com.auna.citas.models.Sede;

public interface ISedeService {
	public List<Sede> listar();
	public Optional<Sede> listarId(int idSede);
	public Sede guardar(Sede s);
	public boolean eliminar(int idSede);
}
