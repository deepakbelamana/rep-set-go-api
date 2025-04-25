package com.semicolon.RepSetGoApi.Repositories;

import com.semicolon.RepSetGoApi.workout.models.WorkOutGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOutGroupRepository extends JpaRepository<WorkOutGroupEntity, Long> {

    @Query(value = "SELECT wg.group_id,wg.user_id,wg.group_name,wg.created_date" +
            " FROM WORKOUT_GROUP wg " +
            "JOIN USERS u ON wg.user_id = u.user_id " +
            "WHERE u.user_id = :userId",
            nativeQuery = true)
    List<WorkOutGroupEntity> findByUserId(@Param("userId") String userId);
}