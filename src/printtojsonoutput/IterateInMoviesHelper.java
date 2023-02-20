package printtojsonoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import classestogettheinput.Movie;

import java.util.ArrayList;

public final class IterateInMoviesHelper {

    public IterateInMoviesHelper() {
    }

    /***
     * Metoda ce itereaza prin filmele din lista realizand
     * maparea caracteristicilor acestora intr-ul arraynode ce
     * va fi intors
     * @param movies lista de filme
     * @return arraynode-ul rezultat
     */

    public ArrayNode iterate(final ArrayList<Movie> movies) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode result = objectMapper.createArrayNode();
        for (Movie movie : movies) {
            ObjectNode movieDetails = objectMapper.createObjectNode();
            movieDetails.put("name", movie.getName());
            movieDetails.put("year", movie.getYear());
            movieDetails.put("duration", movie.getDuration());

            ArrayNode genres = objectMapper.createArrayNode();
            for (String gen : movie.getGenres()) {
                genres.add(gen);
            }
            movieDetails.set("genres", genres);

            ArrayNode actors = objectMapper.createArrayNode();

            for (String actrs : movie.getActors()) {
                actors.add(actrs);
            }

            movieDetails.put("actors", actors);

            ArrayNode countriesBanned = objectMapper.createArrayNode();
            for (String countries : movie.getCountriesBanned()) {
                countriesBanned.add(countries);
            }

            movieDetails.set("countriesBanned", countriesBanned);

            movieDetails.put("numLikes", movie.getNumLikes());
            movieDetails.put("rating", movie.getRating());
            movieDetails.put("numRatings", movie.getNumRatings());
            result.add(movieDetails);
        }
        return result;
    }
}
