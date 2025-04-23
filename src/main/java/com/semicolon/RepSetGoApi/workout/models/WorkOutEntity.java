package com.semicolon.RepSetGoApi.workout.models;

import jakarta.persistence.*;

@Entity
@Table(name = "WORKOUT")
public class WorkOutEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workout_id;

    @Column(name = "workout_name")
    private String workout_name;

    @ManyToOne()
    @JoinColumn(name = "group_id")
    private WorkOutGroupEntity workOutGroupEntity;

    public Long getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(Long workout_id) {
        this.workout_id = workout_id;
    }

    public String getWorkout_name() {
        return workout_name;
    }

    public void setWorkout_name(String workout_name) {
        this.workout_name = workout_name;
    }

    public WorkOutGroupEntity getWorkOutGroupEntity() {
        return workOutGroupEntity;
    }

    public void setWorkOutGroupEntity(WorkOutGroupEntity workOutGroupEntity) {
        this.workOutGroupEntity = workOutGroupEntity;
    }
}
