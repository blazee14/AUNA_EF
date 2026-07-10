import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import UsuarioService from "../../services/usuarioService";

const FormularioRegistro = () => {
  const navigate = useNavigate();
  const [formulario, setFormulario] = useState({
    nombre: "",
    apellido: "",
    tipoDocumento: "",
    numDocumento: "",
    correoElectronico: "",
    numeroCelular: "",
    contrasena: "",
    terminos: false,
  });
  const [errores, setErrores] = useState({});
  const [mostrarErrores, setMostrarErrores] = useState(false);

  const permitirSoloLetras = (e) => {
    const key = e.key;
    const controlKeys = ["Backspace", "Delete", "ArrowLeft", "ArrowRight", "ArrowUp", "ArrowDown", "Tab", "Home", "End"];
    if (controlKeys.includes(key) || e.ctrlKey || e.metaKey) return;
    if (!/^[A-Za-zÁÉÍÓÚáéíóúÑñÜü]$/.test(key)) {
      e.preventDefault();
    }
  };

  const permitirSoloNumeros = (e) => {
    const key = e.key;
    const controlKeys = ["Backspace", "Delete", "ArrowLeft", "ArrowRight", "ArrowUp", "ArrowDown", "Tab", "Home", "End"];
    if (controlKeys.includes(key) || e.ctrlKey || e.metaKey) return;
    if (!/^\d$/.test(key)) {
      e.preventDefault();
    }
  };

  const manejarCambio = (e) => {
    const { name, value, type, checked } = e.target;
    let nuevoValor = type === "checkbox" ? checked : value;

    if (name === "nombre" || name === "apellido") {
      nuevoValor = nuevoValor.replace(/[^A-Za-zÁÉÍÓÚáéíóúÑñÜü]/g, "");
    }

    if (name === "numDocumento" || name === "numeroCelular") {
      nuevoValor = nuevoValor.replace(/\D/g, "");
    }

    const actualFormulario = {
      ...formulario,
      [name]: nuevoValor,
    };

    if (name === "tipoDocumento") {
      const maxDocLength = nuevoValor === "DNI" ? 8 : 12;
      actualFormulario.numDocumento = actualFormulario.numDocumento.slice(0, maxDocLength);
    }

    setFormulario(actualFormulario);

    if (mostrarErrores) {
      const siguientesErrores = {
        ...errores,
        [name]: validarCampo(name, actualFormulario[name]),
      };

      if (name === "tipoDocumento") {
        siguientesErrores.numDocumento = validarCampo("numDocumento", actualFormulario.numDocumento);
      }

      setErrores(siguientesErrores);
    }
  };

  const manejarBlur = (e) => {
    const { name, value } = e.target;
    setErrores({
      ...errores,
      [name]: validarCampo(name, value),
    });
    setMostrarErrores(true);
  };

  const validarCampo = (nombre, valor) => {
    const soloLetras = /^[A-Za-zÁÉÍÓÚáéíóúÑñÜü\s'-]+$/;
    const soloLetrasSinEspacios = /^[A-Za-zÁÉÍÓÚáéíóúÑñÜü]+$/;

    switch (nombre) {
      case "nombre":
      case "apellido":
        if (!valor.trim()) return "Este campo es obligatorio";
        if (valor.trim().length < 2) return "Debe tener al menos 2 caracteres";
        return !soloLetrasSinEspacios.test(valor)
          ? "Solo se permiten letras"
          : "";
      case "tipoDocumento":
        return !valor ? "Selecciona un tipo de documento" : "";
      case "numDocumento":
        return !/^\d{8,12}$/.test(valor) ? "Debe tener entre 8 y 12 dígitos" : "";
      case "correoElectronico":
        if (!valor.trim()) return "Este campo es obligatorio";
        if (valor.trim().length < 8) return "Debe tener al menos 8 caracteres";
        return !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(valor)
          ? "Correo electrónico inválido"
          : "";
      case "numeroCelular":
        return !/^\d{9}$/.test(valor) ? "Debe contener 9 dígitos" : "";
      case "contrasena":
        if (!valor) return "Este campo es obligatorio";
        if (valor.length < 8) return "Debe tener al menos 8 caracteres";
        return !/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/.test(valor)
          ? "Mínimo 8 caracteres, con mayúscula, minúscula y número"
          : "";
      case "terminos":
        return !valor ? "Debes aceptar los términos" : "";
      default:
        return "";
    }
  };

  const validarFormulario = () => {
    const nuevosErrores = {};
    Object.keys(formulario).forEach(
      (k) => (nuevosErrores[k] = validarCampo(k, formulario[k]))
    );
    setErrores(nuevosErrores);
    setMostrarErrores(true);
    return !Object.values(nuevosErrores).some((err) => err);
  };

  const manejarEnvio = (e) => {
    e.preventDefault();
    if (validarFormulario()) {
      const usuario = { ...formulario, tipoUsuario: "PACIENTE" };
      UsuarioService.createUsuarios(usuario)
        .then(() => {
          alert("¡Registro exitoso!");
          navigate("/ingresar");
        })
        .catch(() => alert("Error al registrarte."));
    }
  };

  return (
    <form
      onSubmit={manejarEnvio}
      noValidate
      className="small-form"
      aria-labelledby="registro-paciente-title"
    >
      <h2 id="registro-paciente-title" className="visually-hidden">
        Registro de paciente
      </h2>

      <div className="row g-2 mb-2">
        <div className="col">
          <label htmlFor="nombre" className="form-label">
            Nombre
          </label>
          <input
            id="nombre"
            type="text"
            name="nombre"
            placeholder="Nombre"
            autoComplete="given-name"
            pattern="[A-Za-zÁÉÍÓÚáéíóúÑñÜü]+"
            title="Solo letras"
            minLength={2}
            maxLength={40}
            required
            className={`form-control-registro form-control-sm ${errores.nombre && mostrarErrores ? "is-invalid" : ""}`}
            aria-invalid={!!errores.nombre && mostrarErrores}
            aria-describedby={errores.nombre && mostrarErrores ? "nombre-error" : undefined}
            value={formulario.nombre}
            onChange={manejarCambio}
            onBlur={manejarBlur}
            onKeyDown={permitirSoloLetras}
          />
          {errores.nombre && mostrarErrores && (
            <div id="nombre-error" className="invalid-feedback" role="alert">
              {errores.nombre}
            </div>
          )}
        </div>
        <div className="col">
          <label htmlFor="apellido" className="form-label">
            Apellido
          </label>
          <input
            id="apellido"
            type="text"
            name="apellido"
            placeholder="Apellido"
            autoComplete="family-name"
            pattern="[A-Za-zÁÉÍÓÚáéíóúÑñÜü]+"
            title="Solo letras"
            minLength={2}
            maxLength={40}
            required
            className={`form-control-registro form-control-sm ${errores.apellido && mostrarErrores ? "is-invalid" : ""}`}
            aria-invalid={!!errores.apellido && mostrarErrores}
            aria-describedby={errores.apellido && mostrarErrores ? "apellido-error" : undefined}
            value={formulario.apellido}
            onChange={manejarCambio}
            onBlur={manejarBlur}
            onKeyDown={permitirSoloLetras}
          />
          {errores.apellido && mostrarErrores && (
            <div id="apellido-error" className="invalid-feedback" role="alert">
              {errores.apellido}
            </div>
          )}
        </div>
      </div>

      <div className="row g-2 mb-2">
        <div className="col-4">
          <label htmlFor="tipoDocumento" className="form-label">
            Tipo de documento
          </label>
          <select
            id="tipoDocumento"
            name="tipoDocumento"
            className={`form-select form-select-sm ${errores.tipoDocumento && mostrarErrores ? "is-invalid" : ""}`}
            aria-invalid={!!errores.tipoDocumento && mostrarErrores}
            aria-describedby={errores.tipoDocumento && mostrarErrores ? "tipoDocumento-error" : undefined}
            value={formulario.tipoDocumento}
            onChange={manejarCambio}
            onBlur={manejarBlur}
            required
          >
            <option value="">Tipo</option>
            <option value="DNI">DNI</option>
            <option value="CE">CE</option>
          </select>
          {errores.tipoDocumento && mostrarErrores && (
            <div id="tipoDocumento-error" className="invalid-feedback" role="alert">
              {errores.tipoDocumento}
            </div>
          )}
        </div>
        <div className="col">
          <label htmlFor="numDocumento" className="form-label">
            Nro Documento
          </label>
          <input
            id="numDocumento"
            type="text"
            name="numDocumento"
            placeholder="Nro Documento"
            inputMode="numeric"
            autoComplete="off"
            pattern="\d{8,12}"
            title={formulario.tipoDocumento === "DNI" ? "DNI debe tener 8 dígitos numéricos" : formulario.tipoDocumento === "CE" ? "CE debe tener 12 dígitos numéricos" : "Debe tener entre 8 y 12 dígitos numéricos"}
            maxLength={formulario.tipoDocumento === "DNI" ? 8 : formulario.tipoDocumento === "CE" ? 12 : 12}
            required
            className={`form-control-registro form-control-sm ${errores.numDocumento && mostrarErrores ? "is-invalid" : ""}`}
            aria-invalid={!!errores.numDocumento && mostrarErrores}
            aria-describedby={errores.numDocumento && mostrarErrores ? "numDocumento-error" : undefined}
            value={formulario.numDocumento}
            onChange={manejarCambio}
            onBlur={manejarBlur}
            onKeyDown={permitirSoloNumeros}
          />
          {errores.numDocumento && mostrarErrores && (
            <div id="numDocumento-error" className="invalid-feedback" role="alert">
              {errores.numDocumento}
            </div>
          )}
        </div>
      </div>

      <div className="mb-2">
        <label htmlFor="correoElectronico" className="form-label">
          Correo electrónico
        </label>
        <input
          id="correoElectronico"
          type="email"
          name="correoElectronico"
          placeholder="Correo electrónico"
          autoComplete="email"
          minLength={8}
          required
          className={`form-control-registro form-control-sm ${errores.correoElectronico && mostrarErrores ? "is-invalid" : ""}`}
          aria-invalid={!!errores.correoElectronico && mostrarErrores}
          aria-describedby={errores.correoElectronico && mostrarErrores ? "correoElectronico-error" : undefined}
          value={formulario.correoElectronico}
          onChange={manejarCambio}
          onBlur={manejarBlur}
        />
        {errores.correoElectronico && mostrarErrores && (
          <div id="correoElectronico-error" className="invalid-feedback" role="alert">
            {errores.correoElectronico}
          </div>
        )}
      </div>

      <div className="mb-2">
        <label htmlFor="numeroCelular" className="form-label">
          Teléfono
        </label>
        <input
          id="numeroCelular"
          type="tel"
          name="numeroCelular"
          placeholder="Teléfono"
          autoComplete="tel"
          inputMode="numeric"
          pattern="\d{9}"
          title="Debe contener exactamente 9 dígitos numéricos"
          maxLength={9}
          required
          className={`form-control-registro form-control-sm ${errores.numeroCelular && mostrarErrores ? "is-invalid" : ""}`}
          aria-invalid={!!errores.numeroCelular && mostrarErrores}
          aria-describedby={errores.numeroCelular && mostrarErrores ? "numeroCelular-error" : undefined}
          value={formulario.numeroCelular}
          onChange={manejarCambio}
          onBlur={manejarBlur}
          onKeyDown={permitirSoloNumeros}
        />
        {errores.numeroCelular && mostrarErrores && (
          <div id="numeroCelular-error" className="invalid-feedback" role="alert">
            {errores.numeroCelular}
          </div>
        )}
      </div>

      <div className="mb-2">
        <label htmlFor="contrasena" className="form-label">
          Contraseña
        </label>
        <input
          id="contrasena"
          type="password"
          name="contrasena"
          placeholder="Contraseña"
          autoComplete="new-password"
          minLength={8}
          pattern="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}"
          title="Mínimo 8 caracteres, con mayúscula, minúscula y número"
          required
          className={`form-control-registro form-control-sm ${errores.contrasena && mostrarErrores ? "is-invalid" : ""}`}
          aria-invalid={!!errores.contrasena && mostrarErrores}
          aria-describedby={errores.contrasena && mostrarErrores ? "contrasena-error" : undefined}
          value={formulario.contrasena}
          onChange={manejarCambio}
          onBlur={manejarBlur}
        />
        {errores.contrasena && mostrarErrores && (
          <div id="contrasena-error" className="invalid-feedback" role="alert">
            {errores.contrasena}
          </div>
        )}
      </div>

      <div className="form-check mb-3 text-start">
        <input
          className={`form-check-input ${errores.terminos && mostrarErrores ? "is-invalid" : ""}`}
          type="checkbox"
          name="terminos"
          checked={formulario.terminos}
          onChange={manejarCambio}
          id="terminos"
          aria-invalid={!!errores.terminos && mostrarErrores}
          aria-describedby={errores.terminos && mostrarErrores ? "terminos-error" : undefined}
        />
        <label className="form-check-label ms-2" htmlFor="terminos">
          Acepto los <a href="/registrar">Términos y Condiciones</a> y <a href="/registrar">Política de Privacidad</a>
        </label>
        {errores.terminos && mostrarErrores && (
          <div id="terminos-error" className="invalid-feedback d-block" role="alert">
            {errores.terminos}
          </div>
        )}
      </div>

      <button type="submit" className="btn btn-submit btn-sm w-100">
        REGISTRARSE
      </button>
    </form>
  );
};

export default FormularioRegistro;
