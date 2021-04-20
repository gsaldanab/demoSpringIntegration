package com.geo.demospringintegration.endpoint;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.geo.demospringintegration.model.entity.HelloMsg;
import com.geo.demospringintegration.model.entity.ResponseMsg;

@Component
public class WelcomeEndpoint {
	private Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private static final int STATUS_OK = 0;
	private static final int STATUS_ERROR = 1;

	public Message<?> get(Message<String> msg) {
		String option = msg.getPayload();
		log.info("Request with option: " + option);

		return MessageBuilder.withPayload(getResponse(option)).copyHeadersIfAbsent(msg.getHeaders())
				.setHeader("http_statusCode", HttpStatus.OK).build();
	}

	private ResponseMsg getResponse(String option) {
		ResponseMsg response = new ResponseMsg();
		HelloMsg returnMsg = new HelloMsg();
		String messageOK = "Option %s executed succesfully";
		String messageError = "Option %s failed";

		switch (option) {
		case "A":
		case "B":
		case "C":
			returnMsg = new HelloMsg(String.format(messageOK, option), getCurrentTime());
			response.setResponseStatus(STATUS_OK);
			response.setResponseBody(returnMsg);
			return response;
		case "D":
		case "E":
			returnMsg = new HelloMsg(String.format(messageError, option), getCurrentTime());
			response.setResponseStatus(STATUS_ERROR);
			response.setResponseBody(returnMsg);
			return response;
		default:
			response.setResponseStatus(STATUS_ERROR);
			return response;
		}
	}

	private String getCurrentTime() {
		// get current time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
}
