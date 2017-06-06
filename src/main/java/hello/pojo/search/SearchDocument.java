package hello.pojo.search;

import java.util.List;

public class SearchDocument {

    public String id;
    public String name;
    public Integer matches;
    public String vendor;
    public List<String> model;
    public List<Integer> year;
    public List<String> options;
    public List<SearchPage> bookmarks;
    public List<SearchPage> pages;
    public SearchDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMatches() {
        return matches;
    }

    public void setMatches(Integer matches) {
        this.matches = matches;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public List<String> getModel() {
        return model;
    }

    public void setModel(List<String> model) {
        this.model = model;
    }

    public List<Integer> getYear() {
        return year;
    }

    public void setYear(List<Integer> year) {
        this.year = year;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
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
}

