package com.everyschool.callservice.api.controller.FCM;


import com.everyschool.callservice.api.ApiResponse;
import com.everyschool.callservice.api.controller.FCM.request.OtherUserFcmRequest;
import com.everyschool.callservice.api.service.FCM.FCMNotificationService;
import com.everyschool.callservice.api.service.FCM.dto.OtherUserFcmDto;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/call-service/v1/calls")
public class FCMNotificationController {

    private final FCMNotificationService fcmNotificationService;

    /**
     * 전화 알림 전송 API
     *
     * @param request 통화 걸 상대방의 정보
     * @return 생성 완료 메세지
     */
    @PostMapping("/calling")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> sendNotificationByToken(@RequestBody OtherUserFcmRequest request) throws FirebaseMessagingException {
        OtherUserFcmDto dto = request.toDto();

        return ApiResponse.created(fcmNotificationService.sendNotificationByToken(dto));
    }
    
    /**
     * TODO 전화를 안받았을때 부재중 저장
     */
}