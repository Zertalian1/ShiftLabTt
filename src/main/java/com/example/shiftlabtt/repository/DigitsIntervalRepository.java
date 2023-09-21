package com.example.shiftlabtt.repository;

import com.example.shiftlabtt.model.DigitsInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DigitsIntervalRepository extends JpaRepository<DigitsInterval, Long> {

    @Transactional(readOnly = true)
    @Query("select i from DigitsInterval i order by  i.end-i.start  asc limit 1")
    DigitsInterval getDigitsIntervalByMinLength();
}
