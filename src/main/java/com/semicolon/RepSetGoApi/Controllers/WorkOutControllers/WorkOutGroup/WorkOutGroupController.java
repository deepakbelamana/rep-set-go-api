package com.semicolon.RepSetGoApi.Controllers.WorkOutControllers.WorkOutGroup;

import com.semicolon.RepSetGoApi.Services.WorkOutServices.WorkOutGroup.WorkOutGroupService;
import com.semicolon.RepSetGoApi.workout.models.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rep-set-go/group")
public class WorkOutGroupController {

    @Autowired
    private WorkOutGroupService workOutGroupService;

    @PostMapping()
    public ResponseEntity addWorkOutGroup(@RequestBody CreateWorkOutGroupDTO createWorkOutGroupDTO){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WorkOutGroupDTO workOutGroupDTO = modelMapper.map(createWorkOutGroupDTO,WorkOutGroupDTO.class);
        WorkOutGroupDTO createdWorkOutGroupDTO = workOutGroupService.addWorkOutGroup(workOutGroupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkOutGroupDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<WorkOutGroupDTO>> getWorkOutGroupByUserId(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(workOutGroupService.getWorkOutGroupByUserId(userId));
    }

    @DeleteMapping()
    public ResponseEntity deleteWorkOutGroup(@RequestBody DeleteWorkOutGroupRequestDo deleteWorkOutGroupRequestDo){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WorkOutGroupDTO workOutGroupDTO = modelMapper.map(deleteWorkOutGroupRequestDo,WorkOutGroupDTO.class);
        workOutGroupService.deleteWorkOutGroup(workOutGroupDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<WorkOutGroupDTO> updateWorkOut(@RequestBody UpdateWorkOutGroupRequestDo updateWorkOutGroupRequestDo) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WorkOutGroupDTO workOutGroupDTO = modelMapper.map(updateWorkOutGroupRequestDo,WorkOutGroupDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body( workOutGroupService.updateWorkOutGroup(workOutGroupDTO));
    }

}
