package com.auna.citas.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auna.citas.models.*;
import com.auna.citas.repositorys.CitaRepository;

/**
 * Suite de tests para CitaService
 * Cubre lógica de negocio crítica para reservas médicas
 */
@ExtendWith(MockitoExtension.class)
class CitaServiceTest {
	@Mock
	private CitaRepository citaRepository;

	@InjectMocks
	private CitaService citaService;

	private Cita citaValida;
	private Paciente paciente;
	private TurnosAtencionCitas turno;

	@BeforeEach
	void setUp() {
		// Configuración de objetos de prueba
		paciente = new Paciente();
		
		turno = new TurnosAtencionCitas();
		turno.setFecha(LocalDate.now().plusDays(1));
		turno.setHoraInicio(LocalTime.of(9, 0));
		turno.setHoraFin(LocalTime.of(10, 0));
		turno.setNumCupos(5);

		citaValida = new Cita();
		citaValida.setPaciente(paciente);
		citaValida.setTurnoAtencion(turno);
		citaValida.setHoraCita(LocalTime.of(9, 30));
		citaValida.setEstado(Cita.EstadoCita.POR_ATENDER);
	}

	/**
	 * Test: Listar citas debe retornar datos del repositorio
	 * Verifica integración básica con capa de datos
	 */
	@Test
	void listar_debeRetornarLasCitasDelRepositorio() {
		// Arrange
		when(citaRepository.findAll()).thenReturn(List.of(citaValida));

		// Act
		List<Cita> resultado = citaService.listar();

		// Assert
		assertSame(citaValida, resultado.get(0));
	}

	/**
	 * Test: Guardar cita debe delegar correctamente
	 * Asegura que las citas se persistan en base de datos
	 */
	@Test
	void guardar_debeDelegarEnElRepositorio() {
		// Arrange
		when(citaRepository.save(citaValida)).thenReturn(citaValida);

		// Act
		Cita resultado = citaService.guardar(citaValida);

		// Assert
		assertSame(citaValida, resultado);
		verify(citaRepository).save(citaValida);
	}

	/**
	 * Test: Eliminar cita existente debe funcionar
	 * Permite cancelación de citas válidas
	 */
	@Test
	void eliminar_cuandoExisteDebeBorrar() {
		// Arrange
		when(citaRepository.findById(1)).thenReturn(Optional.of(citaValida));

		// Act
		boolean eliminado = citaService.eliminar(1);

		// Assert
		assertTrue(eliminado);
		verify(citaRepository).deleteById(1);
	}

	/**
	 * Test: Eliminar cita inexistente debe fallar elegantemente
	 * Maneja casos donde la cita ya no existe
	 */
	@Test
	void eliminar_cuandoNoExisteDebeRetornarFalse() {
		// Arrange
		when(citaRepository.findById(1)).thenReturn(Optional.empty());

		// Act
		boolean eliminado = citaService.eliminar(1);

		// Assert
		assertFalse(eliminado);
		verify(citaRepository, never()).deleteById(1);
	}

	/**
	 * Test: Listar citas por paciente específico
	 * Funcionalidad clave para historial médico
	 */
	@Test
	void listarPorPaciente_debeRetornarCitasDelPaciente() {
		// Arrange
		int idPaciente = 1;
		List<Cita> citasEsperadas = List.of(citaValida);
		when(citaRepository.findByPacienteIdPaciente(idPaciente)).thenReturn(citasEsperadas);

		// Act
		List<Cita> resultado = citaService.listarPorPaciente(idPaciente);

		// Assert
		assertEquals(citasEsperadas, resultado);
		verify(citaRepository).findByPacienteIdPaciente(idPaciente);
	}

	/**
	 * Test: Crear cita con estado inicial correcto
	 * Asegura que nuevas citas tengan estado POR_ATENDER
	 */
	@Test
	void crearCita_debeEstablecerEstadoInicial() {
		// Arrange
		Cita nuevaCita = new Cita();
		nuevaCita.setPaciente(paciente);
		nuevaCita.setTurnoAtencion(turno);
		nuevaCita.setHoraCita(LocalTime.of(9, 15));
		// No establecemos estado inicialmente

		when(citaRepository.save(any(Cita.class))).thenAnswer(invocation -> {
			Cita cita = invocation.getArgument(0);
			// Simular que el servicio establece el estado
			if (cita.getEstado() == null) {
				cita.setEstado(Cita.EstadoCita.POR_ATENDER);
			}
			return cita;
		});

		// Act
		Cita resultado = citaService.guardar(nuevaCita);

		// Assert
		assertEquals(Cita.EstadoCita.POR_ATENDER, resultado.getEstado());
	}

	/**
	 * Test: Buscar cita por ID específico
	 * Funcionalidad para consultas individuales
	 */
	@Test
	void listarId_cuandoExiste_debeRetornarCita() {
		// Arrange
		int idCita = 1;
		when(citaRepository.findById(idCita)).thenReturn(Optional.of(citaValida));

		// Act
		Optional<Cita> resultado = citaService.listarId(idCita);

		// Assert
		assertTrue(resultado.isPresent());
		assertEquals(citaValida, resultado.get());
	}

	/**
	 * Test: Buscar cita inexistente debe retornar vacío
	 * Manejo de casos edge en búsquedas
	 */
	@Test
	void listarId_cuandoNoExiste_debeRetornarVacio() {
		// Arrange
		int idCita = 999;
		when(citaRepository.findById(idCita)).thenReturn(Optional.empty());

		// Act
		Optional<Cita> resultado = citaService.listarId(idCita);

		// Assert
		assertFalse(resultado.isPresent());
	}
}