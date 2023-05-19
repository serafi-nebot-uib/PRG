package com.sng;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("P2504");
        JPanel panelContenidos = (JPanel) ventana.getContentPane();
        panelContenidos.setLayout(new BorderLayout());

        JPanel panelVarios = new JPanel(new GridLayout(1, 4));
        JButton colorBorrado = createButton("COLOR BORRADO", Color.BLACK, Color.WHITE);
        JButton borrar = createButton("COLOR BORRADO", Color.BLACK, Color.WHITE);
        JButton salir = createButton("COLOR BORRADO", Color.BLACK, Color.WHITE);
        JPanel panelColoreado = new JPanel(new GridLayout(2, 1));
        panelColoreado.setBackground(Color.YELLOW);
        JRadioButton colorear = new JRadioButton("COLOREADO");
        colorear.setOpaque(true);
        colorear.setForeground(Color.WHITE);
        colorear.setBackground(Color.BLACK);
        JRadioButton sinColorear = new JRadioButton("SIN COLOREAR", true);
        sinColorear.setOpaque(true);
        sinColorear.setForeground(Color.WHITE);
        sinColorear.setBackground(Color.BLACK);
        ButtonGroup colorearGroup = new ButtonGroup();
        colorearGroup.add(colorear);
        colorearGroup.add(sinColorear);
        panelColoreado.add(colorear);
        panelColoreado.add(sinColorear);
        panelVarios.add(colorBorrado);
        panelVarios.add(borrar);
        panelVarios.add(panelColoreado);
        panelVarios.add(salir);
        panelContenidos.add(panelVarios, BorderLayout.PAGE_END);

        Dibujo dibujo = new Dibujo();
        dibujo.setOpaque(true);
        panelContenidos.add(dibujo, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(6, 1));
        JButton rojo = createButton("ROJO", Color.RED, Color.BLACK);
        panelBotones.add(rojo);
        JButton verde = createButton("VERDE", Color.GREEN, Color.BLACK);
        panelBotones.add(verde);
        JButton azul = createButton("AZUL", Color.BLUE, Color.BLACK);
        panelBotones.add(azul);
        JButton cyan = createButton("CYAN", Color.CYAN, Color.BLACK);
        panelBotones.add(cyan);
        JButton magenta = createButton("MAGENTA", Color.MAGENTA, Color.BLACK);
        panelBotones.add(magenta);
        JButton amarillo = createButton("AMARILLO", Color.YELLOW, Color.BLACK);
        panelBotones.add(amarillo);
        panelContenidos.add(panelBotones, BorderLayout.LINE_START);

        JPanel panelFiguras = new JPanel(new GridLayout(5, 1));
        JButton rectangulo = createButton("RECTÁNGIULO", Color.BLACK, Color.WHITE);
        panelFiguras.add(rectangulo);
        JButton elipse = createButton("RECTÁNGIULO", Color.BLACK, Color.WHITE);
        panelFiguras.add(elipse);
        JButton polilinea = createButton("POLININEA", Color.BLACK, Color.WHITE);
        panelFiguras.add(polilinea);
        JButton poligono = createButton("POLÍGONO", Color.BLACK, Color.WHITE);
        panelFiguras.add(poligono);
        JButton texto = createButton("TEXTO", Color.BLACK, Color.WHITE);
        panelFiguras.add(texto);
        panelContenidos.add(panelFiguras, BorderLayout.LINE_END);

        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setPreferredSize(new Dimension(400, 40));

        JMenu generalMenu = new JMenu("GENERAL");
        JMenuItem colorBorradoMenu = new JMenuItem("COLOR BORRADO");
        JMenuItem borrarMenu = new JMenuItem("BORRAR");
        generalMenu.add(colorBorradoMenu);
        generalMenu.add(borrarMenu);
        barraMenu.add(generalMenu);

        JMenu figurasMenu = new JMenu("FIGURAS");
        JMenuItem rectanguloMenu = new JMenuItem("RECTÁNGULO");
        figurasMenu.add(rectanguloMenu);
        JMenuItem elipseMenu = new JMenuItem("ELIPSE");
        figurasMenu.add(elipseMenu);
        JMenuItem polilineaMenu = new JMenuItem("POLILINEA");
        figurasMenu.add(polilineaMenu);
        JMenuItem poligonoMenu = new JMenuItem("POLÍGONO");
        figurasMenu.add(poligonoMenu);
        JMenuItem textoMenu = new JMenuItem("TEXTO");
        figurasMenu.add(textoMenu);
        barraMenu.add(figurasMenu);

        JMenu coloresMenu = new JMenu("COLOR FONDO");
        JMenuItem rojoMenu = new JMenuItem("ROJO");
        coloresMenu.add(rojoMenu);
        JMenuItem verdeMenu = new JMenuItem("VERDE");
        coloresMenu.add(verdeMenu);
        JMenuItem azulMenu = new JMenuItem("AZUL");
        coloresMenu.add(azulMenu);
        JMenuItem cyanMenu = new JMenuItem("CYAN");
        coloresMenu.add(cyanMenu);
        JMenuItem magentaMenu = new JMenuItem("MAGENTA");
        coloresMenu.add(magentaMenu);
        JMenuItem amarilloMenu = new JMenuItem("AMARILLO");
        coloresMenu.add(amarilloMenu);
        barraMenu.add(coloresMenu);

        JMenu coloreadoMenu = new JMenu("COLOREADO");
        JRadioButtonMenuItem colorearMenuBoton = new JRadioButtonMenuItem("COLOREADO");
        JRadioButtonMenuItem sinColorearMenuBoton = new JRadioButtonMenuItem("SIN COLOREAR", true);
        coloreadoMenu.add(colorearMenuBoton);
        coloreadoMenu.add(sinColorearMenuBoton);
        ButtonGroup colorearMenuGroup = new ButtonGroup();
        colorearMenuGroup.add(colorearMenuBoton);
        colorearMenuGroup.add(sinColorearMenuBoton);
        barraMenu.add(coloreadoMenu);

        panelContenidos.add(barraMenu, BorderLayout.PAGE_START);

        ventana.setSize(1200, 100);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private static JButton createButton(String text, Color background, Color foreground) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(background);
        btn.setForeground(foreground);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        return btn;
    }
}