package com.sng;

import com.sng.io.ObjectReader;
import com.sng.io.ObjectWriter;
import com.sng.model.Configuration;
import com.sng.model.Partida;
import com.sng.view.PanelPartida;
import com.sng.view.PanelPartidaDelegate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main implements PanelPartidaDelegate {
    private static final String RESOURCES_PATH = "resources";
    private static final String UIB_IMAGE_PATH = RESOURCES_PATH + "/UIB.jpg";
    private static final String ICONS_PATH = RESOURCES_PATH + "/icons";
    private static final String NUEVA_PARTIDA_ICONO_PATH = ICONS_PATH + "/iconoNuevaPartida.jpg";
    private static final String HISTORIAL_SELECTIVO_ICONO_PATH = ICONS_PATH + "/iconoHistorialSelectivo.jpg";
    private static final String HISTORIAL_GENERAL_ICONO_PATH = ICONS_PATH + "/iconoHistorialGeneral.jpg";
    private static final String CAMBIAR_DIRECTORIO_ICONO_PATH = ICONS_PATH + "/iconoCambiarDirectorio.jpg";
    private static final String SALIR_ICONO_PATH = ICONS_PATH + "/iconoSalir.jpg";
    private static final String RESULTS_PATH = "resultados.dat";
    private static final String CONFIGURATION_PATH = "configuration.dat";
    private static final Configuration DEFAULTS = new Configuration("imagenes");

    private final JFrame ventana;
    private final JPanel panelContenidos;
    private final JPanel panelVisualizaciones;
    private final JPanel panelStandby;
    private final JPanel panelHistorial;
    private Configuration configuration;
    private boolean playing = false;

    private static JButton createButton(String text, Color background, Color foreground) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(background);
        btn.setForeground(foreground);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        return btn;
    }

    public Main() {
        configuration = loadConfiguration();
        ventana = new JFrame("PRÁCTICA PROGRAMACIÓN II - 2022-2023 - UIB");
        panelContenidos = (JPanel) ventana.getContentPane();
        panelContenidos.setLayout(new BorderLayout());
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuItem nuevaPartidaMenu = new JMenuItem("NUEVA PARTIDA");
        nuevaPartidaMenu.setBackground(Color.BLACK);
        nuevaPartidaMenu.setForeground(Color.WHITE);
        nuevaPartidaMenu.addActionListener(e -> {
            showPuzzleForm();
        });
        JMenuItem historialGeneralMenu = new JMenuItem("HISTORIAL GENERAL");
        historialGeneralMenu.setBackground(Color.BLACK);
        historialGeneralMenu.setForeground(Color.WHITE);
        historialGeneralMenu.addActionListener(e -> {
            showHistory();
        });
        JMenuItem historialSelectivoMenu = new JMenuItem("HISTORIAL SELECTIVO");
        historialSelectivoMenu.setBackground(Color.BLACK);
        historialSelectivoMenu.setForeground(Color.WHITE);
        historialSelectivoMenu.addActionListener(e -> {
            showSelectiveHistory();
        });
        JMenuItem cambiarDirectorioMenu = new JMenuItem("CAMBIAR DIRECTORIO DE IMÁGENES");
        cambiarDirectorioMenu.setBackground(Color.BLACK);
        cambiarDirectorioMenu.setForeground(Color.WHITE);
        cambiarDirectorioMenu.addActionListener(e -> {
            showDirectoryChange();
        });
        JMenuItem salirMenu = new JMenuItem("SALIR");
        salirMenu.setBackground(Color.BLACK);
        salirMenu.setForeground(Color.WHITE);
        salirMenu.addActionListener(e -> {
            exit();
        });
        JMenu menu = new JMenu("MENÚ");
        menu.setBackground(Color.BLACK);
        menu.setForeground(Color.WHITE);
        menu.add(nuevaPartidaMenu);
        menu.add(historialGeneralMenu);
        menu.add(historialSelectivoMenu);
        menu.add(cambiarDirectorioMenu);
        menu.add(salirMenu);
        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setBackground(Color.BLACK);
        barraMenu.add(menu);

        JToolBar iconosMenu = new JToolBar();
        iconosMenu.setBackground(Color.BLACK);
        JButton nuevaPartidaIcono = new JButton(new ImageIcon(NUEVA_PARTIDA_ICONO_PATH));
        nuevaPartidaIcono.addActionListener(e -> {
            showPuzzleForm();
        });
        JButton historialGeneralIcono = new JButton(new ImageIcon(HISTORIAL_GENERAL_ICONO_PATH));
        historialGeneralIcono.addActionListener(e -> {
            showHistory();
        });
        JButton historialSelectivoIcono = new JButton(new ImageIcon(HISTORIAL_SELECTIVO_ICONO_PATH));
        historialSelectivoIcono.addActionListener(e -> {
            showSelectiveHistory();
        });
        JButton cambiarDirectorioIcono = new JButton(new ImageIcon(CAMBIAR_DIRECTORIO_ICONO_PATH));
        cambiarDirectorioIcono.addActionListener(e -> {
            showDirectoryChange();
        });
        JButton salirIcono = new JButton(new ImageIcon(SALIR_ICONO_PATH));
        salirIcono.addActionListener(e -> {
            exit();
        });
        iconosMenu.add(nuevaPartidaIcono);
        iconosMenu.add(historialGeneralIcono);
        iconosMenu.add(historialSelectivoIcono);
        iconosMenu.add(cambiarDirectorioIcono);
        iconosMenu.add(salirIcono);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(4, 1));
        JButton nuevaPartidaBoton = createButton("NUEVA PARTIDA", Color.BLACK, Color.WHITE);
        nuevaPartidaBoton.addActionListener(e -> {
            showPuzzleForm();
        });
        JButton historialGeneralBoton = createButton("HISTORIAL GENERAL", Color.BLACK, Color.WHITE);
        historialGeneralBoton.addActionListener(e -> {
            showHistory();
        });
        JButton historialSelectivoBoton = createButton("HISTORIAL SELECTIVO", Color.BLACK, Color.WHITE);
        historialSelectivoBoton.addActionListener(e -> {
            showSelectiveHistory();
        });
        JButton salirBoton = createButton("SALIR", Color.BLACK, Color.WHITE);
        salirBoton.addActionListener(e -> {
            exit();
        });
        panelBotones.add(nuevaPartidaBoton);
        panelBotones.add(historialGeneralBoton);
        panelBotones.add(historialSelectivoBoton);
        panelBotones.add(salirBoton);

        panelVisualizaciones = new JPanel();
        panelVisualizaciones.setLayout(new BorderLayout());

        panelStandby = new JPanel();
        panelStandby.setLayout(new BorderLayout());
        BufferedImage uibImage = null;
        try {
            uibImage = ImageIO.read(new File(UIB_IMAGE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel uibLabel = new JLabel();
        uibLabel.setIcon(new ImageIcon(uibImage));
        uibLabel.setHorizontalAlignment(JLabel.CENTER);
        uibLabel.setVerticalAlignment(JLabel.CENTER);
        panelStandby.add(uibLabel, BorderLayout.CENTER);
        showStandby();

        panelHistorial = new JPanel();
        panelHistorial.setLayout(new BorderLayout());

        JSplitPane separador2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelBotones, panelVisualizaciones);
        JSplitPane separador1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, iconosMenu, separador2);

        panelContenidos.add(barraMenu, BorderLayout.PAGE_START);
        panelContenidos.add(separador1, BorderLayout.CENTER);

        ventana.setSize(1000, 800);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private boolean checkIsPlaying() {
        if (playing) {
            JOptionPane pane = new JOptionPane("DEBES TERMINAR LA PARTIDA EN CURSO", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
            JDialog dialog = pane.createDialog("ERROR");
            dialog.setVisible(true);
            return true;
        }
        return false;
    }

    private void showPuzzleForm() {
        if (checkIsPlaying()) return;
        JLabel nameLabel = new JLabel("NOMBRE JUGADOR");
        nameLabel.setBackground(Color.BLACK);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setOpaque(true);
        JLabel nhLabel = new JLabel("NÚMERO DE SUBDIVISIONES HORIZONTAL");
        nhLabel.setBackground(Color.BLACK);
        nhLabel.setForeground(Color.WHITE);
        nhLabel.setOpaque(true);
        JLabel nvLabel = new JLabel("NÚMERO DE SUBDIVISIONES VERTIVAL");
        nvLabel.setBackground(Color.BLACK);
        nvLabel.setForeground(Color.WHITE);
        nvLabel.setOpaque(true);

        JTextField nameField = new JTextField();
        JTextField nhField = new JTextField();
        JTextField nvField = new JTextField();

        JButton confirm = new JButton("CONFIRMAR");

        JDialog dialog = new JDialog(ventana, true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        dialog.add(nameLabel, c);
        c.gridx = 1;
        dialog.add(nameField, c);

        c.gridx = 0;
        c.gridy = 1;
        dialog.add(nhLabel, c);
        c.gridx = 1;
        dialog.add(nhField, c);

        c.gridx = 0;
        c.gridy = 2;
        dialog.add(nvLabel, c);
        c.gridx = 1;
        dialog.add(nvField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weightx = 1;
        dialog.add(confirm, c);

        confirm.addActionListener(e2 -> {
            String name = null;
            int rows = 0;
            int cols = 0;
            try {
                rows = Integer.parseInt(nvField.getText());
                cols = Integer.parseInt(nhField.getText());
                if (rows <= 0 || cols <= 0) throw new NumberFormatException();
                name = nameField.getText();
            } catch (NumberFormatException exc) {
                JOptionPane pane = new JOptionPane("DEBES INTRODUCIR NÚMEROS MAYORES O IGUAL QUE 1 EN LOS CAMPOS DE SUBDIVISIONES", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
                pane.setBackground(Color.BLACK);
                pane.setForeground(Color.YELLOW);
                JDialog _dialog = pane.createDialog("ERROR");
                _dialog.setVisible(true);
            }
            if (name != null) {
                dialog.dispose();
                startPuzzle(name, rows, cols);
            }
        });

        dialog.setPreferredSize(new Dimension(800, 150));
        dialog.pack();
        dialog.setVisible(true);
    }

    private void startPuzzle(String name, int rows, int cols) {
        playing = true;
        panelVisualizaciones.removeAll();
        File imageDir = new File(configuration.getImageDirectory());
        File[] files = imageDir.listFiles();
        if (files == null || files.length == 0) {
            JOptionPane pane = new JOptionPane("EL DIRECTORIO DE IMÁGENES SELECCIONADO ESTA VACÍO", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
            JDialog dialog = pane.createDialog("ERROR");
            dialog.setVisible(true);
        } else {
            File file = files[(int) (Math.random() * files.length)];
            BufferedImage img = null;
            try {
                img = ImageIO.read(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PanelPartida panelPartida = new PanelPartida(name, img, rows, cols);
            panelPartida.setDelegate(this);
            panelVisualizaciones.add(panelPartida, BorderLayout.CENTER);
            ventana.revalidate();
            ventana.repaint();
        }
    }

    private void showDirectoryChange() {
        if (checkIsPlaying()) return;
        JFileChooser fileChooser = new JFileChooser(new File(configuration.getImageDirectory()));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JButton confirm = new JButton("CONFIRMAR");

        JDialog dialog = new JDialog(ventana, true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        dialog.add(fileChooser, c);

        c.gridx = 2;
        c.gridy += 1;
        c.gridwidth = 1;
        dialog.add(confirm, c);

        confirm.addActionListener(e -> {
            File directory = fileChooser.getSelectedFile();
            if (directory != null && directory.exists()) {
                configuration.setImageDirectory(directory.getAbsolutePath());
                storeConfiguration(configuration);
            }
            dialog.dispose();
        });

        dialog.setPreferredSize(new Dimension(800, 350));
        dialog.pack();
        dialog.setVisible(true);
    }

    private void showHistory() {
        if (checkIsPlaying()) return;
        String str = "";
        try (ObjectReader<Partida> reader = new ObjectReader<>(RESULTS_PATH)) {
            for (Partida partida : reader.readAll()) str += partida + "\n";
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            JOptionPane pane = new JOptionPane("ERROR AL ABRIR EL FICHERO DE RESULTADOS", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
            JDialog dialog = pane.createDialog("ERROR");
            dialog.setVisible(true);
            return;
        }

        panelVisualizaciones.removeAll();
        panelHistorial.removeAll();
        JTextArea areaVisualizacionResultados = new JTextArea();
        areaVisualizacionResultados.setText(str);
        panelHistorial.add(areaVisualizacionResultados);
        panelVisualizaciones.add(panelHistorial);
        ventana.revalidate();
        ventana.repaint();
    }

    private void showSelectiveHistory() {
        if (checkIsPlaying()) return;

        JLabel titleLabel = new JLabel("HISTORIAL JUGADOR");
        titleLabel.setForeground(Color.YELLOW);
        JLabel infoLabel = new JLabel("INTRODUCIR NOMBRE DE JUGADOR");
        infoLabel.setForeground(Color.YELLOW);
        JTextField playerTextField = new JTextField();
        JButton cancel = new JButton("CANCELAR");
        JButton confirm = new JButton("CONFIRMAR");

        JDialog dialog = new JDialog(ventana, true);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        dialog.add(titleLabel, c);
        c.gridy += 1;
        dialog.add(infoLabel, c);
        c.gridy += 1;
        dialog.add(playerTextField, c);

        c.gridwidth = 1;
        c.weightx = 0.5;
        c.gridy += 1;
        dialog.add(cancel, c);
        c.gridx = 1;
        dialog.add(confirm, c);

        confirm.addActionListener(e -> {
            String playerName = playerTextField.getText();
            String str = "";
            File file = new File(RESULTS_PATH);
            if (file.exists() && file.length() > 0) {
                try (ObjectReader<Partida> reader = new ObjectReader<>(RESULTS_PATH)) {
                    for (Partida p : reader.readAll())
                        if (playerName.equals(p.getPlayer()))
                            str += p + "\n";
                } catch (ClassNotFoundException | IOException ignored) {
                    JOptionPane pane = new JOptionPane("ERROR AL ABRIR EL FICHERO DE RESULTADOS", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
                    JDialog dialog2 = pane.createDialog("ERROR");
                    dialog2.setVisible(true);
                    dialog2.dispose();
                    dialog.dispose();
                }
            }

            panelVisualizaciones.removeAll();
            panelHistorial.removeAll();
            JTextArea areaVisualizacionResultados = new JTextArea();
            areaVisualizacionResultados.setText(str);
            panelHistorial.add(areaVisualizacionResultados);
            panelVisualizaciones.add(panelHistorial);
            ventana.revalidate();
            ventana.repaint();
            dialog.dispose();
        });
        cancel.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.setPreferredSize(new Dimension(400, 120));
        dialog.pack();
        dialog.setVisible(true);
    }

    private void showStandby() {
        panelVisualizaciones.removeAll();
        panelVisualizaciones.add(panelStandby);
        ventana.revalidate();
        ventana.repaint();
    }

    @Override
    public void panelPartidaDidEnd(Partida partida) {
        playing = false;
        showStandby();

        List<Partida> partidas = null;
        File file = new File(RESULTS_PATH);
        if (file.exists() && file.length() > 0) {
            try (ObjectReader<Partida> reader = new ObjectReader<>(RESULTS_PATH)) {
                partidas = reader.readAll();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
                JOptionPane pane = new JOptionPane("ERROR AL ABRIR EL FICHERO DE RESULTADOS", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
                JDialog dialog = pane.createDialog("ERROR");
                dialog.setVisible(true);
                exit(-1);
            }
        }

        try (ObjectWriter<Partida> writer = new ObjectWriter<>(RESULTS_PATH)) {
            writer.write(partidas);
            writer.write(partida);
        } catch (IOException e) {
            JOptionPane pane = new JOptionPane("ERROR AL ABRIR EL FICHERO DE RESULTADOS", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
            JDialog dialog = pane.createDialog("ERROR");
            dialog.setVisible(true);
            exit(-1);
        }
    }

    public Configuration loadConfiguration() {
        Configuration conf = DEFAULTS;
        try (ObjectReader<Configuration> reader = new ObjectReader<>(CONFIGURATION_PATH)) {
            conf = reader.read();
        } catch (ClassNotFoundException | IOException ignored) {
        }
        return conf;
    }

    public void storeConfiguration(Configuration conf) {
        try (ObjectWriter<Configuration> writer = new ObjectWriter<>(CONFIGURATION_PATH)) {
            writer.write(conf);
        } catch (IOException ignored) {
        }
    }

    public void exit(int code) {
        ventana.dispose();
        System.exit(code);
    }

    public void exit() {
        exit(0);
    }

    public static void main(String[] args) {
        new Main();
    }
}