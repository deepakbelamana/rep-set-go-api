package com.semicolon.RepSetGoApi.workout.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateWorkOutGroupRequestDo {
    private  String user_id;
    private  String group_name;
    private LocalDateTime created_date;
    private Long group_id;
}
