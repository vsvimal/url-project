package com.URL.Shortener.url.Controller;

import com.URL.Shortener.url.Service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UrlController {

    @Autowired
    private UrlService urlService;


    @PostMapping("/urlshortener")
    public String shortenerUrl(@RequestBody String longUrl){
        return urlService.shortenerUrl(longUrl);
    }

    @GetMapping("/{shortUrl}")
    public String redirectToLongUrl(@PathVariable String shortUrl){
        return urlService.redirectToLongUrl(shortUrl);
    }
}
