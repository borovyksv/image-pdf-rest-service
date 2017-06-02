package hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import hello.pojo.DocumentWithTextPages;
import hello.pojo.Page;
import hello.pojo.selectors.RawSelector;
import hello.pojo.selectors.Selector;
import hello.repository.DocumentWithTextPagesRepository;
import hello.repository.SelectorRepository;
import hello.services.FileService;
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
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {




    /**Search*/
    @RequestMapping(value = "/documents/{vehicle}/{keyword}", method = RequestMethod.GET)
    public List<DocumentWithTextPages> search(@PathVariable( "vehicle" ) String vehicle, @PathVariable( "keyword" ) String keyword) throws JsonProcessingException {
        Instant start = Instant.now();

        List<DocumentWithTextPages> search = documentWithTextPagesRepository.search(vehicle.toUpperCase(), keyword);

        Instant end = Instant.now();
        System.out.println("QUERY DOCUMENTS TIME: "+ Duration.between(start, end));

        List<DocumentWithTextPages> result = new ArrayList<>();


        start = Instant.now();
        for (DocumentWithTextPages documentWithTextPages : search) {
            DocumentWithTextPages document = new DocumentWithTextPages();
            document.setName(documentWithTextPages.getName());
            document.setOptions(documentWithTextPages.getOptions());

            List<Page> bookmarks = documentWithTextPages.getBookmarks();
            List<Page> newBookmarks = filterList(bookmarks, keyword);
            document.setBookmarks(newBookmarks);

            List<Page> pages = documentWithTextPages.getPages();
            List<Page> newPages = filterList(pages, keyword);
            document.setPages(newPages);

//            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(document));

            result.add(document);
        }
        end = Instant.now();
        System.out.println("FILTERING DOCUMENTS TIME: "+ Duration.between(start, end));

        return result;
    }

    private List<Page> filterList(List<Page> pages, String keyword) {
        return pages.parallelStream().filter(page -> page.getText().toLowerCase().contains(keyword.toLowerCase())).map(page -> {
            if (page.getId()==307){
                System.out.println("Hello");
            }
            String text = page.getText().toLowerCase();
            int i = text.indexOf(keyword.toLowerCase());
            int startPos = (i - 20) < 0 ? 0 : (i - 20);
            int endPos = (i +keyword.length()+ 20) > text.length() ? text.length() : (i + keyword.length()+20);
            String substring = page.getText().substring(startPos, endPos);
            page.setText(substring);
            return page;
        }).collect(Collectors.toList());
    }






    /**FILE UPLOAD*/
    @Autowired
    DocumentWithTextPagesRepository documentWithTextPagesRepository;
    @Autowired
    FileService fileService;

//    @RequestMapping(value = "/documents/store", method = RequestMethod.POST, consumes = "multipart/form-data")
//    public Info handleFileUpload(@RequestPart("file") MultipartFile file, @RequestPart("info") List<Info> info) {
//        System.out.println(file.getName()+"\n\n\n\n\n\n");
//        System.out.println("INFO to string "+ info +"\n\n\n\n\n\n");
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
    @Autowired
    SelectorRepository selectorRepository;

    @RequestMapping(value = "/selectors", method = RequestMethod.GET)
    public List<Selector> getSelectors(){
        return selectorRepository.findAll();
    }

    @RequestMapping(value = "/selectors", method = RequestMethod.POST)
    public void addSelector(@RequestBody RawSelector input){
        System.out.println(input);

        Selector saved = selectorRepository.save(new Selector(input.getTitle(), Arrays.asList(input.getOptions().split(", "))));
        System.out.println("saved " + saved);
    }





    /**
     * Next two methods for Angular images and pdf view
     * **/

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable("id") Integer id) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File("D:\\pdf\\copy\\2k14acadia\\IMG\\"+id+".png"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( originalImage, "png", baos );
        baos.flush();
        return  baos.toByteArray();
    }
    @RequestMapping(value = "/pdf/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public void getPdf(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        Path path = Paths.get("D:\\pdf\\copy\\2k14acadia\\PDF\\"+id+".pdf");
        byte[] byteArray = Files.readAllBytes(path);
        response.setStatus(200);
        response.setContentType("application/pdf");
        response.setContentLength(byteArray.length);
        OutputStream os = response.getOutputStream();
        os.write(byteArray);
        os.flush();
        os.close();
    }
}
