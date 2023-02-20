package printtojsonoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import classestogettheinput.Movie;
import classestogettheinput.User;

import java.util.ArrayList;

public final class SeeDetailsMovieToJson {
    private ArrayNode output;
    private User user;

    private static SeeDetailsMovieToJson seeDetailsMovieToJson = null;

    private SeeDetailsMovieToJson() {

    }

    /***
     * Clasa este una singleton asa ca definim metoda getInstance
     * ce va creea o instanta a clasei in cazul in care aceasta nu
     * exista deja sau va intoarce instanta existenta
     *
     * @return intoarce instanta SeeDetailsMovieToJson
     */

    public static SeeDetailsMovieToJson getInstance() {
        if (seeDetailsMovieToJson == null) {
            seeDetailsMovieToJson = new SeeDetailsMovieToJson();
        }
        return seeDetailsMovieToJson;
    }

    /***
     * Realizeaza scrierea in fisierul json de output
     */

    public void writeToJson(final ArrayList<Movie> movies) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode temp = objectMapper.createObjectNode();
        ObjectNode temp1 = objectMapper.createObjectNode();
        ObjectNode temp2 = objectMapper.createObjectNode();
        ArrayNode currentMovies;
        ArrayNode currentPurchasedMovies;
        ArrayNode watchedMovies;
        ArrayNode likedMovies;
        ArrayNode ratedMovies;

            temp2.set("error", null);

            IterateInMoviesHelper iterateInMoviesHelper = new IterateInMoviesHelper();

            currentMovies = iterateInMoviesHelper.iterate(movies);
            temp2.set("currentMoviesList", currentMovies);

            temp.put("name", user.getCredentials().getName());
            temp.put("password", user.getCredentials().getPassword());
            temp.put("accountType", user.getCredentials().getAccountType());
            temp.put("country", user.getCredentials().getCountry());
            temp.put("balance", user.getCredentials().getBalance());

            temp1.set("credentials", temp);

            temp1.put("tokensCount", user.getTokensCount());
            temp1.put("numFreePremiumMovies", user.getNumFreePremiumMovies());


            currentPurchasedMovies = iterateInMoviesHelper.iterate(user.getPurchasedMovies());
            temp1.put("purchasedMovies", currentPurchasedMovies);

            watchedMovies = iterateInMoviesHelper.iterate(user.getWatchedMovies());
            temp1.put("watchedMovies", watchedMovies);

            likedMovies = iterateInMoviesHelper.iterate(user.getLikedMovies());
            temp1.put("likedMovies", likedMovies);

            ratedMovies = iterateInMoviesHelper.iterate(user.getRatedMovies());
            temp1.put("ratedMovies", ratedMovies);

            temp2.set("currentUser", temp1);

            output.add(temp2);
    }

    public void setOutput(final ArrayNode output) {
        this.output = output;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
