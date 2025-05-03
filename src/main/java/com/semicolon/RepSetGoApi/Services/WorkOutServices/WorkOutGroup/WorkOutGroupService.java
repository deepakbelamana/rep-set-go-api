package com.semicolon.RepSetGoApi.Services.WorkOutServices.WorkOutGroup;

import com.semicolon.RepSetGoApi.workout.models.WorkOutDTO;
import com.semicolon.RepSetGoApi.workout.models.WorkOutGroupDTO;
import com.semicolon.RepSetGoApi.workout.models.WorkOutGroupEntity;

import java.util.List;


public interface WorkOutGroupService {

    WorkOutGroupDTO addWorkOutGroup(WorkOutGroupDTO workOutGroupDTO);
    List<WorkOutGroupDTO> getWorkOutGroupByUserId(String userId);

    WorkOutGroupEntity getWorkOutGroupByGroupId(Long groupId);

    WorkOutGroupDTO deleteWorkOutGroup(WorkOutGroupDTO workOutGroupDTO);
    WorkOutGroupDTO updateWorkOutGroup(WorkOutGroupDTO workOutGroupDTO);
}
