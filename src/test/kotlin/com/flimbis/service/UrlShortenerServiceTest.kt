package com.flimbis.service

import com.flimbis.exception.NotFoundException
import com.flimbis.model.Url
import com.flimbis.repository.UrlShortenerRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.Base64

class UrlShortenerServiceTest {
    val repository: UrlShortenerRepository = mockk()
    val service: UrlShortenerService = UrlShortenerService(repository)

    @Test
    fun `create short url`() {
        // given
        val someUrl = "https://google.com"

        val encodedUrl = Base64.getUrlEncoder()
                .encodeToString(someUrl.toByteArray())
        val url = Url()
        url.url = someUrl
        url.shortPath = encodedUrl.substring(0, 5)

        every { repository.save(any()) } returns url

        // when
        val result = service.shortenUrl(someUrl)

        assertThat(encodedUrl.substring(0, 5)).isEqualTo(result!!.shortPath);
    }

    @Test
    fun `return long url`() {
        val url = Url()
        url.url = "https://google.com"
        url.shortPath = "ahuocc"

        every { repository.findByShortPath("ahuocc") } returns url

        val result = service.getOriginalUrl("ahuocc")
        assertThat(result).isEqualTo(url.url)
    }

    @Test
    fun `throw not found exception`() {
        every { repository.findByShortPath(any()) } returns null

        // 1
        /*assertThatThrownBy { service.getOriginalUrl("ahuocc") }
                .isInstanceOf(NotFoundException::class.java)
                .hasMessage("Url not found")*/
        // 2
        assertThatExceptionOfType(NotFoundException::class.java)
                .isThrownBy { service.getOriginalUrl("ahuocc") }
    }
}