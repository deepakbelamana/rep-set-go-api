package com.semicolon.RepSetGoApi.Services.WorkOutServices.WorkOut.impl;

import com.semicolon.RepSetGoApi.Repositories.WorkOutRepository;
import com.semicolon.RepSetGoApi.Services.WorkOutServices.WorkOut.WorkOutService;
import com.semicolon.RepSetGoApi.Services.WorkOutServices.WorkOutGroup.WorkOutGroupService;
import com.semicolon.RepSetGoApi.workout.models.WorkOutDTO;
import com.semicolon.RepSetGoApi.workout.models.WorkOutEntity;
import com.semicolon.RepSetGoApi.workout.models.WorkOutGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class WorkOutServiceImpl implements WorkOutService {
    
    @Autowired
    private WorkOutRepository workOutRepository;
    
    @Autowired
    private WorkOutGroupService workOutGroupService;
    
    @Override
    public WorkOutDTO createWorkOutForWorkOutGroup(WorkOutDTO workOutDTO) {
        WorkOutEntity workOutEntity = mapToWorkOutEntity(workOutDTO);
        WorkOutEntity createdWorkOutEntity = workOutRepository.save(workOutEntity);
        return mapToWorkOutDTO(createdWorkOutEntity);
    }

    @Override
    public List<WorkOutDTO> getWorkOutListByGroupId(Long group_id) {
            List<WorkOutEntity> workOutEntities = workOutRepository.findAllByGroupId(group_id);
            List<WorkOutDTO> workOutDTOList = new LinkedList<>();
            for(WorkOutEntity workOutEntity : workOutEntities) {
                workOutDTOList.add(mapToWorkOutDTO(workOutEntity));
            }
            return workOutDTOList;
    }

    @Override
    public WorkOutDTO deleteWorkOut(WorkOutDTO workOutDTO) {
        WorkOutEntity deleteWorkOutEntity = mapToWorkOutEntity(workOutDTO);
        workOutRepository.delete(deleteWorkOutEntity);
        return workOutDTO;
    }

    @Override
    public WorkOutDTO updateWorkOut(WorkOutDTO workOutDTO) {
        WorkOutEntity workOutEntity = mapToWorkOutEntity(workOutDTO);
        WorkOutEntity updatedWorkOutEntity = workOutRepository.save(workOutEntity);
        return mapToWorkOutDTO(updatedWorkOutEntity);
    }

    private WorkOutDTO mapToWorkOutDTO(WorkOutEntity createdWorkOutEntity) {
        WorkOutDTO workOutDTO = new WorkOutDTO();
        workOutDTO.setWorkout_id(createdWorkOutEntity.getWorkout_id());
        workOutDTO.setGroup_id(createdWorkOutEntity.getWorkOutGroupEntity().getGroup_id());
        workOutDTO.setWorkout_name(createdWorkOutEntity.getWorkout_name());
        return workOutDTO;
    }

    private WorkOutEntity mapToWorkOutEntity(WorkOutDTO workOutDTO) {
        WorkOutEntity workOutEntity = new WorkOutEntity();
        workOutEntity.setWorkout_name(workOutDTO.getWorkout_name());
        workOutEntity.setWorkout_id(workOutDTO.getWorkout_id());
        workOutEntity.setWorkOutGroupEntity(workOutGroupService
                .getWorkOutGroupByGroupId(workOutDTO.getGroup_id()));
        return workOutEntity;

    }
}
