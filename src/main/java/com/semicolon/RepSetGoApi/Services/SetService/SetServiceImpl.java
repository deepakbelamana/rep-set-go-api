package com.semicolon.RepSetGoApi.Services.SetService;

import com.semicolon.RepSetGoApi.Sets.SetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.semicolon.RepSetGoApi.Sets.SetEntity;
import com.semicolon.RepSetGoApi.Repositories.SetRepository;
import com.semicolon.RepSetGoApi.Repositories.WorkOutRepository;
import com.semicolon.RepSetGoApi.workout.models.WorkOutEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class SetServiceImpl implements SetService{

    @Autowired
    private SetRepository setRepository;

    @Autowired
    private WorkOutRepository workOutRepository;

    @Override
    public SetDto createSet(SetDto setDto) {
        SetEntity setEntity = mapToEntity(setDto);
        SetEntity savedSetEntity = setRepository.save(setEntity);
        return mapToDto(savedSetEntity);
    }

    @Override
    public List<SetDto> getSetListForWorkOutId(Long workout_id) {
        List<SetEntity> setEntityList = setRepository.findAllByWorkOutId(workout_id);
        List<SetDto> setEntityDtos = new LinkedList<>();
        for (SetEntity setEntity : setEntityList) {
            setEntityDtos.add(mapToDto(setEntity));
        }
        return setEntityDtos;
    }

    private SetEntity mapToEntity(SetDto setDto) {
        SetEntity setEntity = new SetEntity();
        WorkOutEntity workOutEntity = workOutRepository.findById(setDto.getWorkout_id())
            .orElseThrow(() -> new RuntimeException("Workout not found with id: " + setDto.getWorkout_id()));
        setEntity.setWorkOutEntity(workOutEntity);
        setEntity.setRep_count(setDto.getRep_count());
        setEntity.setWeight(setDto.getWeight().doubleValue());
        setEntity.setCreated_date(LocalDateTime.now());
        return setEntity;
    }

    private SetDto mapToDto(SetEntity setEntity) {
        SetDto dto = new SetDto();
        dto.setWorkout_id(setEntity.getWorkOutEntity().getWorkout_id());
        dto.setRep_count(setEntity.getRep_count());
        dto.setWeight(setEntity.getWeight());
        dto.setCreated_date(setEntity.getCreated_date());
        return dto;
    }
    @Override
    public Map<LocalDate, Double> calculateVolume(Long workout_id) {
        List<SetDto> setDtoList = getSetListForWorkOutId(workout_id);
        HashMap<LocalDate,Double> calculatedVolume = new HashMap<>();
        Double volume;
        for(SetDto setDto : setDtoList){
            LocalDate dateKey = setDto.getCreated_date().toLocalDate();
            volume=setDto.getRep_count()*setDto.getWeight();
            if(calculatedVolume.containsKey(dateKey)){
                calculatedVolume.put(dateKey,calculatedVolume.get(dateKey)+volume);
            } else {
                calculatedVolume.put(dateKey,volume);
            }
        }
        return calculatedVolume;
    }

    @Override
    public Double calculateOneRepMax(Long workout_id) {
        Double oneRm=0.0;
        List<SetDto> setDtoList = getSetListForWorkOutId(workout_id);
        for(SetDto setDto : setDtoList){
            double rep=setDto.getRep_count();
            rep=rep/30;
            rep+=1;
            if(rep*setDto.getWeight()>oneRm){
                oneRm=rep*setDto.getWeight();
            }
        }
        return oneRm;
    }
}
