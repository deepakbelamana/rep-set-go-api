package com.semicolon.RepSetGoApi.Services.WorkOutServices.WorkOutGroup.Impl;

import com.semicolon.RepSetGoApi.Repositories.UserRepository;
import com.semicolon.RepSetGoApi.Repositories.WorkOutGroupRepository;
import com.semicolon.RepSetGoApi.Services.WorkOutServices.WorkOutGroup.WorkOutGroupService;
import com.semicolon.RepSetGoApi.workout.models.WorkOutGroupDTO;
import com.semicolon.RepSetGoApi.workout.models.WorkOutGroupEntity;
import com.semicolon.RepSetGoApi.users.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

@Service
public class WorkOutGroupServiceImpl implements WorkOutGroupService {

    @Autowired
    private WorkOutGroupRepository workOutGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public WorkOutGroupDTO addWorkOutGroup(WorkOutGroupDTO workOutGroupDTO) {
        // Fetch the user
        UserEntity user = userRepository.findById(workOutGroupDTO.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + workOutGroupDTO.getUser_id()));

        // Create and configure the entity
        WorkOutGroupEntity workOutGroupEntity = new WorkOutGroupEntity();
        workOutGroupEntity.setUserEntity(user);
        workOutGroupEntity.setGroupName(workOutGroupDTO.getGroup_name());
        workOutGroupEntity.setCreatedDate(LocalDateTime.now());

        // Save the entity
        WorkOutGroupEntity savedEntity = workOutGroupRepository.save(workOutGroupEntity);

        // Map back to DTO
        WorkOutGroupDTO responseDTO = new WorkOutGroupDTO();
        responseDTO.setUser_id(savedEntity.getUserEntity().getUserId().toString());
        responseDTO.setGroup_name(savedEntity.getGroupName());
        responseDTO.setCreated_date(savedEntity.getCreatedDate());
        responseDTO.setGroup_id(savedEntity.getGroup_id());

        return responseDTO;
    }

    @Override
    public List<WorkOutGroupDTO> getWorkOutGroupByUserId(String userId) {
        List<WorkOutGroupEntity> workOutGroupEntitiesOfUser = workOutGroupRepository.findByUserId(userId);
        List<WorkOutGroupDTO> workOutGroupDTOSofUser=new LinkedList<>();
        for(WorkOutGroupEntity workOutGroupEntity : workOutGroupEntitiesOfUser) {
            workOutGroupDTOSofUser.add(mapEntityToDTO(workOutGroupEntity));
        }
        return workOutGroupDTOSofUser;
    }

    public WorkOutGroupDTO mapEntityToDTO(WorkOutGroupEntity workOutGroupEntity){
        WorkOutGroupDTO workOutGroupDTO = new WorkOutGroupDTO();
        workOutGroupDTO.setCreated_date(workOutGroupEntity.getCreatedDate());
        workOutGroupDTO.setGroup_name(workOutGroupEntity.getGroupName());
        workOutGroupDTO.setUser_id(workOutGroupEntity.getUserEntity().getUserId());
        workOutGroupDTO.setGroup_id(workOutGroupEntity.getGroup_id());
        return workOutGroupDTO;
    }

    public WorkOutGroupEntity getWorkOutGroupByGroupId(Long group_id){
        Optional<WorkOutGroupEntity> workOutGroupEntity = workOutGroupRepository.findById(group_id);
        return workOutGroupEntity.get();
    }
}
