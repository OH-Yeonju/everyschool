package com.everyschool.schoolservice.domain.schooluser.repository;

import com.everyschool.schoolservice.IntegrationTestSupport;
import com.everyschool.schoolservice.api.service.schooluser.dto.SearchMyClassStudentDto;
import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.school.repository.SchoolRepository;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import com.everyschool.schoolservice.domain.schooluser.SchoolUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

class SchoolUserQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private SchoolUserQueryRepository schoolUserQueryRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SchoolUserRepository schoolUserRepository;

    @DisplayName("학급 id로 학급 학생을 조회한다.")
    @Test
    void findBySchoolClassId() {
        //given
        School school = saveSchool();
        SchoolClass schoolClass = saveSchoolClass(school);
        SchoolUser schoolUser1 = saveSchoolUser(school, schoolClass, 1L, 1);
        SchoolUser schoolUser2 = saveSchoolUser(school, schoolClass, 2L, 2);
        SchoolUser schoolUser3 = saveSchoolUser(school, schoolClass, 3L, 3);

        //when
        List<SearchMyClassStudentDto> contents = schoolUserQueryRepository.findBySchoolClassId(schoolClass.getId());

        //then
        assertThat(contents).hasSize(3)
            .extracting("studentId", "studentNum")
            .containsExactlyInAnyOrder(
                tuple(1L, 1),
                tuple(2L, 2),
                tuple(3L, 3)
            );
    }

    private School saveSchool() {
        School school = School.builder()
            .name("수완고등학교")
            .zipcode("62246")
            .address("광주광역시 광산구 장덕로 155")
            .url("http://suwan.gen.hs.kr")
            .tel("062-961-5746")
            .openDate(LocalDate.of(2009, 3, 1).atStartOfDay())
            .schoolTypeCodeId(7)
            .build();
        return schoolRepository.save(school);
    }

    private SchoolClass saveSchoolClass(School school) {
        SchoolClass schoolClass = SchoolClass.builder()
            .schoolYear(2023)
            .grade(1)
            .classNum(3)
            .school(school)
            .teacherId(1L)
            .build();
        return schoolClassRepository.save(schoolClass);
    }

    private SchoolUser saveSchoolUser(School school, SchoolClass schoolClass, Long userId, int studentNum) {
        SchoolUser schoolUser = SchoolUser.builder()
            .schoolUserCodeId(1)
            .studentNum(studentNum)
            .schoolYear(2023)
            .userId(userId)
            .school(school)
            .schoolClass(schoolClass)
            .build();
        return schoolUserRepository.save(schoolUser);
    }
}