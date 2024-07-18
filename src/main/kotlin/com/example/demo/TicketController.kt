package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TicketController {

    @Autowired
    lateinit var ticketChecker: TicketChecker


    @GetMapping("/check-ticket")
    fun checkTicket(@RequestParam url: String): String {
        return try {
            ticketChecker.checkTicket(url)
            "티켓 검사 완료"
        } catch (e: Exception) {
            "티켓 검사 중 오류 발생: ${e.message}"
        }
    }
}