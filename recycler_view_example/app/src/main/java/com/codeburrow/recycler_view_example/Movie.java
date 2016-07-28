package com.codeburrow.recycler_view_example;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jul/26/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class Movie {

    private static final String LOG_TAG = Movie.class.getSimpleName();

    private String title;
    private String genre;
    private String year;

    /* Constructors */
    public Movie() {
    }

    public Movie(String title, String genre, String year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    /* Setters and Getters */
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
