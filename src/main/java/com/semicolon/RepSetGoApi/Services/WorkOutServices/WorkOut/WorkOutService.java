package com.semicolon.RepSetGoApi.Services.WorkOutServices.WorkOut;

import com.semicolon.RepSetGoApi.workout.models.WorkOutDTO;

import java.util.List;

public interface WorkOutService {
    WorkOutDTO createWorkOutForWorkOutGroup(WorkOutDTO workOutDTO);
    List<WorkOutDTO> getWorkOutListByGroupId(Long group_id);
    WorkOutDTO deleteWorkOut(WorkOutDTO workOutDTO);
    WorkOutDTO updateWorkOut(WorkOutDTO workOutDTO);
}
