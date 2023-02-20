package handler;

import classestogettheinput.*;
import pages.CurrentPage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import printtojsonoutput.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class OnPageHandler {
    private Action action;
    private CurrentPage currentPage;
    private ArrayNode output;
    private Database database;
    private User user;

    private static final int PREMIUM_ACCOUNT_PRICE = 10;
    private static final int MOVIE_PRICE = 2;
    private static final int MAX_RATING = 5;


    private static OnPageHandler onPageHandler = null;
    private static final ErrorPrintToJson ERROR_PRINT_TO_JSON =
            ErrorPrintToJson.getInstance();
    private static final JustLogedInToJson JUST_LOGED_IN_TO_JSON =
            JustLogedInToJson.getInstance();
    private static final SearchMovieToJson SEARCH_MOVIE_TO_JSON =
            SearchMovieToJson.getInstance();
    private static final SeeDetailsMovieToJson SEE_DETAILS_MOVIE_TO_JSON =
            SeeDetailsMovieToJson.getInstance();
    private static final SuccesPrintToJson SUCCES_PRINT_TO_JSON =
            SuccesPrintToJson.getInstance();

    private OnPageHandler() {

    }

    /***
     * Clasa este una singleton asa ca definim metoda getInstance
     * ce va creea o instanta a clasei in cazul in care aceasta nu
     * exista deja sau va intoarce instanta existenta
     *
     * @return intoarce instanta OnPageHandler
     */
    public static OnPageHandler getInstance() {
        if (onPageHandler == null) {
            onPageHandler = new OnPageHandler();
        }
        return onPageHandler;
    }

    /***
     * Metoda ce executa comanda primita pe o anumita pagina
     * -> Comanda se executa in cazul in care este posibila
     * -> Afiseaza erore in fisierul json de output in caz contrar
     * @return se returneaza un utilizator (User) curent ce rezulta
     * dupa executia comenzilor
     */

    public User exec() {
        if (currentPage.getCurrentPage().equals("login") && action.getFeature().equals("login")) {
            if (database.getAccounts().containsKey(action.getCredentials().getName())
                    && database.getAccounts().
                    get(action.getCredentials().getName()).
                    equals(action.getCredentials().getPassword())) {
                currentPage.setCurrentPage("HomePageAutentificat");
                user = database.returnUser(action.getCredentials().
                        getName(), action.getCredentials().getPassword());
                JUST_LOGED_IN_TO_JSON.setUser(user);
                JUST_LOGED_IN_TO_JSON.setOutput(output);
                JUST_LOGED_IN_TO_JSON.writeToJson();
            } else {
                currentPage.setCurrentPage("HomePageNeautentificat");
                ERROR_PRINT_TO_JSON.setOutput(output);
                ERROR_PRINT_TO_JSON.writeToJson();
            }
        } else if (!currentPage.getCurrentPage().equals("login")
                && action.getFeature().equals("login")) {
            ERROR_PRINT_TO_JSON.setOutput(output);
            ERROR_PRINT_TO_JSON.writeToJson();
        } else if (currentPage.getCurrentPage().equals("register")
                && action.getFeature().equals("register")) {
            if (!database.getAccounts().containsKey(action.getCredentials().getName())) {
                user = new User(action.getCredentials().getName(),
                        action.getCredentials().getPassword(),
                        action.getCredentials().getAccountType(),
                        action.getCredentials().getCountry(),
                        action.getCredentials().getBalance());
                database.addRegisteredUser(user);
                currentPage.setCurrentPage("HomePageAutentificat");
                SUCCES_PRINT_TO_JSON.setOutput(output);
                SUCCES_PRINT_TO_JSON.setUser(user);
                SUCCES_PRINT_TO_JSON.writeToJson();
            } else {
                currentPage.setCurrentPage("HomePageNeautentificat");
                ERROR_PRINT_TO_JSON.setOutput(output);
                ERROR_PRINT_TO_JSON.writeToJson();
            }
        } else if (currentPage.getCurrentPage().equals("movies")) {
            if (action.getFeature().equals("search")) {
                ArrayList<Movie> afterSearchList = new ArrayList<>();
                for (Movie movie : user.getMoviesList()) {
                    if (movie.getName().startsWith(action.getStartsWith())) {
                        afterSearchList.add(movie);
                    }
                }
                user.setSearchMovie(afterSearchList);
                SEARCH_MOVIE_TO_JSON.setUser(user);
                SEARCH_MOVIE_TO_JSON.setOutput(output);
                SEARCH_MOVIE_TO_JSON.writeToJson();
            }
            if (action.getFeature().equals("filter")) {
                Filter filter = action.getFilters();
                Sort sort = new Sort();
                Contain contain = new Contain();
                if (filter.getSort() != null) {
                    sort = filter.getSort();
                }
                boolean semafor = false;
                if (sort.getDuration() != null) {
                    if (sort.getDuration().equals("decreasing")) {
                        Collections.sort(user.getMoviesList());
                    } else if (sort.getDuration().equals("increasing")) {
                        Collections.reverse(user.getMoviesList());
                    }
                    semafor = true;
                    SUCCES_PRINT_TO_JSON.setOutput(output);
                    SUCCES_PRINT_TO_JSON.setUser(user);
                    SUCCES_PRINT_TO_JSON.writeToJson();
                    return user;
                }
                if (!semafor && sort.getRating() != null) {
                    if (sort.getRating().equals("increasing")) {
                        user.getMoviesList().sort(Comparator.
                                comparing(Movie::getRating));
                    } else if (sort.getRating().equals("decreasing")) {
                        user.getMoviesList().sort(Comparator.
                                comparing(Movie::getRating).reversed());
                    }
                    SUCCES_PRINT_TO_JSON.setOutput(output);
                    SUCCES_PRINT_TO_JSON.setUser(user);
                    SUCCES_PRINT_TO_JSON.writeToJson();
                    return user;
                }
                if (filter.getContains() != null) {
                    contain = filter.getContains();

                    ArrayList<Movie> moviesFiltered = new ArrayList<>();

                    if (contain.getActors() != null && contain.getGenre() == null) {
                        for (Movie movie : user.getOriginalMoviesList()) {
                            if(movie.getActors().containsAll(contain.getActors())) {
                                moviesFiltered.add(movie);
                            }
                        }
                    }
                    if (contain.getGenre() != null && contain.getActors() == null) {
                        for (Movie movie : user.getOriginalMoviesList()) {
                            if (movie.getGenres().containsAll(contain.getGenre())) {
                                moviesFiltered.add(movie);
                            }
                        }
                    } else if (contain.getActors() != null && contain.getGenre() != null){
                        for (Movie movie : user.getOriginalMoviesList()) {
                            if (movie.getGenres().containsAll(contain.getGenre())
                                    && movie.getActors().containsAll(contain.getActors())) {
                                moviesFiltered.add(movie);
                            }
                        }
                    }
                    user.setFileredMovies(moviesFiltered);
                    user.setMoviesList(moviesFiltered);

                    if (moviesFiltered.isEmpty()) {
                        SUCCES_PRINT_TO_JSON.setOutput(output);
                        SUCCES_PRINT_TO_JSON.setUser(user);
                        SUCCES_PRINT_TO_JSON.writeToJson();
                    }

                    if (!moviesFiltered.isEmpty()) {
                        SUCCES_PRINT_TO_JSON.setOutput(output);
                        SUCCES_PRINT_TO_JSON.setUser(user);
                        SUCCES_PRINT_TO_JSON.writeToJson();
                    }
                }
            }
            if (action.getFeature().equals("see details")) {
                ERROR_PRINT_TO_JSON.setOutput(output);
                ERROR_PRINT_TO_JSON.writeToJson();
            }
            if (action.getFeature().equals("purchase") || action.getFeature().equals("watch")
                || action.getFeature().equals("rate") || action.getFeature().equals("like")) {
                ERROR_PRINT_TO_JSON.setOutput(output);
                ERROR_PRINT_TO_JSON.writeToJson();
            }
        } else if (currentPage.getCurrentPage().equals("upgrades")) {
            if (action.getFeature().equals("buy tokens")) {
                user.setTokensCount(action.getCount());
                int remainingCredits = Integer.
                        parseInt(user.getCredentials().getBalance()) - action.getCount();
                user.getCredentials().setBalance(Integer.toString(remainingCredits));
            } else if (action.getFeature().equals("buy premium account")) {
                user.setTokensCount(user.getTokensCount() - PREMIUM_ACCOUNT_PRICE);
                user.getCredentials().setAccountType("premium");
            }
        } else if (currentPage.getCurrentPage().equals("see details")) {
            if (action.getMovie() != null) {
                if (action.getFeature().equals("purchase")) {
                    if (user.getCredentials().getAccountType().equals("premium")
                            && user.getNumFreePremiumMovies() > 0) {
                        for (Movie movie : user.getMoviesList()) {
                            if (movie.getName().equals(action.getMovie())) {
                                user.getPurchasedMovies().add(movie);
                                user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() - 1);
                                SEE_DETAILS_MOVIE_TO_JSON.setUser(user);
                                SEE_DETAILS_MOVIE_TO_JSON.setOutput(output);
                                SEE_DETAILS_MOVIE_TO_JSON.writeToJson(user.getPurchasedMovies());
                            }
                        }
                    } else {
                        for (Movie movie : user.getMoviesList()) {
                            if (movie.getName().equals(action.getMovie())) {
                                if (user.getTokensCount() >= MOVIE_PRICE) {
                                    user.getPurchasedMovies().add(movie);
                                    user.setTokensCount(user.getTokensCount() - MOVIE_PRICE);
                                    SEE_DETAILS_MOVIE_TO_JSON.setUser(user);
                                    SEE_DETAILS_MOVIE_TO_JSON.setOutput(output);
                                    SEE_DETAILS_MOVIE_TO_JSON.
                                            writeToJson(user.getPurchasedMovies());
                                }
                            }
                        }
                    }
                } else if (action.getFeature().equals("watch")) {
                    boolean found = false;
                    for (Movie movie : user.getPurchasedMovies()) {
                        if (movie.getName().equals(action.getMovie())) {
                            found = true;
                            user.getWatchedMovies().add(movie);
                            SEE_DETAILS_MOVIE_TO_JSON.setUser(user);
                            SEE_DETAILS_MOVIE_TO_JSON.setOutput(output);
                            SEE_DETAILS_MOVIE_TO_JSON.writeToJson(user.getWatchedMovies());
                        }
                    }
                    if (!found) {
                        ERROR_PRINT_TO_JSON.setOutput(output);
                        ERROR_PRINT_TO_JSON.writeToJson();
                    }
                } else if (action.getFeature().equals("like")) {
                    boolean found = false;
                    for (Movie movie : user.getWatchedMovies()) {
                        if (movie.getName().equals(action.getMovie())) {
                            found = true;
                            user.getLikedMovies().add(movie);
                            movie.setNumLikes(movie.getNumLikes() + 1);
                            SEE_DETAILS_MOVIE_TO_JSON.setUser(user);
                            SEE_DETAILS_MOVIE_TO_JSON.setOutput(output);
                            SEE_DETAILS_MOVIE_TO_JSON.writeToJson(user.getLikedMovies());
                        }
                    }
                    if (!found) {
                        ERROR_PRINT_TO_JSON.setOutput(output);
                        ERROR_PRINT_TO_JSON.writeToJson();
                    }
                } else if (action.getFeature().equals("rate")) {
                    if (action.getRate() > MAX_RATING) {
                        ERROR_PRINT_TO_JSON.setOutput(output);
                        ERROR_PRINT_TO_JSON.writeToJson();
                    }
                    boolean found = false;
                    for (Movie movie : user.getWatchedMovies()) {
                        if (movie.getName().equals(action.getMovie())) {
                            user.getRatedMovies().add(movie);
                            SEE_DETAILS_MOVIE_TO_JSON.setUser(user);
                            SEE_DETAILS_MOVIE_TO_JSON.setOutput(output);
                            SEE_DETAILS_MOVIE_TO_JSON.writeToJson(user.getRatedMovies());
                        }
                    }
                    if (!found) {
                        ERROR_PRINT_TO_JSON.setOutput(output);
                        ERROR_PRINT_TO_JSON.writeToJson();
                    }
                }
            } else {
                if (action.getFeature().equals("purchase")) {
                    if (user.getCredentials().getAccountType().equals("premium")
                        && user.getNumFreePremiumMovies() > 0) {
                        for (Movie movie : user.getMoviesList()) {
                            if (movie.getName().equals(user.getCurrentMovie().getName())) {
                                user.getPurchasedMovies().add(user.getCurrentMovie());
                                user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() - 1);
                                ArrayList<Movie> moviePurchased = new ArrayList<>();
                                moviePurchased.add(user.getCurrentMovie());
                                SEE_DETAILS_MOVIE_TO_JSON.setUser(user);
                                SEE_DETAILS_MOVIE_TO_JSON.setOutput(output);
                                SEE_DETAILS_MOVIE_TO_JSON.writeToJson(moviePurchased);
                            }
                        }
                    } else if (user.getTokensCount() >= 2) {
                        for (Movie movie : user.getMoviesList()) {
                            if (movie.getName().equals(user.getCurrentMovie().getName())) {
                                user.getPurchasedMovies().add(user.getCurrentMovie());
                                user.setTokensCount(user.getTokensCount() - 2);
                                ArrayList<Movie> moviePurchased = new ArrayList<>();
                                moviePurchased.add(user.getCurrentMovie());
                                SEE_DETAILS_MOVIE_TO_JSON.setUser(user);
                                SEE_DETAILS_MOVIE_TO_JSON.setOutput(output);
                                SEE_DETAILS_MOVIE_TO_JSON.writeToJson(moviePurchased);
                            }
                        }
                    }
                } else if (action.getFeature().equals("watch")) {
                    boolean found = false;
                    for (Movie movie : user.getPurchasedMovies()) {
                        if (movie.getName().equals(user.getCurrentMovie().getName())) {
                            found = true;
                            user.getWatchedMovies().add(user.getCurrentMovie());
                            ArrayList<Movie> movieWatched = new ArrayList<>();
                            movieWatched.add(user.getCurrentMovie());
                            SEE_DETAILS_MOVIE_TO_JSON.setUser(user);
                            SEE_DETAILS_MOVIE_TO_JSON.setOutput(output);
                            SEE_DETAILS_MOVIE_TO_JSON.writeToJson(movieWatched);
                        }
                    }
                    if (!found) {
                        ERROR_PRINT_TO_JSON.setOutput(output);
                        ERROR_PRINT_TO_JSON.writeToJson();
                    }
                } else if (action.getFeature().equals("like")) {
                    boolean found = false;
                    for (Movie movie : user.getWatchedMovies()) {
                        if (movie.getName().equals(user.getCurrentMovie().getName())) {
                            found = true;
                            user.getLikedMovies().add(user.getCurrentMovie());
                            movie.setNumLikes(movie.getNumLikes() + 1);
                            ArrayList<Movie> movieLiked = new ArrayList<>();
                            movieLiked.add(user.getCurrentMovie());
                            SEE_DETAILS_MOVIE_TO_JSON.setUser(user);
                            SEE_DETAILS_MOVIE_TO_JSON.setOutput(output);
                            SEE_DETAILS_MOVIE_TO_JSON.writeToJson(movieLiked);
                        }
                    }
                    if (!found) {
                        ERROR_PRINT_TO_JSON.setOutput(output);
                        ERROR_PRINT_TO_JSON.writeToJson();
                    }
                } else if (action.getFeature().equals("rate")) {
                    if (action.getRate() > MAX_RATING) {
                        ERROR_PRINT_TO_JSON.setOutput(output);
                        ERROR_PRINT_TO_JSON.writeToJson();
                        return user;
                    }
                    boolean found = false;
                    for (Movie movie : user.getWatchedMovies()) {
                        if (movie.getName().equals(user.getCurrentMovie().getName())) {
                            found = true;
                            user.getRatedMovies().add(user.getCurrentMovie());
                            movie.setNumRatings(movie.getNumRatings() + 1);
                            movie.setSumRatings(movie.getSumRatings() + action.getRate());
                            int sum = movie.getSumRatings();
                            double rating = (double) sum / movie.getNumRatings();
                            movie.setRating(rating);
                            ArrayList<Movie> movieRated = new ArrayList<>();
                            movieRated.add(user.getCurrentMovie());
                            SEE_DETAILS_MOVIE_TO_JSON.setUser(user);
                            SEE_DETAILS_MOVIE_TO_JSON.setOutput(output);
                            SEE_DETAILS_MOVIE_TO_JSON.writeToJson(movieRated);
                        }
                    }
                    if (!found) {
                        ERROR_PRINT_TO_JSON.setOutput(output);
                        ERROR_PRINT_TO_JSON.writeToJson();
                    }
                }
            }
        } else {
            ERROR_PRINT_TO_JSON.setOutput(output);
            ERROR_PRINT_TO_JSON.writeToJson();
        }
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public void setAction(final Action action) {
        this.action = action;
    }

    public void setCurrentPage(final CurrentPage currentPage) {
        this.currentPage = currentPage;
    }

    public void setOutput(final ArrayNode output) {
        this.output = output;
    }

    public void setDatabase(final Database database) {
        this.database = database;
    }
}
