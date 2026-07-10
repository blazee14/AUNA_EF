package com.auna.citas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auna.citas.interfaces.services.IAdministradorService;
import com.auna.citas.models.Administrador;
import com.auna.citas.repositorys.AdministradorRepository;

@Service
public class AdministradorService implements IAdministradorService{
	@Autowired
	private AdministradorRepository data;
	
	@Override
	public Administrador guardar(Administrador a) {
		return data.save(a);
	}

}
