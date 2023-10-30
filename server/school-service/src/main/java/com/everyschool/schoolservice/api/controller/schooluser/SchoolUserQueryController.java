package com.everyschool.schoolservice.api.controller.schooluser;

import com.everyschool.schoolservice.api.ApiResponse;
import com.everyschool.schoolservice.api.controller.schooluser.response.MyClassStudentResponse;
import com.everyschool.schoolservice.api.service.schooluser.SchoolUserQueryService;
import com.everyschool.schoolservice.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/school-service/v1/schools/{schoolId}/classes")
public class SchoolUserQueryController {

    private final SchoolUserQueryService schoolUserQueryService;
    private final TokenUtils tokenUtils;

    @GetMapping("/{schoolYear}")
    public ApiResponse<List<MyClassStudentResponse>> searchMyClassStudents(@PathVariable Long schoolId, @PathVariable Integer schoolYear) {
        log.debug("call SchoolUserQueryController#searchMyClassStudent");

        String userKey = tokenUtils.getUserKey();
        log.debug("userKey={}", userKey);

        List<MyClassStudentResponse> responses = schoolUserQueryService.searchMyClassStudents(userKey, schoolYear);

        return ApiResponse.ok(responses);
    }
}