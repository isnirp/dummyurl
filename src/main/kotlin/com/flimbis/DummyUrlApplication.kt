package com.flimbis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DummyUrlApplication

fun main(args: Array<String>) {
	runApplication<DummyUrlApplication>(*args)
}
