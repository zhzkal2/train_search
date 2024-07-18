package com.example.demo

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.stereotype.Component


@Component
class TicketChecker (private val trainConfig: TrainConfig) {

    lateinit var driver: WebDriver

    @PostConstruct
    fun setup() {
        // WebDriver 초기 설정 (ChromeDriver 경로를 환경 변수로 설정)
        System.setProperty("webdriver.chrome.driver", "C:\\spring\\tier\\driver\\chromedriver.exe")
        driver = ChromeDriver()
    }

    fun checkTicket(url:String){
        //웹페이지 열기
        driver.get(url)


        // 특정 입력 필드를 찾아 "용산" 입력
        val inputField1 = driver.findElement(By.id("start"))
        inputField1.clear() // 기존 내용을 지우기 위해 필요할 수 있습니다.
        inputField1.sendKeys("용산")

        // 특정 입력 필드를 찾아 "천안아산" 입력
        val inputField2 = driver.findElement(By.id("get"))
        inputField2.clear() // 기존 내용을 지우기 위해 필요할 수 있습니다.
        inputField2.sendKeys("천안아산")

    }

    @PreDestroy
    fun complete() {
        driver.quit()
    }

}