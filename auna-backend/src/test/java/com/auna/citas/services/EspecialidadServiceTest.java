package com.auna.citas.services;

import com.auna.citas.models.Especialidad;
import com.auna.citas.repositorys.EspecialidadRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EspecialidadService - Ciclo TDD")
class EspecialidadServiceTest {

    @Mock
    private EspecialidadRepository data;

    @InjectMocks
    private EspecialidadService especialidadService;

    private Especialidad especialidad;

    @BeforeEach
    void setUp() {
        especialidad = new Especialidad();
        especialidad.setNombreEspecialidad("Neurología");
        especialidad.setDescripcion("Especialidad del sistema nervioso");
    }

    // ── LISTAR ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("listar() debe retornar lista de especialidades")
    void testListar_retornaListaNoVacia() {
        Especialidad e2 = new Especialidad();
        e2.setNombreEspecialidad("Cardiología");
        e2.setDescripcion("Especialidad del corazón");
        when(data.findAll()).thenReturn(Arrays.asList(especialidad, e2));

        List<Especialidad> resultado = especialidadService.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(data, times(1)).findAll();
    }

    @Test
    @DisplayName("listar() retorna lista vacía si no hay registros")
    void testListar_retornaListaVaciaSinRegistros() {
        when(data.findAll()).thenReturn(List.of());

        List<Especialidad> resultado = especialidadService.listar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ── LISTAR POR ID ────────────────────────────────────────────────────────

    @Test
    @DisplayName("listarId() retorna especialidad existente")
    void testListarId_especialidadExistente() {
        when(data.findById(1)).thenReturn(Optional.of(especialidad));

        Optional<Especialidad> resultado = especialidadService.listarId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Neurología", resultado.get().getNombreEspecialidad());
    }

    @Test
    @DisplayName("listarId() retorna Optional vacío si ID no existe")
    void testListarId_idNoExistente() {
        when(data.findById(999)).thenReturn(Optional.empty());

        Optional<Especialidad> resultado = especialidadService.listarId(999);

        assertFalse(resultado.isPresent());
    }

    // ── GUARDAR ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("guardar() persiste y retorna la especialidad guardada")
    void testGuardar_especialidadValida() {
        when(data.save(any(Especialidad.class))).thenReturn(especialidad);

        Especialidad resultado = especialidadService.guardar(especialidad);

        assertNotNull(resultado);
        assertEquals("Neurología", resultado.getNombreEspecialidad());
        verify(data, times(1)).save(especialidad);
    }

    // ── ELIMINAR ─────────────────────────────────────────────────────────────

        @Test
        @DisplayName("eliminar() retorna true cuando la especialidad existe")
        void testEliminar_especialidadExistente_retornaTrue() {
        when(data.findById(1)).thenReturn(Optional.of(especialidad));
        doNothing().when(data).deleteById(1);

        boolean resultado = especialidadService.eliminar(1);

        assertTrue(resultado);
        verify(data, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("eliminar() retorna false cuando el ID no existe")
    void testEliminar_idNoExistente_retornaFalse() {
        when(data.findById(999)).thenReturn(Optional.empty());

        boolean resultado = especialidadService.eliminar(999);

        assertFalse(resultado);
        verify(data, never()).deleteById(anyInt());
    }
}
