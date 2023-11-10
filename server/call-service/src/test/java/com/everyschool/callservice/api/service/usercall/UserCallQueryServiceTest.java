package com.everyschool.callservice.api.service.usercall;

import com.everyschool.callservice.IntegrationTestSupport;
import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.domain.usercall.UserCall;
import com.everyschool.callservice.domain.usercall.repository.UserCallRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;

class UserCallQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserCallQueryService userCallQueryService;

    @Autowired
    private UserCallRepository userCallRepository;

    @MockBean
    private UserServiceClient userServiceClient;

    @DisplayName("내 통화 목록 불러오기")
    @Test
    void searchMyCalls() {

        // given
        UserInfo teacher = UserInfo.builder()
                .userId(1L)
                .userType('T')
                .userName("신성주")
                .schoolClassId(1L)
                .build();

        String userKey = UUID.randomUUID().toString();

        UserCall userCall1 = saveCal(1L, 2L, "T", "신성주", "홍경환", LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(4), "파일명", "파일명", false);
        UserCall userCall2 = saveCal(1L, 2L, "O", "홍경환", "신성주", LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2), "파일명", "파일명", false);
        UserCall userCall3 = saveCal(1L, 3L, "T", "신성주", "이예리", LocalDateTime.now().minusHours(1), LocalDateTime.now().minusMinutes(30), "파일명", "파일명", false);
        UserCall userCall4 = saveCal(4L, 2L, "T", "이지혁", "홍경환", LocalDateTime.now().minusMinutes(20), LocalDateTime.now().minusMinutes(10), "파일명", "파일명", false);
        UserCall userCall5 = saveCal(1L, 5L, "O", "임우택", "신성주", LocalDateTime.now().minusMinutes(5), LocalDateTime.now().minusMinutes(1), "파일명", "파일명", false);

        given(userServiceClient.searchUserInfoByUserKey(userKey))
                .willReturn(teacher);

        // when
        List<UserCallResponse> responses = userCallQueryService.searchMyCalls(userKey);

        // then
        assertThat(responses).hasSize(4);
        assertThat(responses)
                .extracting("senderName", "receiverName")
                .containsExactlyInAnyOrder(
                        tuple("신성주", "홍경환"),
                        tuple("홍경환", "신성주"),
                        tuple("신성주", "이예리"),
                        tuple("임우택", "신성주")
                );
    }

    private UserCall saveCal(Long teacherId, Long otherUserId, String sender, String senderName, String receiverName,
                             LocalDateTime startDateTime, LocalDateTime endDateTime, String uploadFileName, String storeFileName,
                             Boolean isBad) {
        UserCall userCall = UserCall.builder()
                .teacherId(teacherId)
                .otherUserId(otherUserId)
                .sender(sender)
                .senderName(senderName)
                .receiverName(receiverName)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .uploadFileName(uploadFileName)
                .storeFileName(storeFileName)
                .isBad(isBad)
                .build();
        return userCallRepository.save(userCall);
    }
}