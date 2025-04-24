package com.semicolon.RepSetGoApi.Repositories;

import com.semicolon.RepSetGoApi.Sets.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SetRepository extends JpaRepository<SetEntity,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM WORKOUT_SET WHERE workout_id = :workout_id")
    List<SetEntity> findAllByWorkOutId(@Param("workout_id") Long workout_id);
}
