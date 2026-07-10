package com.auna.citas.repositorys;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auna.citas.models.Cita;

@Repository
public interface CitaRepository extends CrudRepository<Cita, Integer>{
	// Busca todas las citas por id del paciente
    List<Cita> findByPacienteIdPaciente(int idPaciente);
}
