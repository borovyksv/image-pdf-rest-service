package hello;

import hello.pojo.DocumentWithTextPages;
import hello.pojo.search.BookmarksDocument;
import hello.pojo.search.SearchDocument;
import hello.repository.DocumentWithTextPagesRepository;
import hello.services.DocumentService;
//import hello.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController("myController")
@RequestMapping("/techdoc/*")
public class MainController {


    /**
     * Search
     */

    @Autowired
    DocumentService documentService;
    @Autowired
    DocumentWithTextPagesRepository documentWithTextPagesRepository;

    @RequestMapping(value = "documents/{vendor}/{model}/{year}/{keyword}", method = RequestMethod.GET)
    public List<SearchDocument> search(@PathVariable("vendor") String vendor,
                         @PathVariable("model") String model,
                         @PathVariable("year") Integer year,
                         @PathVariable("keyword") String keyword,
                           @RequestParam(value = "start", required=false, defaultValue = "0") Integer start,
                           @RequestParam(value = "end", required=false, defaultValue = "30") Integer end){

        List<DocumentWithTextPages> searchQueryResults = documentWithTextPagesRepository.search(vendor, model, year, keyword);

        List<SearchDocument> formattedResults = documentService.getFormattedResults(keyword, start, end, searchQueryResults);

        return formattedResults;

    }


    @RequestMapping(value = "/bookmarks/{vendor}/{model}/{year}", method = RequestMethod.GET)
    public List<BookmarksDocument> bookmarks(@PathVariable("vendor") String vendor,
                                             @PathVariable("model") String model,
                                             @PathVariable("year") Integer year) {
        System.out.println("bookmarks");

        List<DocumentWithTextPages> documents = documentWithTextPagesRepository.findBookmarks(vendor, model, year);

        return documentService.getBookmarksDocuments(documents);

    }






}
