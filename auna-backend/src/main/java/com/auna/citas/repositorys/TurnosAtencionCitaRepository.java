package com.auna.citas.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auna.citas.models.TurnosAtencionCitas;

@Repository
public interface TurnosAtencionCitaRepository extends CrudRepository<TurnosAtencionCitas, Integer>{

}
