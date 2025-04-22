package com.semicolon.RepSetGoApi.Services.WorkOutServices.WorkOurGroup;

import com.semicolon.RepSetGoApi.workout.models.WorkOutGroupDTO;

import java.util.LinkedList;
import java.util.List;


public interface WorkOutGroupService {

    WorkOutGroupDTO addWorkOutGroup(WorkOutGroupDTO workOutGroupDTO);
    List<WorkOutGroupDTO> getWorkOutGroupByUserId(String userId);
}
