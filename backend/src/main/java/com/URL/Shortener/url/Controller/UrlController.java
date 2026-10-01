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

    @GetMapping("/test")
    public String test() {
        System.out.println("🔥 TEST ENDPOINT HIT");
        return "GET endpoint is working";
    }

    @PostMapping("/urlshortener")
    public String shortenerUrl(@RequestBody String longUrl){
        return urlService.shortenerUrl(longUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl) {
        System.out.println("🔥 SHORT CODE RECEIVED: [" + shortUrl + "]");
        String longUrl = urlService.redirectToLongUrl(shortUrl);
        System.out.println("🔥 LONG URL FOUND: [" + longUrl + "]");
        URI destination = URI.create(longUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, destination.toString())
                .build();
    }
}
