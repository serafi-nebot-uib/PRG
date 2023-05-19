package com.sng;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("TALLER 2 - PROGRAMACIÓN II - CURSO 2022-2023");
        Container panelContenidos = ventana.getContentPane();
        panelContenidos.setLayout(new GridBagLayout());
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel dibujo = new JPanel();
        dibujo.setBackground(Color.YELLOW);
        dibujo.setOpaque(true);
        dibujo.setPreferredSize(new Dimension(100, 200));

        JPanel panelActividades = new JPanel();
        panelActividades.setBackground(Color.RED);
        panelActividades.setOpaque(true);
        panelContenidos.setPreferredSize(new Dimension(100, 200));

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.CYAN);
        panelBotones.setOpaque(true);
        panelBotones.setPreferredSize(new Dimension(100, 200));

        JSplitPane separadorNorte = new JSplitPane(JSplitPane.VERTICAL_SPLIT, null, dibujo);
        JSplitPane separadorOeste = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dibujo, panelActividades);
        JSplitPane separadorEste = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelActividades, panelBotones);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        panelContenidos.add(separadorNorte, c);
        c.gridy = 1;
        panelContenidos.add(dibujo, c);
        c.gridy = 2;
        panelContenidos.add(separadorOeste, c);
        c.gridy = 3;
        panelContenidos.add(panelActividades, c);
        c.gridy = 4;
        panelContenidos.add(separadorEste, c);
        c.gridy = 5;
        panelContenidos.add(panelBotones, c);
        panelContenidos.setBackground(Color.GREEN);

        ventana.setSize(1200, 100);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}