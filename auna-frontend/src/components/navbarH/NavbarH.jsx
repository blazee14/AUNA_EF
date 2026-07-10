import React from 'react'
import './NavbarH.css';
import LogoHome from '../../images/LogoHome.png'
import { useLocation } from 'react-router-dom';

export const NavbarH = () => {
    const location = useLocation();

    // Determinar el texto basado en la ruta actual
    const getNavbarText = () => {
        if (location.pathname.includes('historialCitas')) {
            return 'HISTORIAL DE TUS CITAS';
        }
        return 'RESERVA TUS CITAS MEDICAS';
    };

    return (
        <header className="navbarH-container" role="banner" aria-label="Barra de navegación de paciente">
            <div className="navbarH-logo-section">
                <img
                    src={LogoHome}
                    alt="EsSalud Logo"
                />
            </div>
            <div className="navbarH-paciente-section">
                <div className="navbarH-paciente-text">{getNavbarText()}</div>
            </div>
        </header>
    )
}
