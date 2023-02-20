package classestogettheinput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Database {
    private ArrayList<User> registeredUsers;
    private ArrayList<Movie> listOfMovies;
    private Map<String, String> accounts = new HashMap<>();

    public Database() {

    }

    private static Database database = null;

    /***
     * Clasa este una singleton asa ca definim metoda getInstance
     * ce va creea o instanta a clasei in cazul in care aceasta nu
     * exista deja sau va intoarce instanta existenta
     *
     * @param registeredUsers utilizatorii deja inregistrati
     * @param listOfMovies filmele disponibile
     * @return intoarcem instanta database
     */

    public static Database getInstance(final ArrayList<User> registeredUsers,
                                       final ArrayList<Movie> listOfMovies) {
        if (database == null) {
            database = new Database();
            database.listOfMovies = listOfMovies;
            database.registeredUsers = registeredUsers;
            for (User user : registeredUsers) {
                database.accounts.put(user.getCredentials().getName(),
                        user.getCredentials().getPassword());
            }
        } else {
            database.setListOfMovies(listOfMovies);
            database.setRegisteredUsers(registeredUsers);
            database.accounts.clear();
            for (User user : registeredUsers) {
                database.accounts.put(user.getCredentials().getName(),
                        user.getCredentials().getPassword());
            }
        }
        return database;
    }

    /***
     * Intoarce utilizatorul care corespunde numelui si parolei
     * stocate in baza de date
     *
     * @param name  numele utilizatorului
     * @param password  parola utilizatorului
     * @return utilizatorul
     */

    public User returnUser(final String name, final String password) {
        for (User user : registeredUsers) {
            if (user.getCredentials().getName().equals(name)
                    && user.getCredentials().getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }



    public Map<String, String> getAccounts() {
        return accounts;
    }

    public void setAccounts(final Map<String, String> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }

    /***
     * Adauga utilizatorii deja inregistrati in baza de date
     * dupa citirea din fisierele json
     *
     * @param registeredUsers lista cu utilizatorii deja inregistrati
     */

    public void setRegisteredUsers(final ArrayList<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
        for (User user : registeredUsers) {
            accounts.put(user.getCredentials().getName(), user.getCredentials().getPassword());
        }
    }



    /***
     * Adauga un utizator dupa ce acesta s-a inregistrat
     * in baza de date
     *
     * @param registeredUser utilizatorul inregistrat
     */

    public void addRegisteredUser(final User registeredUser) {
        this.registeredUsers.add(registeredUser);
        accounts.put(registeredUser.getCredentials().getName(),
                registeredUser.getCredentials().getPassword());
    }

    public ArrayList<Movie> getListOfMovies() {
        return listOfMovies;
    }

    public void setListOfMovies(final ArrayList<Movie> listOfMovies) {
        this.listOfMovies = listOfMovies;
    }
}
