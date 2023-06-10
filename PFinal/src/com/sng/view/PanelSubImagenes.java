package com.sng.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelSubImagenes extends JPanel {
    private final List<SubImagen> images;
    private final int rows;
    private final int cols;
    private int spacing = 2;

    public PanelSubImagenes(List<SubImagen> images, int rows, int cols) {
        super();
        if (images == null) throw new IllegalArgumentException("image list cannot be null");
        this.images = images;
        this.rows = rows;
        this.cols = cols;
        if (images.size() != rows * cols) throw new IllegalArgumentException("image list size must match the matrix size");
    }

    public PanelSubImagenes(List<SubImagen> images, int rows, int cols, int spacing) {
        this(images, rows, cols);
        this.spacing = spacing;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public void swap(int first, int second) {
        if (first >= this.images.size() || second >= this.images.size()) return;
        SubImagen tmp = this.images.get(first);
        this.images.set(first, this.images.get(second));
        this.images.set(second, this.images.get(first));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                SubImagen subimage = this.images.get(i * cols + j);
                int xspace = this.spacing * j;
                int yspace = this.spacing * i;
                g.drawImage(subimage.getImage(), j * subimage.width() + xspace, i * subimage.height() + yspace, subimage.width(), subimage.height(), null);
            }
        }
    }
}
