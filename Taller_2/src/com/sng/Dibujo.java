package com.sng;

import javax.swing.*;
import java.awt.*;

public class Dibujo extends JPanel {
    private final Color[] notes = new Color[110];
    private final Image image;
    private boolean displayNotes = false;
    private int noteIndex = 0;
    private int noteWidth = 60;
    private int noteHeight = 40;
    private int noteWidthPadding = 15;
    private int noteHeightPadding = 10;

    public Dibujo(Image image) {
        super();
        this.image = image;
    }

    public int getNoteWidth() {
        return noteWidth;
    }

    public void setNoteWidth(int noteWidth) {
        this.noteWidth = noteWidth;
    }

    public int getNoteHeight() {
        return noteHeight;
    }

    public void setNoteHeight(int noteHeight) {
        this.noteHeight = noteHeight;
    }

    public int getNoteWidthPadding() {
        return noteWidthPadding;
    }

    public void setNoteWidthPadding(int noteWidthPadding) {
        this.noteWidthPadding = noteWidthPadding;
    }

    public int getNoteHeightPadding() {
        return noteHeightPadding;
    }

    public void setNoteHeightPadding(int noteHeightPadding) {
        this.noteHeightPadding = noteHeightPadding;
    }

    public boolean isDisplayNotes() {
        return displayNotes;
    }

    public void setDisplayNotes(boolean displayNotes) {
        this.displayNotes = displayNotes;
    }

    public Color[] getNotes() {
        return notes;
    }

    // replace notes and update screen
    public void setNotes(Color[] colors) {
        if (colors == null) return;
        for (int i = 0; i < notes.length; i++) {
            if (i < colors.length) {
                notes[i] = colors[i];
            } else {
                notes[i] = null;
            }
            noteIndex = i;
        }
        update(getGraphics());
    }

    // check if there is a note at current index
    public boolean hasNote() {
        return noteIndex < notes.length && notes[noteIndex] != null;
    }

    // display next note, if there is one
    public Color nextNote() {
        if (noteIndex >= notes.length) return null;
        Color note = notes[noteIndex++];
        update(this.getGraphics());
        return note;
    }

    // add note and display it
    public boolean addNote(Color note) {
        notes[noteIndex++] = note;
        update(this.getGraphics());
        return noteIndex < notes.length;
    }

    // get current note (pointed by note index)
    public Color getCurrentNote() {
        if (noteIndex < notes.length)
            return notes[noteIndex];
        return null;
    }

    // reset note index, clear note array and clear screen
    public void clearNotes() {
        resetNoteIndex();
        for (int i = 0; i < notes.length; i++)
            notes[i] = null;
        update(this.getGraphics());
    }

    // reset note index to the beginning of the notes array
    public void resetNoteIndex() {
        noteIndex = 0;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (displayNotes) {
            int x = 0;
            int y = 0;
            for (int i = 0; i < notes.length && i < noteIndex; i++) {
                g.setColor(notes[i]);
                // create note rectangle with the specified size and padding
                g.fillRect(x + noteWidthPadding, y + noteHeightPadding, noteWidth - noteWidthPadding, noteHeight - noteHeightPadding);
                x += noteWidth; // advance x coordinate by 1 note size

                // if the next element is a multiple of 11, create a new row
                if ((i + 1) % 11 == 0) {
                    x = 0;
                    y += noteHeight;
                }
            }
        } else {
            g.drawImage(this.image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT), 0, 0, null);
        }
    }
}
