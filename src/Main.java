import handler.ChangePageHandler;
import handler.OnPageHandler;
import pages.CurrentPage;
import classestogettheinput.Action;
import classestogettheinput.Database;
import classestogettheinput.Input;
import classestogettheinput.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;

public class Main {
    /***
     * Intrarea in program ce realizeaza parsarea comenzilor de input,
     * stocarea datelor din fisierul json intr-o baza de date
     * si apelarea metodelor corespunzatoare rezolvarii comezilor
     * primite
     * @param args contine fisierul de input si cel de output
     */
    public static void main(final String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        Input input;
        try {
            input = objectMapper.readValue(new File(args[0]), Input.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayNode output = objectMapper.createArrayNode();
        Database database = Database.getInstance(input.getUsers(), input.getMovies());


        CurrentPage currentPage = CurrentPage.getInstance();
        currentPage.setCurrentPage("HomePageNeautentificat");

        ChangePageHandler changePageHandler = ChangePageHandler.getInstance();
        OnPageHandler onPageHandler = OnPageHandler.getInstance();

        User currentUser = null;

        for (Action action : input.getActions()) {
            if (action.getType().equals("change page")) {
                changePageHandler.setInput(input);
                changePageHandler.setCurrentPage(currentPage);
                changePageHandler.setAction(action);
                changePageHandler.setUser(currentUser);
                changePageHandler.setOutput(output);
                changePageHandler.exec();
            } else if (action.getType().equals("on page")) {
                onPageHandler.setUser(currentUser);
                onPageHandler.setCurrentPage(currentPage);
                onPageHandler.setDatabase(database);
                onPageHandler.setOutput(output);
                onPageHandler.setAction(action);
                currentUser = onPageHandler.exec();
            }
        }



        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        try {
            objectWriter.writeValue(new File(args[1]), output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
