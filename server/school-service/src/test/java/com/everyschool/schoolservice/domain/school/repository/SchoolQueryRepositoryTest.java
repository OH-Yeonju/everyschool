package com.everyschool.schoolservice.domain.school.repository;

import com.everyschool.schoolservice.IntegrationTestSupport;
import com.everyschool.schoolservice.api.controller.school.response.SchoolDetailResponse;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.domain.school.School;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class SchoolQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private SchoolAppQueryRepository schoolQueryRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @DisplayName("이름이 포함된 학교 목록을 조회한다.")
    @Test
    void findByName() {
        // given
        String query = "수완";

        School school1 = saveSchool("수완고등학교", "62246", "광주광역시 광산구 장덕로 155", "http://suwan.gen.hs.kr", "062-961-5746", LocalDate.of(2009, 3, 1), 7);
        School school2 = saveSchool("수완중학교", "62308", "광주광역시 광산구 수완로 19", "http://suwan.gen.ms.kr", "062-975-2206", LocalDate.of(2009, 3, 1), 6);
        School school3 = saveSchool("수완초등학교", "62246", "광주광역시 광산구 장덕로 143", " http://suwan.gen.es.kr", "062-960-9000", LocalDate.of(2008, 9, 1), 5);
        School school4 = saveSchool("수완하나중학교", "62247", "광주광역시 광산구 수완로105번길 47", "http://suwanhana.gen.ms.kr", "062-720-2641", LocalDate.of(2015, 3, 1), 6);
        school4.remove();

        // when
        List<SchoolResponse> responses = schoolQueryRepository.findByNameLike(query);

        // then
        assertThat(responses.size()).isEqualTo(3);
        assertThat(responses)
            .extracting("name", "address")
            .containsExactlyInAnyOrder(
                tuple("수완고등학교", "광주광역시 광산구 장덕로 155"),
                tuple("수완중학교", "광주광역시 광산구 수완로 19"),
                tuple("수완초등학교", "광주광역시 광산구 장덕로 143")
            );
    }

    @DisplayName("학교 PK로 학교 정보를 상세 조회한다.")
    @Test
    void findById() {
        //given
        School school = saveSchool("수완고등학교", "62246", "광주광역시 광산구 장덕로 155", "http://suwan.gen.hs.kr", "062-961-5746", LocalDate.of(2009, 3, 1), 7);

        //when
        Optional<SchoolDetailResponse> response = schoolQueryRepository.findById(school.getId());

        //then
        assertThat(response).isPresent();
    }

    @DisplayName("삭제된 학교는 조회되지 않는다.")
    @Test
    void findByIdWithoutSchool() {
        //given
        School school = saveSchool("수완고등학교", "62246", "광주광역시 광산구 장덕로 155", "http://suwan.gen.hs.kr", "062-961-5746", LocalDate.of(2009, 3, 1), 7);
        school.remove();

        //when
        Optional<SchoolDetailResponse> response = schoolQueryRepository.findById(school.getId());

        //then
        assertThat(response).isEmpty();
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