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

import com.auna.citas.models.DetalleSede;
import com.auna.citas.repositorys.DetalleSedeRepository;

@ExtendWith(MockitoExtension.class)
class DetalleSedeServiceTest {
	@Mock
	private DetalleSedeRepository detalleSedeRepository;

	@InjectMocks
	private DetalleSedeService detalleSedeService;

	@Test
	void listar_debeRetornarLosDetallesDelRepositorio() {
		DetalleSede detalleSede = new DetalleSede();
		when(detalleSedeRepository.findAll()).thenReturn(List.of(detalleSede));

		List<DetalleSede> resultado = detalleSedeService.listar();

		assertSame(detalleSede, resultado.get(0));
	}

	@Test
	void guardar_debeDelegarEnElRepositorio() {
		DetalleSede detalleSede = new DetalleSede();
		when(detalleSedeRepository.save(detalleSede)).thenReturn(detalleSede);

		DetalleSede resultado = detalleSedeService.guardar(detalleSede);

		assertSame(detalleSede, resultado);
	}

	@Test
	void eliminar_cuandoExisteDebeBorrar() {
		when(detalleSedeRepository.findById(1)).thenReturn(Optional.of(new DetalleSede()));

		boolean eliminado = detalleSedeService.eliminar(1);

		assertTrue(eliminado);
		verify(detalleSedeRepository).deleteById(1);
	}

	@Test
	void eliminar_cuandoNoExisteDebeRetornarFalse() {
		when(detalleSedeRepository.findById(1)).thenReturn(Optional.empty());

		boolean eliminado = detalleSedeService.eliminar(1);

		assertFalse(eliminado);
	}
}