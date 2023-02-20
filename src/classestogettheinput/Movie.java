package classestogettheinput;

import java.util.ArrayList;

public final class Movie implements Comparable<Movie> {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors = new ArrayList<>();
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating;
    private int numRatings;
    private int sumRatings;

    public Movie() {

    }

    public int getSumRatings() {
        return sumRatings;
    }

    public void setSumRatings(final int sumRatings) {
        this.sumRatings = sumRatings;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    @Override
    public int compareTo(final Movie o) {
        if (duration == o.duration) {
            if (rating > o.rating) {
                return 1;
            } else if (rating < o.rating) {
                return -1;
            } else {
                return 0;
            }
        } else {
            if (duration > o.duration) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
