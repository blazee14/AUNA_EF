package com.auna.citas.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auna.citas.models.Sede;

@Repository
public interface SedeRepository extends CrudRepository<Sede, Integer>{

}
