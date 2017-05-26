package hello.pojo.selectors;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Selector {

    @Id
    public String id;

    public String title;
    public List<String> options;

    public Selector() {
    }

    public Selector(String title, List<String> options) {
        this.title = title;
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Selector{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", options=" + options +
                '}';
    }
}
