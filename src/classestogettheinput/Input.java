package classestogettheinput;

import java.util.ArrayList;

public final class Input {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<Action> actions = new ArrayList<>();

    public Input() {

    }

    public Input(final ArrayList<User> users, final ArrayList<Movie> movies,
                 final ArrayList<Action> actions) {
        this.users = users;
        this.movies = movies;
        this.actions = actions;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<Action> actions) {
        this.actions = actions;
    }
}
