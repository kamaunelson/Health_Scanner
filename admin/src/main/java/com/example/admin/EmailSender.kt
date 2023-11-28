package com.example.admin

import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailSender {
    fun sendEmail(userEmail: String, otp: Int) {
        //val to = "recipient@example.com"
        val to = userEmail // Use the user's email
        val from = "uzzidaniel002@gmail.com"
        val password = "vlvo dduf twam pnch"
        val host = "smtp.gmail.com"
        val port = "587"

        val properties = Properties()
        properties["mail.smtp.host"] = host
        properties["mail.smtp.port"] = port
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"

        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(from, password)
            }
        })

        val message = MimeMessage(session)
        message.setFrom(InternetAddress(from))
        message.addRecipient(Message.RecipientType.TO, InternetAddress(to))
        message.subject = "Login and OTP Notification ~ HealthScanner"
        message.setText("You have logged in to your account./n Your OTP is: $otp")

        Transport.send(message)
    }
}