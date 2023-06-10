package com.sng.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class PanelSubImagenes extends JPanel {
    private final List<SubImagen> images;
    private final int rows;
    private final int cols;
    private int spacing = 2;
    private Integer selected = null;
    private PanelSubImagenesDelegate delegate = null;

    public PanelSubImagenes(List<SubImagen> images, int rows, int cols) {
        super();
        if (images == null) throw new IllegalArgumentException("image list cannot be null");
        this.images = images;
        this.rows = rows;
        this.cols = cols;
        if (images.size() != rows * cols) throw new IllegalArgumentException("image list size must match the matrix size");
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (images.size() == 0) return;
                int x = (e.getX() - spacing) / images.get(0).width();
                int y = (e.getY() - spacing) / images.get(0).height();
                int idx = indexForCoord(x, y);
                if (selected == null) {
                    selected = idx;
                } else {
                    swap(selected, idx);
                    selected = null;
                    if (delegate != null) delegate.panelSubImagenesDidChange();
                }
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public PanelSubImagenes(List<SubImagen> images, int rows, int cols, int spacing) {
        this(images, rows, cols);
        this.spacing = spacing;
        setPreferredSize(new Dimension(images.get(0).width() * cols, images.get(0).height() * rows));
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public PanelSubImagenesDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(PanelSubImagenesDelegate delegate) {
        this.delegate = delegate;
    }

    public void swap(int first, int second) {
        if (first >= this.images.size() || second >= this.images.size()) return;
        SubImagen tmp = this.images.get(first);
        this.images.set(first, this.images.get(second));
        this.images.set(second, tmp);
    }

    private int indexForCoord(int x, int y) {
        return y * this.cols + x;
    }

    public SubImagen getSubImage(int x, int y) {
        return this.images.get(indexForCoord(x, y));
    }

    public List<SubImagen> getImages() {
        return images;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                SubImagen subimage = getSubImage(x, y);
                int xspace = this.spacing * x;
                int yspace = this.spacing * y;
                float alpha = selected != null && selected == indexForCoord(x, y) ? 0.5f : 1.f;
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2.setComposite(ac);
                g2.drawImage(subimage.getImage(), x * subimage.width() + xspace, y * subimage.height() + yspace, subimage.width(), subimage.height(), null);
            }
        }
    }
}
