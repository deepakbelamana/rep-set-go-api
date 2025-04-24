package com.semicolon.RepSetGoApi.Sets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSetRequestDto {
    private Long workout_id;
    private Integer rep_count;
    private Double weight;
}
