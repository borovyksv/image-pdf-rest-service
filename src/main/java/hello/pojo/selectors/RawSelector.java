package hello.pojo.selectors;

import org.springframework.data.annotation.Id;

public class RawSelector {

    @Id
    public String id;
    public String title;
    public String options;

    public RawSelector() {
    }
    public RawSelector(String title, String options) {
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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
