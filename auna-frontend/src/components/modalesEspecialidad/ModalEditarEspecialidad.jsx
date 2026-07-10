import React, { useState, useEffect } from 'react'

export const ModalEditarEspecialidad = ({ show, onClose, onUpdate, especialidadActual }) => {
    const [especialidad, setEspecialidad] = useState(especialidadActual);

    useEffect(() => {
        setEspecialidad(especialidadActual);
    }, [especialidadActual]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEspecialidad(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onUpdate(especialidad.idEspecialidad, especialidad);
    };

    if (!show) return null;

    return (
        <div className="modal-overlay" role="dialog" aria-modal="true" aria-labelledby="modal-editar-especialidad-title">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 id="modal-editar-especialidad-title" className="modal-title">Editar Especialidad</h5>
                        <button
                            type="button"
                            className="modal-closeBtn"
                            onClick={onClose}
                            aria-label="Cerrar edición"
                        ></button>
                    </div>

                    <div className="modal-body">
                        <form onSubmit={handleSubmit}>
                            <div className="modal-formGroup">
                                <label className="modal-label">Nombre</label>
                                <input
                                    type="text"
                                    className="modal-input"
                                    placeholder="Ingrese su nombre"
                                    name="nombreEspecialidad"
                                    value={especialidad.nombreEspecialidad}
                                    onChange={handleChange}
                                    pattern="[A-Za-zÁÉÍÓÚáéíóúÑñÜü\s'-]+"
                                    title="Solo se permiten letras, espacios, apóstrofes y guiones"
                                    maxLength={60}
                                    required
                                />
                            </div>

                            <div className="modal-formGroup">
                                <label className="modal-label">Descripción</label>
                                <textarea
                                    className="modal-textarea"
                                    placeholder="Ingrese su descripcion"
                                    name="descripcion"
                                    value={especialidad.descripcion}
                                    onChange={handleChange}
                                    rows={4}
                                    maxLength={100}
                                    required
                                />
                                <small className="text-muted">
                                    {especialidad.descripcion.length}/100 caracteres
                                </small>
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
                                    GUARDAR REGISTRO
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ModalEditarEspecialidad;