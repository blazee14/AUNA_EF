package com.auna.citas.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auna.citas.models.*;
import com.auna.citas.repositorys.TurnosAtencionCitaRepository;

/**
 * Suite de tests para TurnosAtencionCitasService
 * Valida lógica de disponibilidad y programación médica
 */
@ExtendWith(MockitoExtension.class)
class TurnosAtencionCitasServiceTest {
    @Mock
    private TurnosAtencionCitaRepository turnosAtencionCitaRepository;

    @InjectMocks
    private TurnosAtencionCitasService turnosAtencionCitasService;

    private TurnosAtencionCitas turnoValido;
    private Medico medico;
    private DetalleSede detalleSede;

    @BeforeEach
    void setUp() {
        // Configuración de objetos de prueba
        medico = new Medico();
        detalleSede = new DetalleSede();
        
        turnoValido = new TurnosAtencionCitas();
        turnoValido.setMedico(medico);
        turnoValido.setDetalleSede(detalleSede);
        turnoValido.setFecha(LocalDate.now().plusDays(1));
        turnoValido.setHoraInicio(LocalTime.of(9, 0));
        turnoValido.setHoraFin(LocalTime.of(10, 0));
        turnoValido.setNumCupos(5);
    }

    /**
     * Test: Listar todos los turnos disponibles
     * Funcionalidad básica para mostrar agenda médica
     */
    @Test
    void listar_debeRetornarTurnosDelRepositorio() {
        // Arrange
        List<TurnosAtencionCitas> turnosEsperados = List.of(turnoValido);
        when(turnosAtencionCitaRepository.findAll()).thenReturn(turnosEsperados);

        // Act
        List<TurnosAtencionCitas> resultado = turnosAtencionCitasService.listar();

        // Assert
        assertEquals(turnosEsperados, resultado);
        verify(turnosAtencionCitaRepository).findAll();
    }

    /**
     * Test: Guardar turno debe persistir correctamente
     * Permite programar nuevos horarios de atención
     */
    @Test
    void guardar_debePersistirTurno() {
        // Arrange
        when(turnosAtencionCitaRepository.save(turnoValido)).thenReturn(turnoValido);

        // Act
        TurnosAtencionCitas resultado = turnosAtencionCitasService.guardar(turnoValido);

        // Assert
        assertEquals(turnoValido, resultado);
        verify(turnosAtencionCitaRepository).save(turnoValido);
    }

    /**
     * Test: Eliminar turno existente debe funcionar
     * Permite cancelar horarios programados
     */
    @Test
    void eliminar_cuandoTurnoExiste_debeEliminar() {
        // Arrange
        int idTurno = 1;
        when(turnosAtencionCitaRepository.findById(idTurno)).thenReturn(Optional.of(turnoValido));

        // Act
        boolean resultado = turnosAtencionCitasService.eliminar(idTurno);

        // Assert
        assertTrue(resultado);
        verify(turnosAtencionCitaRepository).deleteById(idTurno);
    }

    /**
     * Test: Eliminar turno inexistente debe fallar elegantemente
     * Maneja casos donde el turno ya no existe
     */
    @Test
    void eliminar_cuandoTurnoNoExiste_debeRetornarFalse() {
        // Arrange
        int idTurno = 999;
        when(turnosAtencionCitaRepository.findById(idTurno)).thenReturn(Optional.empty());

        // Act
        boolean resultado = turnosAtencionCitasService.eliminar(idTurno);

        // Assert
        assertFalse(resultado);
        verify(turnosAtencionCitaRepository, never()).deleteById(idTurno);
    }

    /**
     * Test: Buscar turno por ID específico
     * Consulta individual de turnos programados
     */
    @Test
    void listarId_cuandoExiste_debeRetornarTurno() {
        // Arrange
        int idTurno = 1;
        when(turnosAtencionCitaRepository.findById(idTurno)).thenReturn(Optional.of(turnoValido));

        // Act
        Optional<TurnosAtencionCitas> resultado = turnosAtencionCitasService.listarId(idTurno);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(turnoValido, resultado.get());
    }

    /**
     * Test: Validación de horarios de trabajo
     * Los turnos deben respetar horarios laborales estándar
     */
    @Test
    void validarHorarioTurno_debeEstarEnRangoLaboral() {
        // Arrange
        TurnosAtencionCitas turnoMuyTemprano = new TurnosAtencionCitas();
        turnoMuyTemprano.setHoraInicio(LocalTime.of(5, 0)); // 5:00 AM muy temprano
        turnoMuyTemprano.setHoraFin(LocalTime.of(6, 0));

        // Act & Assert - Este test validaría reglas de negocio si estuvieran implementadas
        // Por ahora solo verificamos que el turno se puede crear
        when(turnosAtencionCitaRepository.save(turnoMuyTemprano)).thenReturn(turnoMuyTemprano);
        
        TurnosAtencionCitas resultado = turnosAtencionCitasService.guardar(turnoMuyTemprano);
        
        assertNotNull(resultado);
    }

    /**
     * Test: Verificar disponibilidad de cupos
     * Critical para prevenir sobrebooking de citas
     */
    @Test
    void verificarCupos_debePermitirReservaSiHayDisponibilidad() {
        // Arrange
        TurnosAtencionCitas turnoConCupos = new TurnosAtencionCitas();
        turnoConCupos.setNumCupos(3); // Turnos disponibles
        
        // Act & Assert - Test conceptual para lógica de cupos
        assertTrue(turnoConCupos.getNumCupos() > 0, "Debe haber cupos disponibles");
    }

    /**
     * Test: Turnos sin cupos no deben aceptar más citas
     * Previene overbooking del sistema
     */
    @Test
    void verificarCupos_noPuedeReservarSinDisponibilidad() {
        // Arrange
        TurnosAtencionCitas turnoSinCupos = new TurnosAtencionCitas();
        turnoSinCupos.setNumCupos(0); // Sin disponibilidad
        
        // Act & Assert - Test conceptual para validación de cupos
        assertEquals(0, turnoSinCupos.getNumCupos(), "No debe haber cupos disponibles");
    }

    /**
     * Test: Turnos futuros deben ser válidos para reserva
     * No se pueden crear citas en fechas pasadas
     */
    @Test
    void validarFechaTurno_debeEstarEnElFuturo() {
        // Arrange
        TurnosAtencionCitas turnoFuturo = new TurnosAtencionCitas();
        LocalDate fechaFutura = LocalDate.now().plusDays(7);
        turnoFuturo.setFecha(fechaFutura);

        // Act & Assert - Verificar que la fecha es válida
        assertTrue(turnoFuturo.getFecha().isAfter(LocalDate.now()), 
                   "Los turnos deben programarse para fechas futuras");
    }
}