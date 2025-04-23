package com.semicolon.RepSetGoApi.workout.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWorkOutRequestDo {
    private String group_id;
    private String workout_name;
}
