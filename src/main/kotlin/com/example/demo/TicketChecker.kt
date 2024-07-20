package com.example.demo

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.time.Duration


@Component
class TicketChecker(private val trainConfig: TrainConfig) {

    lateinit var driver: WebDriver

    @PostConstruct
    fun setup() {
        // WebDriver 초기 설정 (ChromeDriver 경로를 환경 변수로 설정)
        System.setProperty("webdriver.chrome.driver", "C:\\spring\\tier\\driver\\chromedriver.exe")
        driver = ChromeDriver()
    }
    /*
    * update 2024.07.21
    * url ktx주소 추후에 srt로 구현이 가능할 수 있도록 url은 string으로 받음
    * departure : 출발지 = 용산
    * arrival : 도착지  = 천안아산
    * people :  인원수 = 2
    * month :  개월 = 7
    * day : 요일 = 15
    * hour : 시간 = 7
    */
    fun checkTicket(url: String ,departure: String, arrival: String, people: Int, month: Int, day: Int, hour: Int) {
        //웹페이지 열기
        driver.get(url)


        // 체크박스 찾기
        val checkbox: WebElement = WebDriverWait(driver, Duration.ofSeconds(10)).until(
            ExpectedConditions.visibilityOfElementLocated(By.id("adjcCheckYn"))
        )

        // 체크박스가 체크되어 있는 경우 클릭하여 체크 해제
        if (checkbox.isSelected) {
            checkbox.click()
        }


        // 특정 입력 필드를 찾아 "용산" 입력
        val inputField1 = driver.findElement(By.id("start"))
        inputField1.clear() // 기존 내용을 지우기 위해 필요할 수 있습니다.
        inputField1.sendKeys("용산")

        // 특정 입력 필드를 찾아 "천안아산" 입력
        val inputField2 = driver.findElement(By.id("get"))
        inputField2.clear() // 기존 내용을 지우기 위해 필요할 수 있습니다.
        inputField2.sendKeys("천안아산")

        // select 요소를 찾고 값을 "07"로 설정
        val selectElement_month = driver.findElement(By.id("s_month"))
        val select_month = Select(selectElement_month)
        select_month.selectByValue("07") // 값을 변경하려는 옵션의 value 속성을 입력합니다.

        // select 요소를 찾고 값을 "22"로 설정
        val selectElement_day = driver.findElement(By.id("s_day"))
        val select_day = Select(selectElement_day)
        select_day.selectByValue("22") // 값을 변경하려는 옵션의 value 속성을 입력합니다.


        // select 요소를 찾고 값을 "22"로 설정
        val selectElement_hour = driver.findElement(By.id("s_hour"))
        val select_hour = Select(selectElement_hour)
        select_hour.selectByValue("12") // 값을 변경하려는 옵션의 value 속성을 입력합니다.


        // 버튼 클릭 (JavaScript 함수 호출)
        val buttonElement = WebDriverWait(driver, Duration.ofSeconds(10)).until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("p.btn_inq a"))
        )
        buttonElement.click()

        // 예매 가능 열을 찾기
        WebDriverWait(driver, Duration.ofSeconds(10)).until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tr"))
        )
        val rows: List<WebElement> = driver.findElements(By.cssSelector("tr"))

        val availableTrains = mutableListOf<TrainInfo>()

        // 예매 가능 열을 찾아 availableTrains에 add
        for (row in rows) {
            try {
                val reservationButton = row.findElements(By.cssSelector("img[alt='예약하기']"))
                if (reservationButton != null) {
                    val trainNumber = row.findElement(By.cssSelector("td:nth-child(2) span")).text.trim()
                    val departureStation = row.findElement(By.cssSelector("td:nth-child(3)")).text.split("\n")[0].trim()
                    val departureTime = row.findElement(By.cssSelector("td:nth-child(3)")).text.split("\n")[1].trim()
                    val arrivalStation = row.findElement(By.cssSelector("td:nth-child(4)")).text.split("\n")[0].trim()
                    val arrivalTime = row.findElement(By.cssSelector("td:nth-child(4)")).text.split("\n")[1].trim()
                    val price = row.findElement(By.cssSelector("td.guide365 div.shwSchMlg1")).text.split("\n")[0].trim()

                    availableTrains.add(
                        TrainInfo(
                            trainNumber = trainNumber,
                            departureStation = departureStation,
                            departureTime = departureTime,
                            arrivalStation = arrivalStation,
                            arrivalTime = arrivalTime,
                            price = price
                        )
                    )
                }
            } catch (e: Exception) {

            }
        }

        //테스트 출력코드
        println("-".repeat(68))
        for (train in availableTrains) {
            println(
                String.format(
                    "%-12s %-12s %-8s %-12s %-8s %-8s",
                    train.trainNumber,
                    train.departureStation,
                    train.departureTime,
                    train.arrivalStation,
                    train.arrivalTime,
                    train.price
                )
            )
        }

    }


    @PreDestroy
    fun complete() {
        driver.quit()
    }
}