package classestogettheinput;

import java.util.ArrayList;

public final class User {
    private Credentials credentials;
    private ArrayList<Movie> moviesList = new ArrayList<>();
    private ArrayList<Movie> searchMovie = new ArrayList<>();
    private int tokensCount = 0;
    private static final int NUMBER_OF_FREE_MOVIES = 15;
    private int numFreePremiumMovies = NUMBER_OF_FREE_MOVIES;
    private ArrayList<Movie> originalMoviesList = new ArrayList<>();
    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();
    private ArrayList<Movie> fileredMovies = new ArrayList<>();
    private Movie currentMovie = new Movie();


    public User() {

    }

    public ArrayList<Movie> getOriginalMoviesList() {
        return originalMoviesList;
    }

    public void setOriginalMoviesList(ArrayList<Movie> originalMoviesList) {
        this.originalMoviesList = originalMoviesList;
    }

    public ArrayList<Movie> getFileredMovies() {
        return fileredMovies;
    }

    public void setFileredMovies(ArrayList<Movie> fileredMovies) {
        this.fileredMovies = fileredMovies;
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public void setCurrentMovie(final Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    public ArrayList<Movie> getSearchMovie() {
        return searchMovie;
    }

    public void setSearchMovie(final ArrayList<Movie> searchMovie) {
        this.searchMovie = searchMovie;
    }

    public User(final String name, final String password, final String accountType,
                final String country, final String balance) {
        this.credentials = new Credentials(name, password, accountType, country, balance);
    }

    public ArrayList<Movie> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(final ArrayList<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }
}
