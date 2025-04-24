package com.semicolon.RepSetGoApi.Sets;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SetDto {
    private Long workout_id;
    private Integer rep_count;
    private Double weight;
    private LocalDateTime created_date;
}
