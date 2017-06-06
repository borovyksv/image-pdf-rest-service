package hello.services;

import hello.pojo.DocumentWithTextPages;
import hello.pojo.Page;
import hello.pojo.search.BookmarksDocument;
import hello.pojo.search.SearchDocument;
import hello.pojo.search.SearchPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    public static final int INDENTATION = 80;
    public static final int DEFAULT_LIMIT = 30;

    public List<BookmarksDocument> getBookmarksDocuments(List<DocumentWithTextPages> bookmarks) {
        return bookmarks.parallelStream().map(doc -> {
            BookmarksDocument result = new BookmarksDocument();
            result.setName(doc.getName());
            result.setVendor(doc.getVendor());
            result.setModel(doc.getModel());
            result.setYear(doc.getYear());
            result.setOptions(doc.getOptions());
            result.setBookmarks(doc.getBookmarks());
            return result;
        }).collect(Collectors.toList());
    }

    public List<SearchDocument> getFormattedResults(String keyword, Integer start, Integer end, List<DocumentWithTextPages> search) {

        return search.parallelStream().map(doc -> {
            SearchDocument result = new SearchDocument();
            result.setFolder(doc.getFolder());
            result.setName(doc.getName());
            result.setVendor(doc.getVendor());
            result.setModel(doc.getModel());
            result.setYear(doc.getYear());
            result.setOptions(doc.getOptions());


            List<Page> bookmarks = doc.getBookmarks();
            List<SearchPage> newBookmarks = filterList(bookmarks, keyword, start, end);
            result.setBookmarks(newBookmarks);

            List<Page> pages = doc.getPages();
            List<SearchPage> newPages = filterList(pages, keyword, start, end);
            result.setPages(newPages);

            result.setMatches(countMatches(newBookmarks, newPages));

            return result;
        }).sorted((d1, d2) -> Integer.compare(d2.getMatches(), d1.getMatches())).collect(Collectors.toList());
    }

    public Integer countMatches(List<SearchPage> newBookmarks, List<SearchPage> newPages) {
        final Integer[] result = {0};

        newBookmarks.forEach(page -> result[0] +=page.getMatches());
        newPages.forEach(page -> result[0] +=page.getMatches());

        return result[0];
    }

    public List<SearchPage> filterList(List<Page> pages, String keyword, Integer start, Integer end) {
        return pages.parallelStream()
                .filter(page -> page.getText().toLowerCase().contains(keyword.toLowerCase()))
                .map(page -> {

                    String text = page.getText().toLowerCase();
                    String lowerKeyword = keyword.toLowerCase();

                    int index = text.indexOf(lowerKeyword);
                    int startPos = (index - INDENTATION) < 0 ? 0 : (index - INDENTATION);
                    int endPos = (index + keyword.length() + INDENTATION) > text.length() ? text.length() : (index + keyword.length() + INDENTATION);
                    String substring = page.getText().substring(startPos, endPos);

                    int counter = 1;
                    index = index + keyword.length();
                    int length = text.length();
                    while (index < length) {
                        index = text.indexOf(lowerKeyword, index+keyword.length());
                        if (index==-1){
                            break;
                        }
                        counter++;
                    }

                    SearchPage result = new SearchPage();
                    result.setPageNum(page.getPageNum());
                    result.setMatches(counter);
                    result.setText(substring);

                    return result;})
                .sorted((p1, p2) -> Integer.compare(p2.getMatches(), p1.getMatches()))
                .skip(start)
                .limit(end-start>0?end-start: DEFAULT_LIMIT)
                .collect(Collectors.toList());
    }

}
