package com.auna.citas.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auna.citas.models.Administrador;
import com.auna.citas.repositorys.AdministradorRepository;

@ExtendWith(MockitoExtension.class)
class AdministradorServiceTest {
	@Mock
	private AdministradorRepository administradorRepository;

	@InjectMocks
	private AdministradorService administradorService;

	@Test
	void guardar_debeDelegarEnElRepositorio() {
		Administrador administrador = new Administrador();
		when(administradorRepository.save(administrador)).thenReturn(administrador);

		Administrador resultado = administradorService.guardar(administrador);

		assertSame(administrador, resultado);
	}
}