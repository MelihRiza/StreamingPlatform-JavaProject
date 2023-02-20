package classestogettheinput;

import java.util.ArrayList;

public final class Contain {
    private ArrayList<String> genre;
    private ArrayList<String> actors;

    public Contain() {

    }

    public Contain(final ArrayList<String> genre, final ArrayList<String> actors,
                   final ArrayList<String> country) {
        this.genre = genre;
        this.actors = actors;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(final ArrayList<String> genre) {
        this.genre = genre;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }
}
