package com.flimbis.controller

import com.flimbis.model.UrlDto
import com.flimbis.service.UrlShortenerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

const val BASE_URL = "http://localhost:8081/"

@RestController
@RequestMapping("/api")
class UrlShortenerController(private val service: UrlShortenerService) {

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun createShortUrl(@RequestBody urlDto: UrlDto): UrlDto {
        val url = service.shortenUrl(urlDto.url)

        return UrlDto(BASE_URL + url!!.shortPath)
    }
}