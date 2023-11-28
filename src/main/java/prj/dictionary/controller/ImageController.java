package prj.dictionary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import prj.dictionary.service.StorageService;

import java.util.Base64;

//@RestController
//public class ImageController {
//
//    @Autowired
//    StorageService storageService;
//
//    @GetMapping("/image/{fileName}")
//    public byte[] readFile(@PathVariable String fileName) {
//        byte[] fileBytes = storageService.load(fileName);
//        return fileBytes;
//    }
//}

@Controller
public class ImageController {

    @Autowired
    StorageService storageService;

    @GetMapping("/image/{fileName}")
    public ResponseEntity<String> displayImage(@PathVariable String fileName) {
        byte[] fileBytes = storageService.load(fileName);

        // Convert the byte[] to Base64
        String base64Image = Base64.getEncoder().encodeToString(fileBytes);

        // Determine the image format (you may need to adjust this based on your use case)
        String mimeType = "image/png";

        // Combine the data URI
        String dataUri = "data:" + mimeType + ";base64," + base64Image;

        // Set the Content-Type header to tell the browser how to handle the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML); // For displaying the image in a tab, use TEXT_HTML.

        // Return the data URI as a string in the ResponseEntity
        return ResponseEntity.ok().headers(headers).body(dataUri);
    }
}
