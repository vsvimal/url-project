package com.URL.Shortener.url.Service;

import com.URL.Shortener.url.Model.Url;
import com.URL.Shortener.url.Repo.UserRepo;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UrlService {

    private static final Duration CACHE_TTL = Duration.ofHours(12);

    private final UserRepo userRepo;
    private final Base62Encoder base62Encoder;
    private final RedisTemplate<String, String> redisTemplate;

    public UrlService(UserRepo userRepo, Base62Encoder base62Encoder,
                      @Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate) {
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
        return shortCode;
    }

    public String redirectToLongUrl(String shortUrl) {
        String cachedLongUrl = redisTemplate.opsForValue().get(shortUrl);
        if (cachedLongUrl != null) {
            return cachedLongUrl;
        }

        Url map = userRepo.findByShorturl(shortUrl);
        String longUrl = map.getLongUrl();
        redisTemplate.opsForValue().set(shortUrl, longUrl, CACHE_TTL);
        return longUrl;
    }
}
