package classestogettheinput;

public final class Filter {
    private Sort sort;
    private Contain contains;

    public Filter() {

    }

    private Filter(final Sort sort, final Contain contains) {
        this.contains = contains;
        this.sort = sort;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(final Sort sort) {
        this.sort = sort;
    }

    public Contain getContains() {
        return contains;
    }

    public void setContains(final Contain contains) {
        this.contains = contains;
    }
}
