package com.semicolon.RepSetGoApi.Controllers.SetController;

import com.semicolon.RepSetGoApi.Services.SetService.SetService;
import com.semicolon.RepSetGoApi.Sets.CreateSetRequestDto;
import com.semicolon.RepSetGoApi.Sets.SetDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rep-set-go/set")
public class setController {

    @Autowired
    SetService setService;

    @GetMapping("/{workout_id}")
    public ResponseEntity<List<SetDto>> getSetListForWorKoutId(@PathVariable Long workout_id) {
        List<SetDto> setList = setService.getSetListForWorkOutId(workout_id);
        return ResponseEntity.status(HttpStatus.OK).body(setList);
    }
    @PostMapping()
    public ResponseEntity<SetDto> createSet(@RequestBody CreateSetRequestDto createSetRequestDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        SetDto setDto = modelMapper.map(createSetRequestDto,SetDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(setService.createSet(setDto));
    }

    @GetMapping("/progress/{workout_id}")
    public ResponseEntity<Map<LocalDate,Double>> calculateVolume(@PathVariable Long workout_id) {
        return ResponseEntity.ok().body(setService.calculateVolume(workout_id));
    }

}
