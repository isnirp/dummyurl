package com.flimbis.service

import com.flimbis.model.Url
import com.flimbis.repository.UrlShortenerRepository
import org.springframework.stereotype.Service
import java.util.Base64

@Service
class UrlShortenerService(private val repository: UrlShortenerRepository) {
    fun shortenUrl(originalUrl: String): Url {
        val encodedUrl = Base64.getUrlEncoder()
                .encodeToString(originalUrl.toByteArray())

        val shortenedUrl = Url()
        shortenedUrl.url = originalUrl
        shortenedUrl.shortPath = encodedUrl.substring(0, 5)

        repository.save(shortenedUrl)

        return shortenedUrl
    }

    fun getOriginalUrl(shortPath: String): String? {
        val url = repository.findByShortPath(shortPath)
        return url?.url
    }
}