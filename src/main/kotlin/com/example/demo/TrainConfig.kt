package com.example.demo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
//삭제예정
@Configuration
@ConfigurationProperties(prefix = "train.member")
class TrainConfig {

    lateinit var number: String
    lateinit var password: String

}