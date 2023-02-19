package com.flimbis.repository

import com.flimbis.model.Url
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlShortenerRepository: JpaRepository<Url, Long> {
}