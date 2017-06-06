package hello.services;

import hello.pojo.DocumentWithTextPages;
import hello.pojo.Page;
import hello.pojo.search.BookmarksDocument;
import hello.pojo.search.SearchDocument;
import hello.pojo.search.SearchPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class DocumentService {

    public static final int INDENTATION = 80;

    public List<BookmarksDocument> getBookmarksDocuments(List<DocumentWithTextPages> bookmarks) {
        List<BookmarksDocument> resultList = new ArrayList<>();
        for (DocumentWithTextPages doc : bookmarks) {
            BookmarksDocument result = new BookmarksDocument();
            result.setName(doc.getName());
            result.setVendor(doc.getVendor());
            result.setModel(doc.getModel());
            result.setYear(doc.getYear());
            result.setOptions(doc.getOptions());
            result.setBookmarks(doc.getBookmarks());
            resultList.add(result);
        }
        return resultList;
    }

    public List<SearchDocument> getFormattedResults(String keyword, Integer start, Integer end, List<DocumentWithTextPages> search) {
        List<SearchDocument> resultList = new ArrayList<>();
        for (DocumentWithTextPages doc : search) {
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
            resultList.add(result);
        }
        Collections.sort(resultList, new Comparator<SearchDocument>() {
            @Override
            public int compare(SearchDocument o1, SearchDocument o2) {
                return Integer.compare(o2.getMatches(), o1.getMatches());
            }
        });

        return resultList;
    }

    private Integer countMatches(List<SearchPage> newBookmarks, List<SearchPage> newPages) {

        Integer result = 0;
        for (SearchPage page : newBookmarks) {
           result+=page.getMatches();
        }
        for (SearchPage page : newPages) {
            result+=page.getMatches();
        }
        return result;
    }

    public List<SearchPage> filterList(List<Page> pages, String keyword, Integer start, Integer end) {

        List<SearchPage> resultList = new ArrayList<>();
        for (Page page : pages) {
            if (page.getText().toLowerCase().contains(keyword.toLowerCase())) {
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
                resultList.add(result);
            }
        }
        Collections.sort(resultList, new Comparator<SearchPage>() {
            @Override
            public int compare(SearchPage o1, SearchPage o2) {
                return Integer.compare(o2.getMatches(), o1.getMatches());
            }
        });
        if (resultList.size()>end) {
            resultList = resultList.subList(start, (end - start > 0 ? end - start : end));
        }

        return resultList;
    }

}
