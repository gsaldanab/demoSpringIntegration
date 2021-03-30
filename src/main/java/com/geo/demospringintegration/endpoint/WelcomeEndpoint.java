package com.geo.demospringintegration.endpoint;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.geo.demospringintegration.model.entity.HelloMsg;

@Component
public class WelcomeEndpoint {
	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	public Message<?> get(Message<String> msg) {
		String name = msg.getPayload();
		// log
		log.info("Request with name: " + name);
		// get current time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String currentTime = dtf.format(now);

		String strMsg = "Hello " + name + "! Welcome to spring integration with Spring boot!";

		HelloMsg returnMsg = new HelloMsg(strMsg, currentTime);

		return MessageBuilder.withPayload(returnMsg).copyHeadersIfAbsent(msg.getHeaders())
				.setHeader("http_statusCode", HttpStatus.OK).build();
	}
}
