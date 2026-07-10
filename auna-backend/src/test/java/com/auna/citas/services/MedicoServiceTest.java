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

import com.auna.citas.models.Medico;
import com.auna.citas.repositorys.MedicoRepository;

@ExtendWith(MockitoExtension.class)
class MedicoServiceTest {
	@Mock
	private MedicoRepository medicoRepository;

	@InjectMocks
	private MedicoService medicoService;

	@Test
	void listar_debeRetornarLosMedicosDelRepositorio() {
		Medico medico = new Medico();
		when(medicoRepository.findAll()).thenReturn(List.of(medico));

		List<Medico> resultado = medicoService.listar();

		assertSame(medico, resultado.get(0));
	}

	@Test
	void guardar_debeDelegarEnElRepositorio() {
		Medico medico = new Medico();
		when(medicoRepository.save(medico)).thenReturn(medico);

		Medico resultado = medicoService.guardar(medico);

		assertSame(medico, resultado);
	}

	@Test
	void eliminar_cuandoExisteDebeBorrar() {
		when(medicoRepository.findById(1)).thenReturn(Optional.of(new Medico()));

		boolean eliminado = medicoService.eliminar(1);

		assertTrue(eliminado);
		verify(medicoRepository).deleteById(1);
	}

	@Test
	void eliminar_cuandoNoExisteDebeRetornarFalse() {
		when(medicoRepository.findById(1)).thenReturn(Optional.empty());

		boolean eliminado = medicoService.eliminar(1);

		assertFalse(eliminado);
	}
}