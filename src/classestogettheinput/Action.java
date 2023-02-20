package classestogettheinput;

public final class Action {
    private String type;
    private String page;
    private String feature;
    private Credentials credentials;
    private String startsWith;
    private int count;
    private String movie;
    private int rate;
    private Filter filters;
    private String objectType;


    public Action() {

    }

    private Action(final String type, final String page, final String feature,
                   final Credentials credentials, final String startsWith, final int count,
                   final String movie, final int rate, final Filter filters,
                   final String objectType) {
        this.type = type;
        this.page = page;
        this.feature = feature;
        this.credentials = credentials;
        this.startsWith = startsWith;
        this.count = count;
        this.movie = movie;
        this.rate = rate;
        this.filters = filters;
        this.objectType = objectType;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(final String objectType) {
        this.objectType = objectType;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(final int rate) {
        this.rate = rate;
    }

    public Filter getFilters() {
        return filters;
    }

    public void setFilters(final Filter filters) {
        this.filters = filters;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }
}
