package com.semicolon.RepSetGoApi.workout.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
public class CreateWorkOutGroupDTO {
    private String user_id;
    private String group_name;
    private LocalDateTime created_date;
}
