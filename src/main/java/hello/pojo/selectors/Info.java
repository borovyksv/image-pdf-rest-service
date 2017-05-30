package hello.pojo.selectors;

public class Info {
    public String title;
    public String options;

    public Info() {
    }

    public Info(String title, String options) {
        this.title = title;
        this.options = options;
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

    @Override
    public String toString() {
        return "Info{" +
                "title='" + title + '\'' +
                ", options='" + options + '\'' +
                '}';
    }
}
