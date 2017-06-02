package hello.pojo;

import hello.pojo.selectors.Info;
import org.springframework.data.annotation.Id;

import java.util.List;

public class DocumentWithTextPages {

    @Id
    public String id;

    public String name;
    public List<Page> bookmarks;
    public List<Page> pages;
    public List<Info> options;


    public List<Info> getOptions() {
        return options;
    }

    public void setOptions(List<Info> options) {
        this.options = options;
    }

    public DocumentWithTextPages(List<Page> pages, String name) {
        this.pages = pages;
        this.name = name;
    }

    public DocumentWithTextPages() {

    }

    public List<Page> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Page> bookmarks) {
        this.bookmarks = bookmarks;
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

        DocumentWithTextPages document = (DocumentWithTextPages) o;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }




//    @Id
//    public String id;
//
//    public String name;
//    public List<Info> options;
//    public List<Page> pages;
//
//    public DocumentWithTextPages(List<Info> options) {
//        this.options = options;
//    }
//
//    public DocumentWithTextPages(String name, List<Info> options) {
//        this.name = name;
//        this.options = options;
//        this.pages = pages;
//    }
//
//    public DocumentWithTextPages(List<Page> pages, String name) {
//        this.pages = pages;
//        this.name = name;
//    }
//
//    public DocumentWithTextPages() {
//
//    }
//
//    public List<Info> getOptions() {
//        return options;
//    }
//
//    public void setOptions(List<Info> options) {
//        this.options = options;
//    }
//
//    @Override
//    public String toString() {
//        return "Document{" +
//                "name= '" + name + '\'' +
//                ", pages: " + pages.size() +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        DocumentWithTextPages document = (DocumentWithTextPages) o;
//
//        if (!name.equals(document.name)) return false;
//        return pages.equals(document.pages);
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = name.hashCode();
//        result = 31 * result + pages.hashCode();
//        return result;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public List<Page> getPages() {
//        return pages;
//    }
//
//    public void setPages(List<Page> pages) {
//        this.pages = pages;
//    }


}

