package com.auna.citas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Jornada_medicos")
public class JornadaMedicos extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_jornada_medicos")
	private int idJornadaMedicos;
	
	@ManyToOne
	@JoinColumn(name = "id_medico", nullable = false)
	private Medico medico;

	@Enumerated(EnumType.STRING)
	@Column(name = "dia_semana", nullable = false)
	private DiaSemana diaSemana;
	
	public JornadaMedicos() {
		// TODO Auto-generated constructor stub
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}

	public int getIdJornadaMedicos() {
		return idJornadaMedicos;
	}

	public enum DiaSemana {
	    LUNES,
	    MARTES,
	    MIERCOLES,
	    JUEVES,
	    VIERNES,
	    SABADO,
	    DOMINGO
	}
}
