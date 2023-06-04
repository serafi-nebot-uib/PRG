package com.sng;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Taller2 {
    private static final Note[] NOTES = {Note.DO, Note.RE, Note.MI, Note.FA, Note.SOL, Note.LA, Note.SI};
    private static final Color[] NOTES_COLOR = {Color.RED, Color.PINK, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.WHITE, Color.GREEN};
    private static final File[] NOTE_SOUNDS = new File[NOTES.length];
    private static File CHAMPIONS_SOUND = null;
    private static File ERROR_SOUND = null;
    private static JPanel panelBotonesNotas = null;
    private static JButton botonReproducir = null;
    private static JLabel etiqueta = null;
    private static Dibujo dibujo = null;
    private static JPanel panelActividades = null;
    private static Container panelContenidos = null;
    private static Color[] notes = null;
    private static boolean guessNotes = false;

    // get color of note, if it does not exist, null will be returned
    public static Color getNoteColor(Note note) {
        if (note == null) return null;
        int idx = -1;
        for (int i = 0; i < NOTES.length && idx < 0; i++)
            if (note == NOTES[i]) idx = i;

        if (idx < 0) return null;
        return NOTES_COLOR[idx];
    }

    // get note for a given color, if color does not exist, null will be returned
    public static Note getNoteForColor(Color color) {
        if (color == null) return null;
        int idx = -1;
        for (int i = 0; i < NOTES_COLOR.length && idx < 0; i++)
            if (color == NOTES_COLOR[i]) idx = i;
        if (idx < 0) return null;
        return NOTES[idx];
    }

    // get sound for note, if sound does not exist, null will be returned
    private static File getNoteSound(Note note) {
        if (note == null) return null;
        int idx = -1;
        for (int i = 0; i < NOTES.length && idx < 0; i++)
            if (note == NOTES[i]) idx = i;

        if (idx < 0) return null;
        return NOTE_SOUNDS[idx];
    }

    // load sound files
    private static void loadSounds() throws IOException {
        CHAMPIONS_SOUND = new File("sonidos/campeones.wav");
        ERROR_SOUND = new File("sonidos/error.wav");
        for (int i = 0; i < NOTE_SOUNDS.length; i++) {
            String filename = String.format("sonidos/%s.wav", NOTES[i].name());
            File file = new File(filename);
            if (!file.exists()) throw new IOException(String.format("El archivo \"%s\" no existe", filename));
            NOTE_SOUNDS[i] = file;
        }
    }

    // play note sound
    private static void playNote(Note note) {
        playSound(getNoteSound(note));
    }

    // play sound file
    private static void playSound(File sound) {
        if (sound == null) return;
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JButton createButton(String text, Color background, Color foreground) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(background);
        btn.setForeground(foreground);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        return btn;
    }

    // start create mode
    private static void createStart() {
        // display note buttons
        panelActividades.removeAll();
        panelActividades.add(panelBotonesNotas);
        panelActividades.revalidate();
        panelActividades.repaint();
        // reset & display notes
        dibujo.clearNotes();
        dibujo.setDisplayNotes(true);
        // make sure all graphic components are updated
        panelContenidos.revalidate();
    }

    // end create mode
    private static void createEnd() {
        // save played notes
        notes = dibujo.getNotes();
        dibujo.setDisplayNotes(false);
        // display etiqueta
        panelActividades.removeAll();
        panelActividades.add(etiqueta);
        panelActividades.revalidate();
        panelActividades.repaint();
        // make sure all graphic components are updated
        panelContenidos.revalidate();
    }

    // start play mode
    private static void playStart() {
        // set notes and reset note index to 0
        dibujo.setNotes(notes);
        dibujo.setDisplayNotes(true);
        dibujo.resetNoteIndex();
        // display play button
        panelActividades.removeAll();
        panelActividades.add(botonReproducir);
        panelActividades.revalidate();
        panelActividades.repaint();
        // make sure all graphic components are updated
        panelContenidos.revalidate();
    }

    // end play mode
    private static void playEnd() {
        // display etiqueta
        panelActividades.removeAll();
        panelActividades.add(etiqueta);
        panelActividades.revalidate();
        panelActividades.repaint();
        // make sure all graphic components are updated
        panelContenidos.revalidate();
    }

    // start adivinar mode
    private static void guessStart() {
        // set global variable so that note buttons do not create new notes
        guessNotes = true;
        // display note buttons
        panelActividades.removeAll();
        panelActividades.add(panelBotonesNotas);
        panelActividades.revalidate();
        panelActividades.repaint();
        // set notes, reset note index & display notes
        dibujo.setNotes(notes);
        dibujo.resetNoteIndex();
        dibujo.setDisplayNotes(true);
        // make sure all graphic components are updated
        panelContenidos.revalidate();
    }

    // end adivinar mode
    private static void guessEnd() {
        // set global variable so that note buttons create new notes
        guessNotes = false;
        // display etiqueta
        panelActividades.removeAll();
        panelActividades.add(etiqueta);
        panelActividades.revalidate();
        panelActividades.repaint();
        dibujo.setDisplayNotes(true);
        // make sure all graphic components are updated
        panelContenidos.revalidate();
    }

    private static void initPanelBotonesNotas() {
        panelBotonesNotas = new JPanel(new FlowLayout());
        ActionListener noteButtonListener = e -> {
            JButton btn = (JButton) e.getSource();
            Note note = Note.valueOf(btn.getText());

            // if guessNotes is set, current mode is "adivinar"
            if (guessNotes) {
                // get current note pointed by dibujo
                Note currentNote = getNoteForColor(dibujo.getCurrentNote());
                if (note == currentNote) {
                    // note has been guessed, play sound and display note
                    playNote(note);
                    dibujo.nextNote();
                } else if (dibujo.hasNote()) {
                    // note has been missed, play error sound
                    playSound(ERROR_SOUND);
                }

                if (!dibujo.hasNote()) {
                    // note sequence end has been reached, end guess mode and play champions sound
                    guessEnd();
                    playSound(CHAMPIONS_SOUND);
                    // display success pop up
                    JOptionPane pane = new JOptionPane("LO HAS CONSEGUIDO", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
                    pane.setBackground(Color.BLACK);
                    pane.setForeground(Color.YELLOW);
                    JDialog dialog = pane.createDialog("ENHORABUENA");
                    dialog.setVisible(true);
                }
            } else {
                // create and play note
                playNote(note);
                // if we have reached maximum note capacity, end create mode
                if (!dibujo.addNote(getNoteColor(note)))
                    createEnd();
            }
        };

        // create notes buttons from array
        for (int i = 0; i < NOTES.length; i++) {
            JButton noteBtn = createButton(NOTES[i].name(), getNoteColor(NOTES[i]), Color.BLACK);
            noteBtn.addActionListener(noteButtonListener);
            panelBotonesNotas.add(noteBtn);
        }
        JButton botonFIN = createButton("FIN", Color.BLACK, Color.WHITE);
        botonFIN.addActionListener(e -> {
            createEnd();
        });
        panelBotonesNotas.add(botonFIN);
    }

    private static void initBotonReproducir() {
        botonReproducir = new JButton(">");
        botonReproducir.addActionListener(e -> {
            // get next note
            Note note = getNoteForColor(dibujo.nextNote());
            playSound(getNoteSound(note));
            // if note end has been reached, end play mode
            if (!dibujo.hasNote())
                playEnd();
        });
    }

    private static void initEtiqueta() {
        etiqueta = new JLabel("TALLER 2 - PROGRAMACIÓN II - CURSO 2022-2023");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 24));
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        etiqueta.setBackground(Color.BLACK);
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setOpaque(true);
    }

    private static void init() throws IOException {
        JFrame ventana = new JFrame("TALLER 2 - PROGRAMACIÓN II - CURSO 2022-2023");
        panelContenidos = ventana.getContentPane();
        panelContenidos.setLayout(new BoxLayout(panelContenidos, BoxLayout.Y_AXIS));
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dibujo = new Dibujo(ImageIO.read(new File("UIB.jpg")));
        dibujo.setBackground(Color.BLACK);
        dibujo.setOpaque(true);
        dibujo.setPreferredSize(new Dimension(200, 500)); // set minimum preferred size

        panelActividades = new JPanel();
        panelActividades.setLayout(new BorderLayout());
        panelActividades.setOpaque(true);

        initPanelBotonesNotas();
        initBotonReproducir();
        initEtiqueta();
        panelActividades.add(etiqueta, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 4));
        panelBotones.setOpaque(true);

        JButton crear = createButton("CREAR", Color.BLACK, Color.WHITE);
        crear.addActionListener(e -> {
            createStart();
        });
        JButton reproducir = createButton("REPRODUCIR", Color.BLACK, Color.WHITE);
        reproducir.addActionListener(e -> {
            playStart();
        });
        JButton adivinar = createButton("ADIVINAR", Color.BLACK, Color.WHITE);
        adivinar.addActionListener(e -> {
            guessStart();
        });
        JButton salir = createButton("SALIR", Color.BLACK, Color.WHITE);
        salir.addActionListener(e -> {
            // close window and exit program
            ventana.dispose();
            System.exit(0);
        });
        panelBotones.add(crear);
        panelBotones.add(reproducir);
        panelBotones.add(adivinar);
        panelBotones.add(salir);

        JSplitPane separadorOeste = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dibujo, panelActividades);
        JSplitPane separadorEste = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelActividades, panelBotones);

        panelContenidos.add(separadorOeste);
        panelContenidos.add(separadorEste);

        ventana.setSize(700, 650);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            loadSounds();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // display error pop up
            JOptionPane pane = new JOptionPane("ERROR AL ABRIR FICHEROS DE SONIDO: " + e.getMessage(), JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
            pane.setBackground(Color.BLACK);
            pane.setForeground(Color.YELLOW);
            JDialog dialog = pane.createDialog("ERROR");
            dialog.setVisible(true);
            System.exit(-1);
        }

        try {
            init();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // display error pop up
            JOptionPane pane = new JOptionPane("ERROR EN LA EJECUCIÓN DEL PROGRAMA: " + e.getMessage(), JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
            pane.setBackground(Color.BLACK);
            pane.setForeground(Color.YELLOW);
            JDialog dialog = pane.createDialog("ERROR");
            dialog.setVisible(true);
            System.exit(-1);
        }
    }
}