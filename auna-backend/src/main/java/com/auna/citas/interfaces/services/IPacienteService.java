package com.auna.citas.interfaces.services;

import java.util.List;

import com.auna.citas.models.Paciente;

public interface IPacienteService {
	public List<Paciente> listar();
	public Paciente guardar(Paciente p);
}
