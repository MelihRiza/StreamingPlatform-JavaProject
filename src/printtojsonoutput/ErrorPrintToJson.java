package printtojsonoutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class ErrorPrintToJson {
    private ArrayNode output;

    private static ErrorPrintToJson errorPrintToJson = null;

    private ErrorPrintToJson() {

    }

    /***
     * Clasa este una singleton asa ca definim metoda getInstance
     * ce va creea o instanta a clasei in cazul in care aceasta nu
     * exista deja sau va intoarce instanta existenta
     *
     * @return intoarce instanta ErrorPrintToJson
     */

    public static ErrorPrintToJson getInstance() {
        if (errorPrintToJson == null) {
            errorPrintToJson = new ErrorPrintToJson();
        }
        return errorPrintToJson;
    }

    /***
     * Realizeaza scrierea in fisierul json de output
     */

    public void writeToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode temp = objectMapper.createObjectNode();
        ArrayNode tempPurchasedMovies = objectMapper.createArrayNode();
        temp.put("error", "Error");
        temp.set("currentMoviesList", tempPurchasedMovies);
        temp.set("currentUser", null);
        output.add(temp);
    }

    public void setOutput(final ArrayNode output) {
        this.output = output;
    }
}
