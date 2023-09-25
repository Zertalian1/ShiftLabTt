package com.example.shiftlabtt;

import com.example.shiftlabtt.model.DigitsInterval;
import com.example.shiftlabtt.model.LetterInterval;
import com.example.shiftlabtt.service.IntervalService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = ShiftLabTtApplication.class)
class InternalServiceTest {
    @Autowired
    private IntervalService intervalService;

    /**
     * Добавление и получения минимального численного интервала без пересечений
     */
    @Test
    @Transactional
    void saveAndGetDigitWithoutIntersection() {
        List<DigitsInterval> intervals = new ArrayList<>(List.of(
                new DigitsInterval(1L, 10L),
                new DigitsInterval(11L, 19L),
                new DigitsInterval(20L, 23L)
        ));
        intervalService.mergeDigitInterval(intervals);
        DigitsInterval minInterval = intervalService.getMinDigitInterval();
        Assertions.assertNotNull(minInterval);
        Assertions.assertEquals(minInterval.getStart(), 20L);
        Assertions.assertEquals(minInterval.getEnd(), 23L);
    }

    /**
     * Добавление и получения минимального численного интервала с пересечением
     */
    @Test
    @Transactional
    void saveAndGetDigitWithIntersection() {
        List<DigitsInterval> intervals = new ArrayList<>(List.of(
                new DigitsInterval(1L, 2L),
                new DigitsInterval(10L, 23L),
                new DigitsInterval(2L, 5L)
        ));
        intervalService.mergeDigitInterval(intervals);
        DigitsInterval minInterval = intervalService.getMinDigitInterval();
        Assertions.assertNotNull(minInterval);
        Assertions.assertEquals(minInterval.getStart(), 1L);
        Assertions.assertEquals(minInterval.getEnd(), 5L);
    }

    /**
     * Добавление и получения минимального буквенного интервала без пересечений
     */
    @Test
    @Transactional
    void saveAndGetLetterWithoutIntersection() {
        List<LetterInterval> intervals = new ArrayList<>(List.of(
                new LetterInterval('a', 'g'),
                new LetterInterval('h', 'n'),
                new LetterInterval('o', 'r')
        ));
        intervalService.mergeLetterInterval(intervals);
        LetterInterval minInterval = intervalService.getMinLetterInterval();
        Assertions.assertNotNull(minInterval);
        Assertions.assertEquals(minInterval.getStart(), 'o');
        Assertions.assertEquals(minInterval.getEnd(), 'r');
    }

    /**
     * Добавление и получения минимального буквенного интервала с пересечением
     */
    @Test
    @Transactional
    void saveAndGetLetterWithIntersection() {
        List<LetterInterval> intervals = new ArrayList<>(List.of(
                new LetterInterval('a', 'c'),
                new LetterInterval('h', 'n'),
                new LetterInterval('c', 'e')
        ));
        intervalService.mergeLetterInterval(intervals);
        LetterInterval minInterval = intervalService.getMinLetterInterval();
        Assertions.assertNotNull(minInterval);
        Assertions.assertEquals(minInterval.getStart(), 'a');
        Assertions.assertEquals(minInterval.getEnd(), 'e');
    }
}
