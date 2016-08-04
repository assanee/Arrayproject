package xyz.stepsecret.arrayproject3.TabFragments.models;

/**
 * Created by Lincoln on 15/01/16.
 */
public class QueueModel {
    private String title, genre, year;

    public QueueModel() {
    }

    public QueueModel(String title, String genre, String year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
