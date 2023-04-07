package com.sng;

import java.io.Serializable;

public class Film implements Serializable {
    private String title;
    private String director;
    private Integer year;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Film() {
    }

    public Film(String title, String director, Integer year) {
        this.title = title;
        this.director = director;
        this.year = year;
    }

    public static Film createFromUserInput() {
        Film film = new Film();

        System.out.print("Enter film's title: ");
        film.title = LT.readLine();

        System.out.print("Enter film's director: ");
        film.director = LT.readLine();

        film.year = null;
        while (film.year == null) {
            try {
                System.out.print("Enter film's release year: ");
                film.year = LT.readInt();
            } catch (NumberFormatException ignored) {
                System.out.println("[ERROR] film's year must be an integer");
            }
        }

        return film;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", year=" + year +
                '}';
    }
}
