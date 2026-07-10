package com.auna.citas.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auna.citas.models.Administrador;

@Repository
public interface AdministradorRepository extends CrudRepository<Administrador, Integer>{

}
