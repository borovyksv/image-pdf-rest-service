package hello;

import hello.pojo.DocumentWithTextPages;
import hello.pojo.search.BookmarksDocument;
import hello.pojo.search.SearchDocument;
import hello.repository.DocumentWithTextPagesRepository;
import hello.services.DocumentService;
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

    /**
     * Methods for Angular images and pdf view
     * */

    @RequestMapping(value = "{id}/png/{page}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable("id") String id, @PathVariable("page") Integer page) throws IOException {
        DocumentWithTextPages doc = documentWithTextPagesRepository.findById(id);
        String folder = doc.getFolder();
        BufferedImage originalImage = ImageIO.read(new File(folder+"\\IMG\\"+page+".png"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( originalImage, "png", baos );
        baos.flush();
        return  baos.toByteArray();
    }
    @RequestMapping(value = "{id}/pdf/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public void getPdf(@PathVariable("id") String id, @PathVariable("page") Integer page, HttpServletResponse response) throws IOException {
        DocumentWithTextPages doc = documentWithTextPagesRepository.findById(id);
        String folder = doc.getFolder();
        Path path = Paths.get(folder+"\\PDF\\"+page+".pdf");
        byte[] byteArray = Files.readAllBytes(path);
        response.setStatus(200);
        response.setContentType("application/pdf");
        response.setContentLength(byteArray.length);
        OutputStream os = response.getOutputStream();
        os.write(byteArray);
        os.flush();
        os.close();
    }





    /**FILE UPLOAD*/

//    @Autowired
//    FileService fileService;

//    @RequestMapping(value = "/documents/store", method = RequestMethod.POST, consumes = "multipart/form-data")
//    public Info handleFileUpload(@RequestPart("file") MultipartFile file, @RequestPart("info") List<Info> info) {
//
//
//        DocumentWithTextPages saved = documentWithTextPagesRepository.save(new DocumentWithTextPages(info));
//        String id = saved.getId();
//        System.out.println("ID "+id);
//
//        if (fileService.store(file, id)){
//            saved.setName(file.getOriginalFilename());
//            documentWithTextPagesRepository.save(saved);
//        }
//        return new Info("message", "File "+file.getOriginalFilename()+" uploaded");
//    }


    /**
     * Methods to work with selectors
     * */
//    @Autowired
//    SelectorRepository selectorRepository;
//
//    @RequestMapping(value = "/selectors", method = RequestMethod.GET)
//    public List<Selector> getSelectors(){
//        return selectorRepository.findAll();
//    }
//
//    @RequestMapping(value = "/selectors", method = RequestMethod.POST)
//    public void addSelector(@RequestBody RawSelector input){
//        System.out.println(input);
//
//        Selector saved = selectorRepository.save(new Selector(input.getTitle(), Arrays.asList(input.getOptions().split(", "))));
//        System.out.println("saved " + saved);
//    }






}
