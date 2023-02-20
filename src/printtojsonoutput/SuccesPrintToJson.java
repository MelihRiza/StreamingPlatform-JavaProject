package printtojsonoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import classestogettheinput.User;

public final class SuccesPrintToJson {
    private ArrayNode output;
    private User user;

    private static SuccesPrintToJson succesPrintToJson = null;

    private SuccesPrintToJson() {

    }

    /***
     * Clasa este una singleton asa ca definim metoda getInstance
     * ce va creea o instanta a clasei in cazul in care aceasta nu
     * exista deja sau va intoarce instanta existenta
     *
     * @return intoarce instanta SuccesPrintToJson
     */

    public static SuccesPrintToJson getInstance() {
        if (succesPrintToJson == null) {
            succesPrintToJson = new SuccesPrintToJson();
        }
        return succesPrintToJson;
    }
    /***
     * Realizeaza scrierea in fisierul json de output
     */

    public void writeToJson() {
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

        currentMovies = iterateInMoviesHelper.iterate(user.getMoviesList());

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
