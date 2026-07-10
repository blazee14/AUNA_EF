package com.auna.citas.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Turnos_atencion_citas")
public class TurnosAtencionCitas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_turnos_atencion_citas")
	private int idTurnosAtencionCitas;
	
	@ManyToOne
	@JoinColumn(name = "id_medico", nullable = true)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Medico medico;
	
	@ManyToOne
	@JoinColumn(name = "id_detalle_sede", nullable = false)
	private DetalleSede detalleSede;
	
	@OneToMany(mappedBy = "turnoAtencion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Cita> citas;
	
	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;
	
	@Column(name = "hora_inicio", nullable = false)
	private LocalTime horaInicio;
	
	@Column(name = "hora_fin", nullable = false)
	private LocalTime horaFin;
	
	@Column(name = "num_cupos", nullable = false)
	private int numCupos;
	
	public TurnosAtencionCitas() {
		// TODO Auto-generated constructor stub
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public DetalleSede getDetalleSede() {
		return detalleSede;
	}

	public void setDetalleSede(DetalleSede detalleSede) {
		this.detalleSede = detalleSede;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public int getNumCupos() {
		return numCupos;
	}

	public void setNumCupos(int numCupos) {
		this.numCupos = numCupos;
	}

	public int getIdTurnosAtencionCitas() {
		return idTurnosAtencionCitas;
	}

}
