// Home.jsx

import React from "react";
import './Home.css';
import { SidebarP } from "../../components/sidebarP/SidebarP";
import { NavbarH } from "../../components/navbarH/NavbarH";
import { useState } from 'react';
import { Outlet } from "react-router-dom";

export const Home = () => {
    const [isCollapsed, setIsCollapsed] = useState(false);
    const [backgroundColor, setBackgroundColor] = useState('#ffffff'); 
    const [fontSizeScale, setFontSizeScale] = useState('normal'); 
    const [colorblindMode, setColorblindMode] = useState(false);
    const [focusMode, setFocusMode] = useState(false);
    const [highlightInteractions, setHighlightInteractions] = useState(false);

    const darkTextClass = backgroundColor === '#000000' ? 'dark-mode-text' : '';
    const largeFontClass = fontSizeScale === 'large' ? 'large-font' : '';
    const colorblindClass = colorblindMode ? 'colorblind-mode mode-colorblind' : '';
    const highContrastClass = backgroundColor === '#000000' ? 'mode-high-contrast' : '';
    const focusModeClass = focusMode ? 'mode-focus' : '';
    const highlightInteractionsClass = highlightInteractions ? 'mode-highlight-interactions' : '';
    const accessibilityPageClasses = [highContrastClass, largeFontClass, colorblindClass, focusModeClass, highlightInteractionsClass]
        .filter(Boolean)
        .join(' ');

    const activeModes = [];
    if (backgroundColor === '#000000') activeModes.push('Alto contraste');
    if (fontSizeScale === 'large') activeModes.push('Texto grande');
    if (colorblindMode) activeModes.push('Modo daltónico');
    if (focusMode) activeModes.push('Modo enfoque');
    if (highlightInteractions) activeModes.push('Resaltar controles');

    return (
        <div 
            className={`home-body app-container ${accessibilityPageClasses}`}
            style={{ backgroundColor: backgroundColor }}
        >
            <SidebarP 
                collapsed={isCollapsed} 
                toggleSidebar={() => setIsCollapsed(!isCollapsed)} 
                setBackgroundColor={setBackgroundColor}
                currentBackgroundColor={backgroundColor}
                setFontSizeScale={setFontSizeScale}
                currentFontSizeScale={fontSizeScale}
                setColorblindMode={setColorblindMode}
                currentColorblindMode={colorblindMode}
                setFocusMode={setFocusMode}
                currentFocusMode={focusMode}
                setHighlightInteractions={setHighlightInteractions}
                currentHighlightInteractions={highlightInteractions}
            />
            {/* 4. Aplicar AMBAS clases condicionales */}
            <main id="main-content" className={`main-content ${isCollapsed ? "" : "mobile-pushed"} ${darkTextClass} ${largeFontClass} ${colorblindClass}`}>
                <NavbarH toggleSidebar={() => setIsCollapsed(!isCollapsed)} />
                {activeModes.length > 0 && (
                    <div className="accessibility-live-bar" aria-live="polite">
                        <span className="accessibility-live-bar__label">Modos activos</span>
                        <div className="accessibility-live-bar__chips">
                            {activeModes.map((mode) => (
                                <span key={mode} className="accessibility-live-bar__chip">
                                    {mode}
                                </span>
                            ))}
                        </div>
                    </div>
                )}
                <Outlet />
            </main>
            <div className="sidebar-overlay" onClick={() => setIsCollapsed(true)} />
        </div>
    );
};