package com.example.peking.repository;

import com.example.peking.entity.LogisticsRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticsRecordsRepository extends JpaRepository<LogisticsRecords, Integer> {
}
