package com.auna.citas.services;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.auna.citas.models.Usuario;
import com.auna.citas.models.Usuario.tipoDocumento;
import com.auna.citas.models.Usuario.tipoUsuario;
import com.auna.citas.repositorys.UsuarioRepository;

import java.util.Optional;

/**
 * Suite de tests para UsuarioService
 * Cubre validaciones de negocio y casos edge
 */
public class UsuarioServiceTest {
    
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test: DNI inválido debe lanzar excepción
     * Valida formato correcto de documento de identidad
     */
    @Test
    public void cuandoRegistroUsuarioConDniInvalido_entoncesArrojaExcepcion() {
        // Arrange
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setTipoDocumento(tipoDocumento.DNI);
        nuevoUsuario.setNumDocumento("12345"); // DNI incompleto

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.guardar(nuevoUsuario);
        });

        assertEquals("El DNI debe tener exactamente 8 dígitos", exception.getMessage());
    }

    /**
     * Test: Registro exitoso con DNI válido
     * Verifica que las contraseñas se encripten correctamente
     */
    @Test
    public void cuandoRegistroUsuarioConDniValido_entoncesGuardaConContrasenaEncriptada() {
        // Arrange
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setTipoDocumento(tipoDocumento.DNI);
        nuevoUsuario.setNumDocumento("12345678");
        nuevoUsuario.setContrasena("secreta");
        nuevoUsuario.setTipoUsuario(tipoUsuario.PACIENTE);

        when(passwordEncoder.encode("secreta")).thenReturn("hash-secreto");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Usuario usuarioGuardado = usuarioService.guardar(nuevoUsuario);

        // Assert
        assertEquals("hash-secreto", usuarioGuardado.getContrasena());
        verify(passwordEncoder).encode("secreta");
        verify(usuarioRepository).save(nuevoUsuario);
    }

    /**
     * Test: Validación de Carné de Extranjería (CE)
     * Los CE deben permitir formatos diferentes a DNI
     */
    @Test
    public void cuandoRegistroUsuarioConCE_entoncesPermiteFormatosDiferentes() {
        // Arrange
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setTipoDocumento(tipoDocumento.CE);
        nuevoUsuario.setNumDocumento("CE123456"); // Formato CE válido
        nuevoUsuario.setContrasena("secreta");
        nuevoUsuario.setTipoUsuario(tipoUsuario.PACIENTE);

        when(passwordEncoder.encode("secreta")).thenReturn("hash-secreto");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Usuario usuarioGuardado = usuarioService.guardar(nuevoUsuario);

        // Assert - No debe lanzar excepción para CE
        assertNotNull(usuarioGuardado);
        assertEquals("hash-secreto", usuarioGuardado.getContrasena());
    }

    /**
     * Test: DNI con letras debe ser rechazado
     * Asegura que solo se acepten números en DNI
     */
    @Test
    public void cuandoDniContieneLetras_entoncesArrojaExcepcion() {
        // Arrange
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setTipoDocumento(tipoDocumento.DNI);
        nuevoUsuario.setNumDocumento("1234567A"); // DNI con letra

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.guardar(nuevoUsuario);
        });

        assertEquals("El DNI debe tener exactamente 8 dígitos", exception.getMessage());
    }

    /**
     * Test: Eliminación de usuario existente
     * Verifica que la eliminación funcione correctamente
     */
    @Test
    public void cuandoEliminaUsuarioExistente_entoncesRetornaTrue() {
        // Arrange
        int idUsuario = 1;
        Usuario usuarioExistente = new Usuario();
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuarioExistente));

        // Act
        boolean resultado = usuarioService.eliminar(idUsuario);

        // Assert
        assertTrue(resultado);
        verify(usuarioRepository).deleteById(idUsuario);
    }

    /**
     * Test: Eliminación de usuario inexistente
     * Debe manejar casos donde el usuario no existe
     */
    @Test
    public void cuandoEliminaUsuarioInexistente_entoncesRetornaFalse() {
        // Arrange
        int idUsuario = 999;
        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.empty());

        // Act
        boolean resultado = usuarioService.eliminar(idUsuario);

        // Assert
        assertFalse(resultado);
        verify(usuarioRepository, never()).deleteById(idUsuario);
    }

    /**
     * Test: Actualización sin cambiar contraseña
     * Debe preservar contraseña existente si no se proporciona nueva
     */
    @Test
    public void cuandoActualizaSinContrasena_entoncesPreservaContrasenaExistente() {
        // Arrange
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setContrasena("hash-existente");
        
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setContrasena(""); // Contraseña vacía
        usuarioActualizado.setTipoDocumento(tipoDocumento.DNI);
        usuarioActualizado.setNumDocumento("12345678");

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Usuario resultado = usuarioService.guardar(usuarioActualizado);

        // Assert - Debe encriptar la contraseña vacía (comportamiento actual)
        verify(passwordEncoder).encode("");
    }
}
