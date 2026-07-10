package com.auna.citas.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auna.citas.models.Paciente;
import com.auna.citas.repositorys.PacienteRepository;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {
	@Mock
	private PacienteRepository pacienteRepository;

	@InjectMocks
	private PacienteService pacienteService;

	@Test
	void guardar_debeDelegarEnElRepositorio() {
		Paciente paciente = new Paciente();
		when(pacienteRepository.save(paciente)).thenReturn(paciente);

		Paciente resultado = pacienteService.guardar(paciente);

		assertSame(paciente, resultado);
	}

	@Test
	void listar_debeRetornarLosPacientesDelRepositorio() {
		Paciente paciente = new Paciente();
		when(pacienteRepository.findAll()).thenReturn(List.of(paciente));

		List<Paciente> resultado = pacienteService.listar();

		assertSame(paciente, resultado.get(0));
	}
}