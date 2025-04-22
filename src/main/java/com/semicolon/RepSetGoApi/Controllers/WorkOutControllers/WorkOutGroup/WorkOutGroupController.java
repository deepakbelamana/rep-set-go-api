package com.semicolon.RepSetGoApi.Controllers.WorkOutControllers.WorkOutGroup;

import com.semicolon.RepSetGoApi.Services.WorkOutServices.WorkOurGroup.WorkOutGroupService;
import com.semicolon.RepSetGoApi.workout.models.CreateWorkOutGroupDTO;
import com.semicolon.RepSetGoApi.workout.models.WorkOutGroupDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
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

}
