package hello;

import hello.pojo.DocumentWithTextPages;
import hello.pojo.selectors.Info;
import hello.pojo.selectors.RawSelector;
import hello.pojo.selectors.Selector;
import hello.repository.DocumentWithTextPagesRepository;
import hello.repository.SelectorRepository;
import hello.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
import java.util.Arrays;
import java.util.List;

@RestController
public class MainController {


    /**FILE UPLOAD*/
    @Autowired
    DocumentWithTextPagesRepository documentWithTextPagesRepository;
    @Autowired
    FileService fileService;
    @RequestMapping(value = "/documents/store", method = RequestMethod.POST, consumes = "multipart/form-data")
    public Info handleFileUpload(@RequestPart("file") MultipartFile file, @RequestPart("info") List<Info> info) {
        System.out.println(file.getName()+"\n\n\n\n\n\n");
        System.out.println("INFO to string "+ info +"\n\n\n\n\n\n");
        if (fileService.store(file)){

            DocumentWithTextPages document = new DocumentWithTextPages(file.getOriginalFilename(), info);
            documentWithTextPagesRepository.save(document);
        }
        return new Info("message", "File "+file.getOriginalFilename()+" uploaded");
    }


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
