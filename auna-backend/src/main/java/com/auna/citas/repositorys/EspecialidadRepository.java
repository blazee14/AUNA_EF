package com.auna.citas.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auna.citas.models.Especialidad;

@Repository
public interface EspecialidadRepository extends CrudRepository<Especialidad, Integer>{

}
