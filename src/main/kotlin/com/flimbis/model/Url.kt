package com.flimbis.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Url(@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long, val url: String, val encodedUrl: String)

data class UrlDto(val url: String)
