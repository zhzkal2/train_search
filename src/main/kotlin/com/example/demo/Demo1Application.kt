package com.example.demo

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(TrainConfig::class)
class Demo1Application(val slackService: SlackServiceImpl) : CommandLineRunner {

    override fun run(vararg args: String?) {
        slackService.showSlackInfo()
        slackService.sendMessage("테스트 메시지")
    }
}

fun main(args: Array<String>) {
    runApplication<Demo1Application>(*args)
}
