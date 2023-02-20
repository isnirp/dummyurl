package com.flimbis.controller

import com.flimbis.model.Url
import com.flimbis.service.UrlShortenerService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class UrlShortenerControllerTest(@Autowired val mockmvc: MockMvc) {
    @MockkBean
    lateinit var service: UrlShortenerService

    @Test
    fun `given a url return a short url`() {
        val url = Url()
        url.url = "https://google.com"
        url.shortPath = "ahoucc"

        every { service.shortenUrl(any()) } returns url

        mockmvc.perform(MockMvcRequestBuilders.post("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"url":"https://google.com"}"""))
                .andExpect(status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value(BASE_URL + url.shortPath))
    }

    @Test
    fun `given a shortPath return original url`() {
        val url = "https://google.com"
        every { service.getOriginalUrl(any()) } returns url

        mockmvc.perform(MockMvcRequestBuilders.get("/api/decode/ahuocc"))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.url", Matchers.`is`(url)))
    }
}