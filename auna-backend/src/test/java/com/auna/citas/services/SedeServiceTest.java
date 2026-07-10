package com.auna.citas.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auna.citas.models.Sede;
import com.auna.citas.repositorys.SedeRepository;

@ExtendWith(MockitoExtension.class)
class SedeServiceTest {
	@Mock
	private SedeRepository sedeRepository;

	@InjectMocks
	private SedeService sedeService;

	@Test
	void listar_debeRetornarLasSedesDelRepositorio() {
		Sede sede = new Sede();
		when(sedeRepository.findAll()).thenReturn(List.of(sede));

		List<Sede> resultado = sedeService.listar();

		assertSame(sede, resultado.get(0));
	}

	@Test
	void guardar_debeDelegarEnElRepositorio() {
		Sede sede = new Sede();
		when(sedeRepository.save(sede)).thenReturn(sede);

		Sede resultado = sedeService.guardar(sede);

		assertSame(sede, resultado);
	}

	@Test
	void eliminar_cuandoExisteDebeBorrar() {
		when(sedeRepository.findById(1)).thenReturn(Optional.of(new Sede()));

		boolean eliminado = sedeService.eliminar(1);

		assertTrue(eliminado);
		verify(sedeRepository).deleteById(1);
	}

	@Test
	void eliminar_cuandoNoExisteDebeRetornarFalse() {
		when(sedeRepository.findById(1)).thenReturn(Optional.empty());

		boolean eliminado = sedeService.eliminar(1);

		assertFalse(eliminado);
	}
}