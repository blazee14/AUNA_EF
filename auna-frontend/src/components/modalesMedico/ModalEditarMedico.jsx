import React, { useState, useEffect } from 'react'
import especialidadService from '../../services/especialidadService';

export const ModalEditarMedico = ({ show, onClose, onUpdate, medicoActual }) => {
    const [medico, setMedico] = useState(medicoActual);
    const [especialidades, setEspecialidades] = useState([]);

    useEffect(() => {
        setMedico(medicoActual);
    }, [medicoActual]);

    useEffect(() => {
        especialidadService.getAllEspecialidades()
            .then(response => setEspecialidades(response.data))
            .catch(error => console.log("Error al cargar especialidades:", error));
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name === 'especialidad') {
            setMedico(prev => ({
                ...prev,
                especialidad: { idEspecialidad: value }
            }));
        } else {
            setMedico(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onUpdate(medico.idMedico, medico);
    };

    if (!show) return null;

    return (
        <div className="modal-overlay" role="dialog" aria-modal="true" aria-labelledby="modal-editar-medico-title">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 id="modal-editar-medico-title" className="modal-title">Editar Medico</h5>
                        <button
                            type="button"
                            className="modal-closeBtn"
                            onClick={onClose}
                            aria-label="Cerrar edición"
                        ></button>
                    </div>

                    <div className="modal-body">
                        <form onSubmit={handleSubmit}>
                            <div className="modal-row">
                                <div className="modal-col">
                                    <div className="modal-formGroup">
                                        <label className="modal-label">Nombre del Médico</label>
                                        <input
                                            type="text"
                                            className="modal-input"
                                            placeholder="Ingrese nombre del médico"
                                            name="nombreMedico"
                                            value={medico.nombreMedico}
                                            onChange={handleChange}
                                            pattern="[A-Za-zÁÉÍÓÚáéíóúÑñÜü\s'-]+"
                                            title="Solo se permiten letras, espacios, apóstrofes y guiones"
                                            maxLength={60}
                                            required
                                        />
                                    </div>
                                </div>

                                <div className="modal-col">
                                    <div className="modal-formGroup">
                                        <label className="modal-label">Especialidad</label>
                                        <select
                                            className="modal-select"
                                            name="especialidad"
                                            value={medico.especialidad.idEspecialidad}
                                            onChange={handleChange}
                                            required
                                        >
                                            <option value="" disabled hidden>Seleccione una especialidad</option>
                                            {especialidades.map(especialidad => (
                                                <option key={especialidad.idEspecialidad} value={especialidad.idEspecialidad}>
                                                    {especialidad.nombreEspecialidad}
                                                </option>
                                            ))}
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div className="modal-footer">
                                <button
                                    type="button"
                                    className="modal-btn modal-btnSecondary"
                                    onClick={onClose}
                                >
                                    CANCELAR
                                </button>
                                <button
                                    type="submit"
                                    className="modal-btn modal-btnPrimary"
                                >
                                    GUARDAR MÉDICO
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalEditarMedico;