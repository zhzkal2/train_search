package com.example.demo

interface SlackService {

    fun sendMessage(text: String)

    fun sendMessage(channel: String, text: String)
}