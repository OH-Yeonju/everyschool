package com.everyschool.schoolservice.api.service.school;

import com.everyschool.schoolservice.IntegrationTestSupport;
import com.everyschool.schoolservice.api.controller.school.response.SchoolDetailResponse;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.school.repository.SchoolRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;


class SchoolQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private SchoolAppQueryService schoolQueryService;

    @Autowired
    private SchoolRepository schoolRepository;

    @DisplayName("학교명이 포함된 모든 학교 목록을 조회할 수 있다.")
    @Test
    void searchSchools() {
        //given
        String query = "수완";

        School school1 = saveSchool("수완고등학교", "62246", "광주광역시 광산구 장덕로 155", "http://suwan.gen.hs.kr", "062-961-5746", LocalDate.of(2009, 3, 1), 7);
        School school2 = saveSchool("수완중학교", "62308", "광주광역시 광산구 수완로 19", "http://suwan.gen.ms.kr", "062-975-2206", LocalDate.of(2009, 3, 1), 6);
        School school3 = saveSchool("수완초등학교", "62246", "광주광역시 광산구 장덕로 143", " http://suwan.gen.es.kr", "062-960-9000", LocalDate.of(2008, 9, 1), 5);
        School school4 = saveSchool("수완하나중학교", "62247", "광주광역시 광산구 수완로105번길 47", "http://suwanhana.gen.ms.kr", "062-720-2641", LocalDate.of(2015, 3, 1), 6);
        school4.remove();

        //when
        List<SchoolResponse> responses = schoolQueryService.searchSchools(query);

        //then
        assertThat(responses).hasSize(3);
        assertThat(responses)
            .extracting("name", "address")
            .containsExactlyInAnyOrder(
                tuple("수완고등학교", "광주광역시 광산구 장덕로 155"),
                tuple("수완중학교", "광주광역시 광산구 수완로 19"),
                tuple("수완초등학교", "광주광역시 광산구 장덕로 143")
            );
    }

    @DisplayName("학교 정보가 존재하지 않으면 예외가 발생한다.")
    @Test
    void searchOneSchoolWithoutSchool() {
        //given

        //when //then
        assertThatThrownBy(() -> schoolQueryService.searchSchoolInfo(1L))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("등록이 되지 않은 학교입니다.");
    }

    @DisplayName("조회된 학교가 삭제된 학교라면 예외가 발생한다.")
    @Test
    void searchOneSchoolRemovedSchool() {
        //given
        School school = saveSchool("수완고등학교", "62246", "광주광역시 광산구 장덕로 155", "http://suwan.gen.hs.kr", "062-961-5746", LocalDate.of(2009, 3, 1), 7);
        school.remove();

        //when //then
        assertThatThrownBy(() -> schoolQueryService.searchSchoolInfo(school.getId()))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("등록이 되지 않은 학교입니다.");
    }

    @DisplayName("학교 PK로 학교 정보를 조회할 수 있다.")
    @Test
    void searchOneSchool() {
        //given
        School school = saveSchool("수완고등학교", "62246", "광주광역시 광산구 장덕로 155", "http://suwan.gen.hs.kr", "062-961-5746", LocalDate.of(2009, 3, 1), 7);

        //when
        SchoolDetailResponse response = schoolQueryService.searchSchoolInfo(school.getId());

        //then
        assertThat(response.getUrl()).isEqualTo("http://suwan.gen.hs.kr");
    }

    private School saveSchool(String name, String zipcode, String address, String url, String tel, LocalDate localDate, int schoolTypeCodeId) {
        School school = School.builder()
            .name(name)
            .zipcode(zipcode)
            .address(address)
            .url(url)
            .tel(tel)
            .openDate(localDate.atStartOfDay())
            .schoolTypeCodeId(schoolTypeCodeId)
            .build();
        return schoolRepository.save(school);
    }
}