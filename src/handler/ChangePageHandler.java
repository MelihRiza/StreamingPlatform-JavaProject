package handler;

import pages.CurrentPage;
import printtojsonoutput.ErrorPrintToJson;
import printtojsonoutput.SeeDetailsMovieToJson;
import printtojsonoutput.SuccesPrintToJson;
import com.fasterxml.jackson.databind.node.ArrayNode;
import classestogettheinput.Action;
import classestogettheinput.Input;
import classestogettheinput.Movie;
import classestogettheinput.User;

import java.util.ArrayList;

public final class ChangePageHandler {
    private Input input;
    private User user;
    private Action action;
    private CurrentPage currentPage;
    private ArrayNode output;

    private static ChangePageHandler changePageHandler = null;
    private static final ErrorPrintToJson ERROR_PRINT_TO_JSON =
            ErrorPrintToJson.getInstance();
    private static final SeeDetailsMovieToJson SEE_DETAILS_MOVIE_TO_JSON =
            SeeDetailsMovieToJson.getInstance();
    private static final SuccesPrintToJson SUCCES_PRINT_TO_JSON =
            SuccesPrintToJson.getInstance();

    private ChangePageHandler() {

    }

    /***
     * Clasa este una singleton asa ca definim metoda getInstance
     * ce va creea o instanta a clasei in cazul in care aceasta nu
     * exista deja sau va intoarce instanta existenta
     *
     * @return intoarce instanta ChangePageHandler
     */

    public static ChangePageHandler getInstance() {
        if (changePageHandler == null) {
            changePageHandler = new ChangePageHandler();
        }
        return changePageHandler;
    }

    /***
     * Metoda ce executa de comanda de schimbare a paginii primita:
     * -> Schimba pagina curenta in cazul in care se poate
     * -> Afiseaza eroare in caz contrar in fisierul json de output
     */

    public User exec() {

        if (currentPage.getCurrentPage().equals("HomePageNeautentificat")) {
            if (action.getPage().equals("login")) {
                currentPage.setCurrentPage("login");
            } else if (action.getPage().equals("register")) {
                currentPage.setCurrentPage("register");
            } else if (action.getPage().equals("movies")) {
                ERROR_PRINT_TO_JSON.setOutput(output);
                ERROR_PRINT_TO_JSON.writeToJson();
                currentPage.setCurrentPage("HomePageNeautentificat");
            } else if (action.getPage().equals("logout")) {
                ERROR_PRINT_TO_JSON.setOutput(output);
                ERROR_PRINT_TO_JSON.writeToJson();
                currentPage.setCurrentPage("HomePageNeautentificat");
            }
        } else if (currentPage.getCurrentPage().equals("HomePageAutentificat")) {
            if (action.getPage().equals("login")) {
                ERROR_PRINT_TO_JSON.setOutput(output);
                ERROR_PRINT_TO_JSON.writeToJson();
            } else if (action.getPage().equals("logout")) {
                currentPage.setCurrentPage("HomePageNeautentificat");
            } else if (action.getPage().equals("movies")) {
                currentPage.setCurrentPage("movies");
                ArrayList<Movie> moviesAvailable = new ArrayList<>();
                for (Movie movie : input.getMovies()) {
                    if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                        moviesAvailable.add(movie);
                    }
                }
                user.setMoviesList(moviesAvailable);
                user.setOriginalMoviesList(moviesAvailable);
                SUCCES_PRINT_TO_JSON.setOutput(output);
                SUCCES_PRINT_TO_JSON.setUser(user);
                SUCCES_PRINT_TO_JSON.writeToJson();
            } else if (action.getPage().equals("upgrades")) {
                currentPage.setCurrentPage("upgrades");
            }
        } else if (currentPage.getCurrentPage().equals("movies")) {
            if (action.getPage().equals("see details")) {
                currentPage.setCurrentPage("see details");
                boolean movieExists = false;
                ArrayList<Movie> movieForDetails = new ArrayList<>();
                for (Movie movie : user.getMoviesList()) {
                    if (movie.getName().equals(action.getMovie())) {
                        user.setCurrentMovie(movie);
                        movieExists = true;
                        movieForDetails.add(movie);
                        SEE_DETAILS_MOVIE_TO_JSON.setUser(user);
                        SEE_DETAILS_MOVIE_TO_JSON.setOutput(output);
                        SEE_DETAILS_MOVIE_TO_JSON.writeToJson(movieForDetails);
                    }
                }
                if (!movieExists) {
                    currentPage.setCurrentPage("movies");
                    ERROR_PRINT_TO_JSON.setOutput(output);
                    ERROR_PRINT_TO_JSON.writeToJson();
                }
            } else if (action.getPage().equals("logout")) {
                currentPage.setCurrentPage("HomePageNeautentificat");
                user = null;
            } else if (action.getPage().equals("login")) {
                currentPage.setCurrentPage("movies");
                ERROR_PRINT_TO_JSON.setOutput(output);
                ERROR_PRINT_TO_JSON.writeToJson();
            } else if (action.getPage().equals("movies")) {
                ArrayList<Movie> moviesAvailable = new ArrayList<>();
                for (Movie movie : input.getMovies()) {
                    if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                        moviesAvailable.add(movie);
                    }
                }
                user.setMoviesList(moviesAvailable);
                user.setOriginalMoviesList(moviesAvailable);
                currentPage.setCurrentPage("movies");
                SUCCES_PRINT_TO_JSON.setOutput(output);
                SUCCES_PRINT_TO_JSON.setUser(user);
                SUCCES_PRINT_TO_JSON.writeToJson();
            }
        } else if (currentPage.getCurrentPage().equals("upgrades")) {
            if (action.getPage().equals("movies")) {
                currentPage.setCurrentPage("movies");
                ArrayList<Movie> moviesAvailable = new ArrayList<>();
                for (Movie movie : input.getMovies()) {
                    if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                        moviesAvailable.add(movie);
                    }
                }
                user.setMoviesList(moviesAvailable);
                user.setOriginalMoviesList(moviesAvailable);
                SUCCES_PRINT_TO_JSON.setOutput(output);
                SUCCES_PRINT_TO_JSON.setUser(user);
                SUCCES_PRINT_TO_JSON.writeToJson();
            }
        } else if (currentPage.getCurrentPage().equals("see details")) {
            if (action.getPage().equals("movies")) {
                ArrayList<Movie> moviesAvailable = new ArrayList<>();
                for (Movie movie : input.getMovies()) {
                    if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                        moviesAvailable.add(movie);
                    }
                }
                user.setMoviesList(moviesAvailable);
                user.setOriginalMoviesList(moviesAvailable);
                currentPage.setCurrentPage("movies");
                SUCCES_PRINT_TO_JSON.setOutput(output);
                SUCCES_PRINT_TO_JSON.setUser(user);
                SUCCES_PRINT_TO_JSON.writeToJson();
            } else if (action.getPage().equals("logout")) {
                currentPage.setCurrentPage("HomePageNeautentificat");
                user = null;
            }
        } else {
            ERROR_PRINT_TO_JSON.setOutput(output);
            ERROR_PRINT_TO_JSON.writeToJson();
        }
        return user;
    }

    public void setInput(final Input input) {
        this.input = input;
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
}
