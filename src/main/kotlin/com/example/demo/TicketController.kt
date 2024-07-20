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
    fun checkTicket(
        @RequestParam url: String,
        @RequestParam departure: String,
        @RequestParam arrival: String,
        @RequestParam people: Int,
        @RequestParam month: Int,
        @RequestParam day: Int,
        @RequestParam hour: Int
    ): String {
        return try {
            ticketChecker.checkTicket(url,departure, arrival, people, month, day, hour)

            //깃 이그노어 테스트를 위해서 주석 추가

            "티켓 검사 완료"

        } catch (e: Exception) {
            "티켓 검사 중 오류 발생: ${e.message}"
        }
    }



}