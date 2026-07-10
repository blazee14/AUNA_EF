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

import com.auna.citas.models.JornadaMedicos;
import com.auna.citas.repositorys.JornadaMedicoRepository;

@ExtendWith(MockitoExtension.class)
class JornadaMedicoServiceTest {
	@Mock
	private JornadaMedicoRepository jornadaMedicoRepository;

	@InjectMocks
	private JornadaMedicoService jornadaMedicoService;

	@Test
	void listar_debeRetornarLasJornadasDelRepositorio() {
		JornadaMedicos jornadaMedicos = new JornadaMedicos();
		when(jornadaMedicoRepository.findAll()).thenReturn(List.of(jornadaMedicos));

		List<JornadaMedicos> resultado = jornadaMedicoService.listar();

		assertSame(jornadaMedicos, resultado.get(0));
	}

	@Test
	void guardar_debeDelegarEnElRepositorio() {
		JornadaMedicos jornadaMedicos = new JornadaMedicos();
		when(jornadaMedicoRepository.save(jornadaMedicos)).thenReturn(jornadaMedicos);

		JornadaMedicos resultado = jornadaMedicoService.guardar(jornadaMedicos);

		assertSame(jornadaMedicos, resultado);
	}

	@Test
	void eliminar_cuandoExisteDebeBorrar() {
		when(jornadaMedicoRepository.findById(1)).thenReturn(Optional.of(new JornadaMedicos()));

		boolean eliminado = jornadaMedicoService.eliminar(1);

		assertTrue(eliminado);
		verify(jornadaMedicoRepository).deleteById(1);
	}

	@Test
	void eliminar_cuandoNoExisteDebeRetornarFalse() {
		when(jornadaMedicoRepository.findById(1)).thenReturn(Optional.empty());

		boolean eliminado = jornadaMedicoService.eliminar(1);

		assertFalse(eliminado);
	}
}