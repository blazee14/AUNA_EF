package com.auna.citas.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auna.citas.models.JornadaMedicos;

@Repository
public interface JornadaMedicoRepository extends CrudRepository<JornadaMedicos, Integer>{

}
