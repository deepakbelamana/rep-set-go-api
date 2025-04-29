package com.semicolon.RepSetGoApi.Services.SetService;

import com.semicolon.RepSetGoApi.Sets.SetDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SetService {

    SetDto createSet(SetDto setDto);
    List<SetDto> getSetListForWorkOutId(Long workout_id);

    Map<LocalDate,Double> calculateVolume(Long workoutId);

    Double calculateOneRepMax(Long workoutId);
}
