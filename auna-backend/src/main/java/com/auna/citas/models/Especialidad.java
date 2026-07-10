package com.auna.citas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Especialidades")
public class Especialidad extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_especialidad")
	private int idEspecialidad;
	
	@Column(name = "nombre_especialidad",nullable = false, length = 100, unique = true)
	private String nombreEspecialidad;
	
	@Column(name = "descripcion",nullable = false, length = 100)
	private String descripcion;
	
	public Especialidad() {
		// TODO Auto-generated constructor stub
	}

	public String getNombreEspecialidad() {
		return nombreEspecialidad;
	}

	public void setNombreEspecialidad(String nombreEspecialidad) {
		this.nombreEspecialidad = nombreEspecialidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdEspecialidad() {
		return idEspecialidad;
	}
}
