package com.sng;

import com.sng.view.PanelPartida;
import com.sng.view.PanelSubImagenes;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    private static final String RESOURCES_PATH = "resources";
    private static final String ICONS_PATH = RESOURCES_PATH + "/icons";
    private static final String NUEVA_PARTIDA_ICONO_PATH = ICONS_PATH + "/iconoNuevaPartida.jpg";
    private static final String HISTORIAL_SELECTIVO_ICONO_PATH = ICONS_PATH + "/iconoHistorialSelectivo.jpg";
    private static final String HISTORIAL_GENERAL_ICONO_PATH = ICONS_PATH + "/iconoHistorialGeneral.jpg";
    private static final String CAMBIAR_DIRECTORIO_ICONO_PATH = ICONS_PATH + "/iconoCambiarDirectorio.jpg";
    private static final String SALIR_ICONO_PATH = ICONS_PATH + "/iconoSalir.jpg";

    private static JButton createButton(String text, Color background, Color foreground) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(background);
        btn.setForeground(foreground);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        return btn;
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("PRÁCTICA PROGRAMACIÓN II - 2022-2023 - UIB");
        JPanel panelContenidos = (JPanel) ventana.getContentPane();
        panelContenidos.setLayout(new BorderLayout());
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuItem nuevaPartidaMenu = new JMenuItem("NUEVA PARTIDA");
        nuevaPartidaMenu.setBackground(Color.BLACK);
        nuevaPartidaMenu.setForeground(Color.WHITE);
        JMenuItem clasificacionMenu = new JMenuItem("CLASIFICACIÓN GENERAL");
        clasificacionMenu.setBackground(Color.BLACK);
        clasificacionMenu.setForeground(Color.WHITE);
        JMenuItem historialMenu = new JMenuItem("HISTORIAL");
        historialMenu.setBackground(Color.BLACK);
        historialMenu.setForeground(Color.WHITE);
        JMenuItem cambiarDirectorioMenu = new JMenuItem("CAMBIAR DIRECTORIO DE IMÁGENES");
        cambiarDirectorioMenu.setBackground(Color.BLACK);
        cambiarDirectorioMenu.setForeground(Color.WHITE);
        JMenuItem salirMenu = new JMenuItem("SALIR");
        salirMenu.setBackground(Color.BLACK);
        salirMenu.setForeground(Color.WHITE);
        JMenu menu = new JMenu("MENÚ");
        menu.setBackground(Color.BLACK);
        menu.setForeground(Color.WHITE);
        menu.add(nuevaPartidaMenu);
        menu.add(clasificacionMenu);
        menu.add(historialMenu);
        menu.add(cambiarDirectorioMenu);
        menu.add(salirMenu);
        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setBackground(Color.BLACK);
        barraMenu.add(menu);

        JToolBar iconosMenu = new JToolBar();
        iconosMenu.setBackground(Color.BLACK);
        JButton nuevaPartidaIcono = new JButton(new ImageIcon(NUEVA_PARTIDA_ICONO_PATH));
        JButton clasificacionIcono = new JButton(new ImageIcon(HISTORIAL_SELECTIVO_ICONO_PATH));
        JButton historialIcono = new JButton(new ImageIcon(HISTORIAL_GENERAL_ICONO_PATH));
        JButton cambiarDirectorioIcono = new JButton(new ImageIcon(CAMBIAR_DIRECTORIO_ICONO_PATH));
        JButton salirIcono = new JButton(new ImageIcon(SALIR_ICONO_PATH));
        iconosMenu.add(nuevaPartidaIcono);
        iconosMenu.add(clasificacionIcono);
        iconosMenu.add(historialIcono);
        iconosMenu.add(cambiarDirectorioIcono);
        iconosMenu.add(salirIcono);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4, 1));
        JButton nuevaPartidaBoton = createButton("NUEVA PARTIDA", Color.BLACK, Color.WHITE);
        JButton clasificacionBoton = createButton("CLASIFICACIÓN GENERAL", Color.BLACK, Color.WHITE);
        JButton historialBoton = createButton("HISTORIAL", Color.BLACK, Color.WHITE);
        JButton salirBoton = createButton("SALIR", Color.BLACK, Color.WHITE);
        panelBotones.add(nuevaPartidaBoton);
        panelBotones.add(clasificacionBoton);
        panelBotones.add(historialBoton);
        panelBotones.add(salirBoton);

        JPanel panelVisualizaciones = new JPanel();
        panelVisualizaciones.setLayout(new BorderLayout());
        panelVisualizaciones.setBackground(Color.YELLOW);

        ///// TEST
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("imagenes/600-scion-01.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PanelPartida panelPartida = new PanelPartida(img, 3, 3);
        panelVisualizaciones.add(panelPartida, BorderLayout.CENTER);

        JSplitPane separador2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelBotones, panelVisualizaciones);
        JSplitPane separador1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, iconosMenu, separador2);

        panelContenidos.add(barraMenu, BorderLayout.PAGE_START);
        panelContenidos.add(separador1, BorderLayout.CENTER);

        ventana.setSize(700, 650);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}