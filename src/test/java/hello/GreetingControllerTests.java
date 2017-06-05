/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.pojo.DocumentWithTextPages;
import hello.pojo.Page;
import hello.pojo.search.SearchDocument;
import hello.pojo.search.SearchPage;
import hello.repository.DocumentWithTextPagesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GreetingControllerTests {
    @Autowired
    DocumentWithTextPagesRepository documentWithTextPagesRepository;

    @Test
    public void search() throws JsonProcessingException {
        String vehicle = "Toyota";
        String keyword = "water pump";

        Instant start = Instant.now();

        List<DocumentWithTextPages> search = documentWithTextPagesRepository.search(vehicle.toUpperCase(), keyword);

        Instant end = Instant.now();
        System.out.println("QUERY DOCUMENTS TIME: "+ Duration.between(start, end));

        List<SearchDocument> result = new ArrayList<>();


        start = Instant.now();
        for (DocumentWithTextPages documentWithTextPages : search) {
            SearchDocument document = new SearchDocument();
            document.setName(documentWithTextPages.getName());
            document.setOptions(documentWithTextPages.getOptions());

            List<Page> bookmarks = documentWithTextPages.getBookmarks();
            List<SearchPage> newBookmarks = filterList(bookmarks, keyword);
            document.setBookmarks(newBookmarks);

            List<Page> pages = documentWithTextPages.getPages();
            List<SearchPage> newPages = filterList(pages, keyword);
            document.setPages(newPages);

            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(document));

            result.add(document);
        }
        end = Instant.now();
        System.out.println("FILTERING DOCUMENTS TIME: "+ Duration.between(start, end));
    }

    private List<SearchPage> filterList(List<Page> pages, String keyword) {
        return pages.stream().filter(page -> page.getText().toLowerCase().contains(keyword.toLowerCase())).map(page -> {

            String text = page.getText().toLowerCase();
            String lowerKeyword = keyword.toLowerCase();

            int index = text.indexOf(lowerKeyword);
            int startPos = (index - 20) < 0 ? 0 : (index - 20);
            int endPos = (index + keyword.length() + 20) > text.length() ? text.length() : (index + keyword.length() + 20);
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

            return result;
            }).sorted((p1, p2) -> Integer.compare(p2.getMatches(), p1.getMatches())).collect(Collectors.toList());
    }


    @Test
    public void savana(){
        DocumentWithTextPages savana = documentWithTextPagesRepository.getSavana();
        for (int i = 500; i < 1500; i++) {
            DocumentWithTextPages doc = new DocumentWithTextPages();
            doc.setName(savana.getName()+" "+i);
            doc.setOptions(savana.getOptions());
            doc.setBookmarks(savana.getBookmarks());
            doc.setPages(savana.getPages());
            System.out.println(doc);
            System.out.println("count "+documentWithTextPagesRepository.count());
            documentWithTextPagesRepository.save(doc);
        }
        System.out.println(savana);
    }


    @Test
    public void test(){
        for (int i = 30; i < 50; i++) {
            List<DocumentWithTextPages> all = documentWithTextPagesRepository.findAll();
            for (DocumentWithTextPages documentWithTextPages : all) {
                DocumentWithTextPages doc = new DocumentWithTextPages();

                doc.setName(documentWithTextPages.getName() + " " + i);
                doc.setPages(documentWithTextPages.getPages());
                doc.setOptions(documentWithTextPages.getOptions());
                doc.setBookmarks(documentWithTextPages.getBookmarks());

                doc.getPages().add(new Page(15000 + i, " qwerty" + i));

                System.out.println(doc);
                documentWithTextPagesRepository.save(doc);
            }
        }
//        for (DocumentWithTextPages documentWithTextPages : all) {
//            for (int i = 0; i < 300; i++) {
//                DocumentWithTextPages document = new DocumentWithTextPages();
//                document.setName(documentWithTextPages.getName() + " " + i);
//                document.setBookmarks(documentWithTextPages.getDocsByVehicle());
//                document.setOptions(documentWithTextPages.getOptions());
//
//                List<Page> pages = documentWithTextPages.getPages();
//                Page page = pages.get(10);
//                page.setText(page.getText()+" qwerty"+i+" "+page.getText());
//                pages.set(10, page);
//                List<Page> pages1 = pages.subList(0, 5000);
//
//                document.setPages(pages1);
//                System.out.println(document);
//
//                documentWithTextPagesRepository.save(document);
//
//            }
//        }
    }

}
