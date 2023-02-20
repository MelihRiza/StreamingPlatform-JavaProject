package pages;


public final class CurrentPage {
    private String currentPage;

    private static CurrentPage currentPageInstance = null;

    private CurrentPage() {

    }

    /***
     * Clasa este una singleton asa ca definim metoda getInstance
     * ce va creea o instanta a clasei in cazul in care aceasta nu
     * exista deja sau va intoarce instanta existenta
     *
     * @return intoarce instanta CurrentPage
     */

    public static CurrentPage getInstance() {
        if (currentPageInstance == null) {
            currentPageInstance = new CurrentPage();
        }
        return currentPageInstance;
    }


    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final String currentPage) {
        this.currentPage = currentPage;
    }

}
