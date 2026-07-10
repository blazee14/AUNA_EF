package com.auna.citas.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.auna.citas.models.DetalleSede;

@Repository
public interface DetalleSedeRepository extends CrudRepository<DetalleSede, Integer>{

}
