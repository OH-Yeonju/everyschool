package com.everyschool.openaiservice.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "chat-service", url = "https://every-school.com/api")
public interface ChatServiceClient {

    @GetMapping("/chat-service/client/v1/chat-rooms")
    List<Long> searchChatRoomIdByDate(@RequestParam(name = "date") LocalDate date);

}