package com.URL.Shortener.url.Repo;

import com.URL.Shortener.url.Model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Url, Long> {

    Url findByShorturl(String shorturl);
}