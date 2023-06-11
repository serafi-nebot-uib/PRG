package com.sng.view;

import com.sng.model.Partida;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PanelPartida extends JPanel implements PanelSubImagenesDelegate {
    private final int rows;
    private final int cols;
    private final PanelSubImagenes panelSubImagenes;
    private final List<SubImagen> images = new ArrayList<>();
    private final JProgressBar barraTemporal;
    private final Timer progressTimer;
    private final Image image;
    private final String name;
    private PanelPartidaDelegate delegate;

    public PanelPartida(String name, BufferedImage image, int rows, int cols) {
        super();
        this.name = name;
        this.image = image;
        this.rows = rows;
        this.cols = cols;
        // assign a time limit based on chosen rows and columns
        this.barraTemporal = new JProgressBar(0, rows * cols * 3);
        this.barraTemporal.setValue(0);
        this.barraTemporal.setStringPainted(true); // show current percentage
        // increment progress bar by one each second
        this.progressTimer = new Timer(1000, e -> {
            int value = this.barraTemporal.getValue();
            if (value == this.barraTemporal.getMaximum()) {
                // time limit reached, show failure message to the player
                JOptionPane pane = new JOptionPane("NO LO HAS CONSEGUIDO - EL TIEMPO HA TERMIMADO", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
                pane.setBackground(Color.BLACK);
                pane.setForeground(Color.YELLOW);
                JDialog dialog = pane.createDialog("HAS PERDIDO");
                dialog.setVisible(true);
                end();
            } else {
                this.barraTemporal.setValue(value + 1);
            }
        });

        // determine the width and height of each sub-image based on chosen rows and columns
        int width = image.getWidth() / cols;
        int height = image.getHeight() / rows;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                images.add(new SubImagen(image.getSubimage(j * width, i * height, width, height)));
        // randomly shuffle SubImagen list to form the puzzle
        List<SubImagen> shuffled = new ArrayList<>(images);
        for (int i = 0; i < shuffled.size(); i++) {
            SubImagen tmp = shuffled.get(i);
            int idx = (int) (Math.random() * shuffled.size());
            shuffled.set(i, shuffled.get(idx));
            shuffled.set(idx, tmp);
        }
        panelSubImagenes = new PanelSubImagenes(shuffled, rows, cols);
        panelSubImagenes.setDelegate(this);
        setLayout(new BorderLayout());
        add(panelSubImagenes, BorderLayout.CENTER);
        add(this.barraTemporal, BorderLayout.PAGE_END);
    }

    public void start() {
        progressTimer.start();
    }

    public void setDelegate(PanelPartidaDelegate delegate) {
        this.delegate = delegate;
    }

    public void end() {
        this.progressTimer.stop();
        removeAll();
        JLabel imagenSolucion = new JLabel();
        imagenSolucion.setIcon(new ImageIcon(this.image));
        imagenSolucion.setHorizontalAlignment(JLabel.CENTER);
        imagenSolucion.setVerticalAlignment(JLabel.CENTER);
        add(imagenSolucion, BorderLayout.CENTER);
        JButton botonContinuar = new JButton("CONTINUAR");
        botonContinuar.setFont(new Font("Arial", Font.BOLD, 14));
        botonContinuar.setBackground(Color.BLACK);
        botonContinuar.setForeground(Color.WHITE);
        botonContinuar.setOpaque(true);
        botonContinuar.setBorderPainted(false);
        botonContinuar.addActionListener(e -> {
            // notify parent that the match is over
            if (this.delegate != null)
                this.delegate.panelPartidaDidEnd(new Partida(this.name, Date.from(Instant.now()), rows * cols));
        });
        add(botonContinuar, BorderLayout.PAGE_END);
        revalidate();
    }

    @Override
    public void panelSubImagenesDidChange() {
        // there has been a change in the puzzle, check if the puzzle has been solved
        boolean same = true;
        List<SubImagen> puzzle = panelSubImagenes.getImages();
        for (int i = 0; i < this.images.size() && same; i++) same = this.images.get(i) == puzzle.get(i);
        if (same) {
            // puzzle has been solved, display success message to player
            this.progressTimer.stop();
            JOptionPane pane = new JOptionPane(String.format("¡ENHORABUENA! LO HAS CONSEGUIDO\nHAS OBTENIDO %d PUNTOS", rows * cols), JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
            pane.setBackground(Color.BLACK);
            pane.setForeground(Color.YELLOW);
            JDialog dialog = pane.createDialog("HAS GANADO");
            dialog.setVisible(true);
            end();
        }
    }
}
