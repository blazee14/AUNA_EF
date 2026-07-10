import React, { useState } from 'react';

const ModalAgregarUsuario = ({ show, onClose, onSave }) => {
    const usuarioInicial = {
        tipoDocumento: '',
        numDocumento: '',
        nombre: '',
        apellido: '',
        numeroCelular: '',
        correoElectronico: '',
        tipoUsuario: 'PACIENTE',
        contrasena: ''
    };

    const [usuario, setUsuario] = useState(usuarioInicial);


    const handleChange = (e) => {
        const { name, value } = e.target;
        setUsuario(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSave(usuario);
        setUsuario(usuarioInicial);
    };

    if (!show) return null;

    return (
        <div className="modal-overlay" role="dialog" aria-modal="true" aria-labelledby="modal-agregar-usuario-title">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 id="modal-agregar-usuario-title" className="modal-title">Registro</h5>
                        <button
                            type="button"
                            className="modal-closeBtn"
                            onClick={onClose}
                            aria-label="Cerrar registro"
                        ></button>
                    </div>

                    <div className="modal-body">
                        <form onSubmit={handleSubmit}>
                            <div className="modal-row">
                                <div className="modal-col">
                                    <div className="modal-formGroup">
                                        <label className="modal-label">Tipo de documento</label>
                                        <select
                                            className="modal-select"
                                            name="tipoDocumento"
                                            value={usuario.tipoDocumento}
                                            onChange={handleChange}
                                            required
                                        >
                                            <option value="" disabled hidden>Seleccione su documento</option>
                                            <option value="DNI">DNI</option>
                                            <option value="CE">CE</option>
                                        </select>
                                    </div>
                                </div>
                                <div className="modal-col">
                                    <div className="modal-formGroup">
                                        <label className="modal-label">Número de documento</label>
                                        <input
                                            type="text"
                                            className="modal-input"
                                            placeholder="Ingrese número de documento"
                                            name="numDocumento"
                                            value={usuario.numDocumento}
                                            onChange={handleChange}
                                            pattern="\d{8,12}"
                                            title="Debe contener entre 8 y 12 dígitos numéricos"
                                            required
                                        />
                                    </div>
                                </div>
                            </div>

                            <div className="modal-row">
                                <div className="modal-col">
                                    <div className="modal-formGroup">
                                        <label className="modal-label">Nombre</label>
                                        <input
                                            type="text"
                                            className="modal-input"
                                            placeholder="Ingrese nombre del usuario"
                                            name="nombre"
                                            value={usuario.nombre}
                                            onChange={handleChange}
                                            pattern="[A-Za-zÁÉÍÓÚáéíóúÑñÜü\s'-]+"
                                            title="Solo se permiten letras, espacios, apóstrofes y guiones"
                                            maxLength={40}
                                            required
                                        />
                                    </div>
                                </div>
                                <div className="modal-col">
                                    <div className="modal-formGroup">
                                        <label className="modal-label">Apellido</label>
                                        <input
                                            type="text"
                                            className="modal-input"
                                            placeholder="Ingrese apellido del usuario"
                                            name="apellido"
                                            value={usuario.apellido}
                                            onChange={handleChange}
                                            pattern="[A-Za-zÁÉÍÓÚáéíóúÑñÜü\s'-]+"
                                            title="Solo se permiten letras, espacios, apóstrofes y guiones"
                                            maxLength={40}
                                            required
                                        />
                                    </div>
                                </div>
                            </div>

                            <div className="modal-row">
                                <div className="modal-col">
                                    <div className="modal-formGroup">
                                        <label className="modal-label">Teléfono</label>
                                        <input
                                            type="tel"
                                            className="modal-input"
                                            placeholder="999999999"
                                            name="numeroCelular"
                                            value={usuario.numeroCelular}
                                            onChange={handleChange}
                                            pattern="\d{9}"
                                            title="Debe contener exactamente 9 dígitos numéricos"
                                            required
                                        />
                                    </div>
                                </div>
                                <div className="modal-col">
                                    <div className="modal-formGroup">
                                        <label className="modal-label">Correo</label>
                                        <input
                                            type="email"
                                            className="modal-input"
                                            placeholder="Ingrese correo del usuario"
                                            name="correoElectronico"
                                            value={usuario.correoElectronico}
                                            onChange={handleChange}
                                            required
                                        />
                                    </div>
                                </div>
                            </div>

                            <div className="modal-row">
                                <div className="modal-col">
                                    <div className="modal-formGroup">
                                        <label className="modal-label">Tipo de usuario</label>
                                        <select
                                            className="modal-select"
                                            name="tipoUsuario"
                                            value={usuario.tipoUsuario}
                                            onChange={handleChange}
                                            required
                                        >
                                            <option value="PACIENTE">PACIENTE</option>
                                            <option value="ADMINISTRADOR">ADMINISTRADOR</option>
                                        </select>
                                    </div>
                                </div>
                                <div className="modal-col">
                                    <div className="modal-formGroup">
                                        <label className="modal-label">Contraseña</label>
                                        <input
                                            type="password"
                                            className="modal-input"
                                            placeholder="Ingrese su contraseña"
                                            name="contrasena"
                                            value={usuario.contrasena}
                                            onChange={handleChange}
                                            pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}"
                                            title="Mínimo 8 caracteres, al menos 1 mayúscula, 1 minúscula y 1 número"
                                            required
                                        />
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
                                    GUARDAR CAMBIOS
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ModalAgregarUsuario;