package com.semicolon.RepSetGoApi.Controllers.WorkOutControllers.WorkOut;

import com.semicolon.RepSetGoApi.Services.WorkOutServices.WorkOut.WorkOutService;
import com.semicolon.RepSetGoApi.workout.models.CreateWorkOutRequestDo;
import com.semicolon.RepSetGoApi.workout.models.DeleteWorkOutRequestDo;
import com.semicolon.RepSetGoApi.workout.models.WorkOutDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/rep-set-go/workout")
public class WorkOutController {

    @Autowired
    private WorkOutService workOutService;

    @PostMapping()
    public ResponseEntity<WorkOutDTO> createWorkoutForWorkOutGroup(@RequestBody CreateWorkOutRequestDo createWorkOutRequestDo) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WorkOutDTO workOutDTO = modelMapper.map(createWorkOutRequestDo,WorkOutDTO.class);
        WorkOutDTO createdWorkOutDTO = workOutService.createWorkOutForWorkOutGroup(workOutDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkOutDTO);
    }
    @GetMapping("/{group_id}")
    public ResponseEntity<List<WorkOutDTO>> getWorkOutListByGroupId(@PathVariable Long group_id) {
            List<WorkOutDTO> workOutDTOList = workOutService.getWorkOutListByGroupId(group_id);
            return ResponseEntity.status(HttpStatus.OK).body(workOutDTOList);
    }

    @DeleteMapping()
    public ResponseEntity deleteWorkOut(@RequestBody DeleteWorkOutRequestDo deleteWorkOutRequestDo){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WorkOutDTO workOutDTO = modelMapper.map(deleteWorkOutRequestDo,WorkOutDTO.class);
        workOutService.deleteWorkOut(workOutDTO);
        return ResponseEntity.ok().build();
    }

}
