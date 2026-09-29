package com.URL.Shortener.url.Controller;

import com.URL.Shortener.url.Service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "${FRONTEND_URL:http://localhost:5173}")
public class UrlController {

    @Autowired
    private UrlService urlService;


    @PostMapping("/urlshortener")
    public String shortenerUrl(@RequestBody String longUrl){
        return urlService.shortenerUrl(longUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl) {
        URI destination = URI.create(urlService.redirectToLongUrl(shortUrl));
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, destination.toString())
                .build();
    }
}
