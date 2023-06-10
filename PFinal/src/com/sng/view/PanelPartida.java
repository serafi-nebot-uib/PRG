package com.sng.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PanelPartida extends JPanel implements PanelSubImagenesDelegate {
    private final int rows;
    private final int cols;
    private final PanelSubImagenes panelSubImagenes;
    private final List<SubImagen> images = new ArrayList<>();
    private final JProgressBar barraTemporal;

    public PanelPartida(BufferedImage image, int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.barraTemporal = new JProgressBar(0, 100);
        this.barraTemporal.setBackground(Color.YELLOW);
        this.barraTemporal.setForeground(Color.RED);
        setLayout(new BorderLayout());

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
        add(panelSubImagenes, BorderLayout.CENTER);
    }

    @Override
    public void panelSubImagenesDidChange() {
        boolean same = true;
        List<SubImagen> puzzle = panelSubImagenes.getImages();
        for (int i = 0; i < this.images.size() && same; i++) same = this.images.get(i) == puzzle.get(i);
        System.out.println(same);
    }
}
