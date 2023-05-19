package com.sng;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class P1 {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Practica 20/04/2023 (Serafí Nebot Ginard)");
        JPanel panelContenidos = (JPanel) ventana.getContentPane();
        panelContenidos.setLayout(new BorderLayout());

        JPanel visualizador = new JPanel();
        visualizador.setBackground(Color.WHITE);
        panelContenidos.add(visualizador, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(6, 1));
        panelBotones.setBackground(Color.GREEN);
        JButton rojo = createSidePanelButton("ROJO", Color.RED, Color.BLACK, visualizador);
        panelBotones.add(rojo);
        JButton verde = createSidePanelButton("VERDE", Color.GREEN, Color.BLACK, visualizador);
        panelBotones.add(verde);
        JButton azul = createSidePanelButton("AZUL", Color.BLUE, Color.BLACK, visualizador);
        panelBotones.add(azul);
        JButton cyan = createSidePanelButton("CYAN", Color.CYAN, Color.BLACK, visualizador);
        panelBotones.add(cyan);
        JButton magenta = createSidePanelButton("MAGENTA", Color.MAGENTA, Color.BLACK, visualizador);
        panelBotones.add(magenta);
        JButton amarillo = createSidePanelButton("AMARILLO", Color.YELLOW, Color.BLACK, visualizador);
        panelBotones.add(amarillo);
        panelContenidos.add(panelBotones, BorderLayout.LINE_START);

        JPanel panelSalir = new JPanel(new BorderLayout());
        panelSalir.setPreferredSize(new Dimension(100, 100));
        panelSalir.setBackground(Color.CYAN);
        JButton salir = createButton("SALIR", Color.BLACK, Color.WHITE);
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panelSalir.add(salir);
        panelContenidos.add(panelSalir, BorderLayout.PAGE_END);

        ventana.setSize(1200,100);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private static JButton createButton(String text, Color background, Color foreground) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(background);
        btn.setForeground(foreground);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        return btn;
    }

    private static JButton createSidePanelButton(String text, Color background, Color foreground, JPanel panel) {
        JButton btn = createButton(text, background, foreground);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setBackground(background);
            }
        });
        return btn;
    }
}