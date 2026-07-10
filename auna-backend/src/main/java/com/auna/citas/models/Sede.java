package com.auna.citas.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Sedes")
public class Sede extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sedes")
	private int idSedes;
	
	@OneToMany(mappedBy = "sede", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DetalleSede> detalles;
	
	@Column(name = "nombre_distrito",nullable = false, length = 100)
	private String nombreDistrito;
	
	@Column(name = "nombre_sede", nullable = false, length = 100)
	private String nombreSede;
	
	public Sede() {
		// TODO Auto-generated constructor stub
	}

	public String getNombreDistrito() {
		return nombreDistrito;
	}

	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}

	public String getNombreSede() {
		return nombreSede;
	}

	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}

	public int getIdSedes() {
		return idSedes;
	}
}
