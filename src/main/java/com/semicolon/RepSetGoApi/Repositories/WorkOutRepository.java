package com.semicolon.RepSetGoApi.Repositories;

import com.semicolon.RepSetGoApi.workout.models.WorkOutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkOutRepository extends JpaRepository<WorkOutEntity,Long> {

    @Query(nativeQuery = true,value = "select wt.workout_id, wt.workout_name, wt.group_id " +
            "from workout wt join workout_group wg on wg.group_id=wt.group_id " +
            "where wg.group_id=:group_id")
    List<WorkOutEntity> findAllByGroupId(@Param("group_id") Long group_id);
}
