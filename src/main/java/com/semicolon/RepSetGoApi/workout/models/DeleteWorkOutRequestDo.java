package com.semicolon.RepSetGoApi.workout.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteWorkOutRequestDo {
    private Long workout_id;
    private String workout_name;
    private Long group_id;
}
