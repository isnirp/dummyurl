package com.flimbis.controller

import com.flimbis.model.UrlDto
import com.flimbis.service.UrlShortenerService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI

const val BASE_URL = "http://localhost:8081/api/"

@RestController
@RequestMapping("/api")
class UrlShortenerController(private val service: UrlShortenerService) {

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun createShortUrl(@RequestBody urlDto: UrlDto): UrlDto {
        val url = service.shortenUrl(urlDto.url)
        return UrlDto(BASE_URL + url.shortPath)
    }

    @GetMapping("/decode/{shortPath}")
    fun retrieveOriginalUrl(@PathVariable shortPath: String): UrlDto {
        val url = service.getOriginalUrl(shortPath)
        return UrlDto(url!!)
    }

    @GetMapping("/{shortPath}")
    fun redirectUrl(@PathVariable shortPath: String): ResponseEntity<HttpStatus> {
        val url = service.getOriginalUrl(shortPath)
        return ResponseEntity
            .status(HttpStatus.MOVED_PERMANENTLY)
            .location(URI.create(url!!))
            .header(HttpHeaders.CONNECTION, "close")
            .build()
    }
}