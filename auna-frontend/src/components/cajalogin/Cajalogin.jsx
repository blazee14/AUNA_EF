import React, { useState } from "react";
import "./Cajalogin.css";
import abrazoImg from "../../images/AbrazoESSALUD.png";
import { useNavigate } from "react-router-dom";
import logueoService from "../../services/logueoService";

export const Cajalogin = () => {
  const navigate = useNavigate();

  const [numDocumento, setNumDocumento] = useState("");
  const [password, setPassword] = useState("");

  const permitirSoloNumeros = (e) => {
    const key = e.key;
    const controlKeys = ["Backspace", "Delete", "ArrowLeft", "ArrowRight", "ArrowUp", "ArrowDown", "Tab", "Home", "End"];
    if (controlKeys.includes(key) || e.ctrlKey || e.metaKey) return;
    if (!/^\d$/.test(key)) {
      e.preventDefault();
    }
  };

  const validarDni = (valor) => /^\d{8}$/.test(valor);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!validarDni(numDocumento)) {
      alert("El DNI debe tener exactamente 8 dígitos numéricos.");
      return;
    }

    const loginData = {
      username: numDocumento,
      password: password,
    };

    logueoService.comprobarLogueo(loginData)
      .then((response) => {
        const usuario = response.data;

        localStorage.setItem("usuario", JSON.stringify(usuario));

        if (usuario.tipoUsuario === "ADMINISTRADOR") {
          navigate("/dashboard");
        } else if (usuario.tipoUsuario === "PACIENTE") {
          navigate("/");
        } else {
          alert("Tipo de usuario no reconocido");
        }
      })
      .catch((error) => {
        console.error("Error al iniciar sesión:", error);
        alert("Credenciales incorrectas o error en el servidor.");
      });
  };

  return (
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-lg-5 col-md-7">
          <div className="login-box">
            <h2>INGRESA</h2>
            <form onSubmit={handleSubmit} aria-labelledby="login-title">
              <h3 id="login-title" className="visually-hidden">
                Inicio de sesión
              </h3>
              <div className="input-group mb-4">
                <span className="input-group-text" aria-hidden="true">
                  <i className="fas fa-user"></i>
                </span>
                <label htmlFor="login-numDocumento" className="visually-hidden">Número de documento</label>
                <input
                  id="login-numDocumento"
                  type="number"
                  className="form-control"
                  placeholder="Número de documento"
                  aria-label="Número de documento"
                  autoComplete="username"
                  inputMode="numeric"
                  pattern="\d{8}"
                  minLength={8}
                  maxLength={8}
                  value={numDocumento}
                  onChange={(e) => setNumDocumento(e.target.value)}
                  onKeyDown={permitirSoloNumeros}
                  required
                />
              </div>

              <div className="input-group mb-4">
                <span className="input-group-text" aria-hidden="true">
                  <i className="fas fa-lock"></i>
                </span>
                <label htmlFor="login-password" className="visually-hidden">Contraseña</label>
                <input
                  id="login-password"
                  type="password"
                  className="form-control"
                  placeholder="Contraseña"
                  aria-label="Contraseña"
                  autoComplete="current-password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>

              <button type="submit">INGRESAR</button>
              <a href="/ingresar">¿Olvidaste tu contraseña?</a>
            </form>
          </div>
        </div>
      </div>

      <div className="row justify-content-center">
        <div className="col-10 col-md-8 text-center">
          <img src={abrazoImg} className="family-img" alt="Familia" />
        </div>
      </div>
    </div>
  );
};
