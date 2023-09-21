package com.example.shiftlabtt.repository;

import com.example.shiftlabtt.model.LetterInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LetterIntervalRepository extends JpaRepository<LetterInterval, Long> {

    @Transactional(readOnly = true)
    @Query("select i from LetterInterval i order by  ASCII(i.end)-ASCII(i.start)  asc limit 1")
    LetterInterval getLettersIntervalByMinLength();
}
