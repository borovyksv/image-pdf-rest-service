package hello.pojo.search;

import hello.pojo.Page;

import java.util.List;

public class BookmarksDocument {

    public String name;
    public String vendor;
    public List<String> model;
    public List<Integer> year;
    public List<String> options;
    public List<Page> bookmarks;

    public BookmarksDocument() {

    }

    public BookmarksDocument(List<String> options) {
        this.options = options;
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

    public List<Page> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Page> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

