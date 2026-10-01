package com.URL.Shortener.url.Service;

import com.URL.Shortener.url.Model.Url;
import com.URL.Shortener.url.Repo.UserRepo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Service
public class UrlService {

    private final UserRepo userRepo;
    private final Base62Encoder base62Encoder;
    private final RedisTemplate<String, String> redisTemplate;

    private static final Duration CACHE_TTL = Duration.ofHours(12);

    public UrlService(
            UserRepo userRepo,
            Base62Encoder base62Encoder,
            RedisTemplate<String, String> redisTemplate) {

        this.userRepo = userRepo;
        this.base62Encoder = base62Encoder;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public String shortenerUrl(String longUrl) {
        Url map = new Url();
        map.setLongUrl(longUrl);
        Url saved = userRepo.save(map);
        Long generatedId = saved.getId();
        String shortCode = base62Encoder.encode(generatedId);
        saved.setShorturl(shortCode);
        userRepo.save(saved);
        redisTemplate.opsForValue().set(
                shortCode,
                longUrl,
                CACHE_TTL
        );

        return shortCode;
    }

    public String redirectToLongUrl(String shortUrl) {
        String cachedLongUrl = redisTemplate
                .opsForValue()
                .get(shortUrl);

        if (cachedLongUrl != null) {
            return cachedLongUrl;
        }
        Url map = userRepo.findByShorturl(shortUrl);
        if (map == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Short URL not found"
            );
        }
        String longUrl = map.getLongUrl();
        redisTemplate.opsForValue().set(
                shortUrl,
                longUrl,
                CACHE_TTL
        );
        return longUrl;
    }
}

//public String redirectToLongUrl(String shortUrl) {
//    String cachedLongUrl = redisTemplate.opsForValue().get(shortUrl);
//    if (cachedLongUrl != null) {
//        return cachedLongUrl;
//    }
//
//    Url map = userRepo.findByShorturl(shortUrl);
//    if (map == null) {
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found");
//    }
//    String longUrl = map.getLongUrl();
//    redisTemplate.opsForValue().set(shortUrl, longUrl, CACHE_TTL);
//    return longUrl;
//}
