package com.auna.citas.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auna.citas.models.Medico;

@Repository
public interface MedicoRepository extends CrudRepository<Medico, Integer>{

}
