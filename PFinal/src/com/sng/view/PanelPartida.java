package com.sng.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class PanelPartida extends JPanel {
    private final int rows;
    private final int cols;
    private final JProgressBar barraTemporal;

    public PanelPartida(BufferedImage image, int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.barraTemporal = new JProgressBar(0, 100);
        this.barraTemporal.setBackground(Color.YELLOW);
        this.barraTemporal.setForeground(Color.RED);
        setBackground(Color.RED);
        setLayout(new BorderLayout());

        List<SubImagen> images = new ArrayList<>();
        int width = image.getWidth() / cols;
        int height = image.getHeight() / rows;
        System.out.printf("[original] w: %d, h: %d\n", image.getWidth(), image.getHeight());
        System.out.printf("[subimage] w: %d, h: %d\n", width, height);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                images.add(new SubImagen(image.getSubimage(j * width, i * height, width, height)));
        Collections.shuffle(images);
        PanelSubImagenes panelSubImagenes = new PanelSubImagenes(images, 3, 3);
        add(panelSubImagenes, BorderLayout.CENTER);
    }
}
