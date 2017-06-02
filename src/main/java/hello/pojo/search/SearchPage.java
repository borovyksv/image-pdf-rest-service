package hello.pojo.search;

public class SearchPage {

    public Integer pageNum;
    public Integer matches;
    public String text;

    public SearchPage(Integer pageNum, String text) {

        this.pageNum = pageNum;
        this.text = text;
    }

    public SearchPage() {

    }

    public Integer getMatches() {
        return matches;
    }

    public void setMatches(Integer matches) {
        this.matches = matches;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
