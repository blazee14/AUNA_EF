package com.auna.citas.interfaces.services;

import java.util.List;
import java.util.Optional;

import com.auna.citas.models.Cita;

public interface ICitaService {
	public List<Cita> listar();
	public List<Cita> listarPorPaciente(int idPaciente);  //Lista Para que paciente vea sus citas
	public Optional<Cita> listarId(int idCita);
	public Cita guardar(Cita c);
	public boolean eliminar(int idCita);
}
