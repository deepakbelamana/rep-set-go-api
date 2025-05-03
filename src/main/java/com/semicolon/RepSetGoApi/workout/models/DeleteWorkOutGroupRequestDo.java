package com.semicolon.RepSetGoApi.workout.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeleteWorkOutGroupRequestDo {
    private  String user_id;
    private  String group_name;
    private LocalDateTime created_date;
    private Long group_id;
}
