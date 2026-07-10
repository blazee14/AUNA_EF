package com.auna.citas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auna.citas.interfaces.services.IUsuarioService;
import com.auna.citas.models.Usuario;
import com.auna.citas.repositorys.UsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService{
	@Autowired
	private UsuarioRepository data;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<Usuario> listar() {
		List<Usuario> listar = (List<Usuario>) data.findAll();
		return listar;
	}

	@Override
	public Optional<Usuario> listarId(int idUsuario) {
		return data.findById(idUsuario);
	}

	@Override
	public Usuario guardar(Usuario u) {
		// Validación de DNI
		if (u.getTipoDocumento() == Usuario.tipoDocumento.DNI) {
			if (u.getNumDocumento() == null || u.getNumDocumento().length() != 8 || !u.getNumDocumento().matches("\\d{8}")) {
				throw new IllegalArgumentException("El DNI debe tener exactamente 8 dígitos");
			}
		}
		
		String usuarioContrasena = u.getContrasena();
		String contrasenaEncode = passwordEncoder.encode(usuarioContrasena);
		
		Usuario UsuarioGuardar = u;
		UsuarioGuardar.setContrasena(contrasenaEncode);
		
		return data.save(UsuarioGuardar);
	}

	@Override
	public boolean eliminar(int idUsuario) {
		Optional<Usuario> usuarioOpt = listarId(idUsuario);
		if (usuarioOpt.isPresent()) {
			data.deleteById(idUsuario);
			return true;
		}
		return false;
	}
}
