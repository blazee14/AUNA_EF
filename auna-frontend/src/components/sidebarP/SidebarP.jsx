import React, { useState, useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import "./SidebarP.css";

// Recibimos las nuevas props para el color de fondo y el tamaño de la fuente
export const SidebarP = ({ setBackgroundColor, currentBackgroundColor, setFontSizeScale, currentFontSizeScale, setColorblindMode, currentColorblindMode, setFocusMode, currentFocusMode, setHighlightInteractions, currentHighlightInteractions }) => {
    const [collapsed, setCollapsed] = useState(false);
    const [isMobile, setIsMobile] = useState(false);
    const navigate = useNavigate();

    // Recuperar usuario (Asegúrate de que 'usuario' existe en localStorage)
    const usuario = JSON.parse(localStorage.getItem("usuario"));

    // Detecta si es móvil al cargar y al cambiar el tamaño de la pantalla
    useEffect(() => {
        const checkIfMobile = () => {
            setIsMobile(window.innerWidth <= 768);
        };

        checkIfMobile();
        window.addEventListener("resize", checkIfMobile);

        return () => {
            window.removeEventListener("resize", checkIfMobile);
        };
    }, []);

    useEffect(() => {
        if (isMobile) {
            setCollapsed(true);
        } else {
            setCollapsed(false);
        }
    }, [isMobile]);

    const toggleSidebar = () => {
        setCollapsed(!collapsed);
    };

    const handleLogout = () => {
        localStorage.removeItem("usuario");
        navigate("/ingresar");
    };

    // Función para cambiar el color de fondo (Blanco <-> Negro)
    const toggleBackgroundColor = () => {
        const newColor = currentBackgroundColor === '#ffffff' ? '#000000' : '#ffffff';
        setBackgroundColor(newColor);
    };

    // Función para cambiar el tamaño de la fuente (Normal <-> Grande)
    const toggleFontSize = () => {
        const newScale = currentFontSizeScale === 'normal' ? 'large' : 'normal';
        setFontSizeScale(newScale);
    };

    const toggleColorblindMode = () => {
        setColorblindMode(!currentColorblindMode);
    };

    const toggleFocusMode = () => {
        setFocusMode(!currentFocusMode);
    };

    const toggleHighlightInteractions = () => {
        setHighlightInteractions(!currentHighlightInteractions);
    };

    return (
        <>
            <aside
                className={`sidebar d-flex flex-column ${collapsed ? "collapsed" : ""}`}
                id="sidebar"
                aria-label="Panel de navegación principal"
            >
                <div className="sidebar-header">
                    <span className="sidebar-text">
                        PACIENTE: {usuario?.nombre || 'Paciente'} {usuario?.apellido || 'Paciente'}
                    </span>
                    <button
                        className="btn btn-outline-light px-2 py-1 rounded-2"
                        style={{ width: "36px", height: "36px" }}
                        onClick={toggleSidebar}
                        aria-controls="sidebar"
                        aria-expanded={!collapsed}
                        aria-label={collapsed ? "Abrir navegación" : "Cerrar navegación"}
                    >
                        <i className="bi bi-list" aria-hidden="true"></i>
                    </button>
                </div>

                <nav className="nav flex-column mt-2" aria-label="Navegación principal">
                    <NavLink
                        to="/reservaCitas"
                        className={({ isActive }) =>
                            `nav-link d-flex align-items-center ${isActive ? "active" : ""}`
                        }
                        onClick={() => isMobile && setCollapsed(true)}
                    >
                        <i className="bi bi-calendar-check me-2"></i>
                        <span className="sidebar-text">Citas</span>
                    </NavLink>
                    <NavLink
                        to="/historialCitas"
                        className={({ isActive }) =>
                            `nav-link d-flex align-items-center ${isActive ? "active" : ""}`
                        }
                        onClick={() => isMobile && setCollapsed(true)}
                    >
                        <i className="bi bi-journal-text me-2"></i>
                        <span className="sidebar-text">Historial</span>
                    </NavLink>
                </nav>

                <div className="accessibility-panel" role="region" aria-label="Controles de accesibilidad">
                    <div className="accessibility-panel__header">
                        <div>
                            <p className="accessibility-kicker">Accesibilidad</p>
                            <h2 className="accessibility-heading">Ajustes rápidos</h2>
                        </div>
                        <span className="accessibility-badge">5 funciones</span>
                    </div>

                    <button
                        className={`accessibility-card ${currentBackgroundColor === '#000000' ? 'is-active' : ''}`}
                        onClick={toggleBackgroundColor}
                        type="button"
                        aria-pressed={currentBackgroundColor === '#000000'}
                        title="Alternar alto contraste"
                    >
                        <span className="accessibility-card__icon">
                            <i className="bi bi-paint-bucket" aria-hidden="true"></i>
                        </span>
                        <span className="accessibility-card__content">
                            <span className="accessibility-card__title">Alto contraste</span>
                            <span className="accessibility-card__subtitle">Mejora la lectura al instante</span>
                        </span>
                        <span className="accessibility-card__state">
                            {currentBackgroundColor === '#000000' ? 'Activo' : 'Inactivo'}
                        </span>
                    </button>

                    <button
                        className={`accessibility-card ${currentFontSizeScale === 'large' ? 'is-active' : ''}`}
                        onClick={toggleFontSize}
                        type="button"
                        aria-pressed={currentFontSizeScale === 'large'}
                        title="Cambiar el tamaño de la fuente"
                    >
                        <span className="accessibility-card__icon">
                            <i className="bi bi-fonts" aria-hidden="true"></i>
                        </span>
                        <span className="accessibility-card__content">
                            <span className="accessibility-card__title">Texto grande</span>
                            <span className="accessibility-card__subtitle">Aumenta la legibilidad</span>
                        </span>
                        <span className="accessibility-card__state">
                            {currentFontSizeScale === 'large' ? 'Activo' : 'Inactivo'}
                        </span>
                    </button>

                    <button
                        className={`accessibility-card ${currentColorblindMode ? 'is-active' : ''}`}
                        onClick={toggleColorblindMode}
                        type="button"
                        aria-pressed={currentColorblindMode}
                        title="Alternar modo para daltonismo"
                    >
                        <span className="accessibility-card__icon">
                            <i className="bi bi-eyeglasses" aria-hidden="true"></i>
                        </span>
                        <span className="accessibility-card__content">
                            <span className="accessibility-card__title">Modo daltónico</span>
                            <span className="accessibility-card__subtitle">Resalta el contenido con más contraste</span>
                        </span>
                        <span className="accessibility-card__state">
                            {currentColorblindMode ? 'Activo' : 'Inactivo'}
                        </span>
                    </button>

                    <button
                        className={`accessibility-card ${currentFocusMode ? 'is-active' : ''}`}
                        onClick={toggleFocusMode}
                        type="button"
                        aria-pressed={currentFocusMode}
                        title="Activar modo enfoque"
                    >
                        <span className="accessibility-card__icon">
                            <i className="bi bi-bullseye" aria-hidden="true"></i>
                        </span>
                        <span className="accessibility-card__content">
                            <span className="accessibility-card__title">Modo enfoque</span>
                            <span className="accessibility-card__subtitle">Destaca el contenido principal</span>
                        </span>
                        <span className="accessibility-card__state">
                            {currentFocusMode ? 'Activo' : 'Inactivo'}
                        </span>
                    </button>

                    <button
                        className={`accessibility-card ${currentHighlightInteractions ? 'is-active' : ''}`}
                        onClick={toggleHighlightInteractions}
                        type="button"
                        aria-pressed={currentHighlightInteractions}
                        title="Resaltar botones y enlaces"
                    >
                        <span className="accessibility-card__icon">
                            <i className="bi bi-lightning-charge" aria-hidden="true"></i>
                        </span>
                        <span className="accessibility-card__content">
                            <span className="accessibility-card__title">Resaltar controles</span>
                            <span className="accessibility-card__subtitle">Marca enlaces, botones y campos</span>
                        </span>
                        <span className="accessibility-card__state">
                            {currentHighlightInteractions ? 'Activo' : 'Inactivo'}
                        </span>
                    </button>
                </div>

                <div className="logout mt-auto p-3 border-top border-secondary">
                    <button
                        className="btn btn-outline-danger w-100 d-flex align-items-center justify-content-center"
                        onClick={handleLogout}
                    >
                        <i className="bi bi-box-arrow-right me-2"></i>
                        <span className="sidebar-text">Cerrar sesión</span>
                    </button>
                </div>
            </aside>

            {!collapsed && isMobile && (
                <div
                    className="sidebar-overlay"
                    onClick={toggleSidebar}
                />
            )}
        </>
    );
};