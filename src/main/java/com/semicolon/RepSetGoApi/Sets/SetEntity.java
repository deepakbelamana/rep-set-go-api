package com.semicolon.RepSetGoApi.Sets;

import com.semicolon.RepSetGoApi.workout.models.WorkOutEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "WORKOUT_SET")
public class SetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long set_id;

    @ManyToOne()
    @JoinColumn(name = "workout_id")
    private WorkOutEntity workOutEntity;

    @Column(name="rep_count")
    private Integer rep_count;

    @Column(name="created_date")
    private LocalDateTime created_date;

    @Column(name = "weight")
    private  Double weight;

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Long getSet_id() {
        return set_id;
    }

    public void setSet_id(Long set_id) {
        this.set_id = set_id;
    }

    public WorkOutEntity getWorkOutEntity() {
        return workOutEntity;
    }

    public void setWorkOutEntity(WorkOutEntity workOutEntity) {
        this.workOutEntity = workOutEntity;
    }

    public Integer getRep_count() {
        return rep_count;
    }

    public void setRep_count(Integer rep_count) {
        this.rep_count = rep_count;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }
}
