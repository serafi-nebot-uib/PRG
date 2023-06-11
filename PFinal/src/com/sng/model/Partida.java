package com.sng.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Partida implements Serializable {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private String player;
    private Date date;
    private int points;

    public Partida() {
    }

    public Partida(String player, Date date, int points) {
        this.player = player;
        this.date = date;
        this.points = points;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return String.format("JUGADOR: %s\t- FECHA: %s\t- PUNTOS: %d puntos.", this.player, DATE_FORMAT.format(this.date), this.points);
    }
}
