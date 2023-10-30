package com.everyschool.reportservice.api.service.report.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateReportDto {

    private Integer typeId;
    private String title;
    private String who;
    private String when;
    private String where;
    private String what;
    private String how;
    private String why;

    @Builder
    private CreateReportDto(Integer typeId, String title, String who, String when, String where, String what, String how, String why) {
        this.typeId = typeId;
        this.title = title;
        this.who = who;
        this.when = when;
        this.where = where;
        this.what = what;
        this.how = how;
        this.why = why;
    }
}
