package com.auna.citas.models;

import java.time.LocalTime;

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
@Table(name="Citas")
public class Cita {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cita")
	private int idCita;
	
	@ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)  // FK en tabla cita
    private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name = "id_turnos_atencion_citas", nullable = false)
	private TurnosAtencionCitas turnoAtencion;
	
	@Column(name = "hora_cita", nullable = false)
	private LocalTime horaCita;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false)
	private EstadoCita estado;

	public Cita() {
		
	}
	
	public TurnosAtencionCitas getTurnoAtencion() {
		return turnoAtencion;
	}

	public void setTurnoAtencion(TurnosAtencionCitas turnoAtencion) {
		this.turnoAtencion = turnoAtencion;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public LocalTime getHoraCita() {
		return horaCita;
	}

	public void setHoraCita(LocalTime horaCita) {
		this.horaCita = horaCita;
	}

	public EstadoCita getEstado() {
		return estado;
	}

	public void setEstado(EstadoCita estado) {
		this.estado = estado;
	}

	public int getIdCita() {
		return idCita;
	}

	public enum EstadoCita {
	    POR_ATENDER,
	    ATENDIDA,
	    CANCELADA
	}
}
