package com.auna.citas.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auna.citas.models.Paciente;

@Repository
public interface PacienteRepository extends CrudRepository<Paciente, Integer>{

}
