package hello.pojo.search;

import hello.pojo.selectors.Info;

import java.util.List;

public class SearchDocument {

    public String name;
    public Integer matches;
    public List<Info> options;
    public List<SearchPage> bookmarks;
    public List<SearchPage> pages;
    public SearchDocument() {

    }

    public Integer getMatches() {
        return matches;
    }

    public void setMatches(Integer matches) {
        this.matches = matches;
    }

    public List<SearchPage> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<SearchPage> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public List<SearchPage> getPages() {
        return pages;
    }

    public void setPages(List<SearchPage> pages) {
        this.pages = pages;
    }

    public List<Info> getOptions() {
        return options;
    }

    public void setOptions(List<Info> options) {
        this.options = options;
    }


    @Override
    public String toString() {
        return "Document{" +
                "name= '" + name + '\'' +
                ", pages: " + pages.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchDocument document = (SearchDocument) o;

        if (!name.equals(document.name)) return false;
        return pages.equals(document.pages);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + pages.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

