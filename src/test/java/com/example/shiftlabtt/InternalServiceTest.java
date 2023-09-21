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

    @Test
    @Transactional
    void saveAndGetDigit() {
        List<DigitsInterval> intervals = new ArrayList<>(List.of(
                new DigitsInterval(1L, 10L),
                new DigitsInterval(3L, 4L),
                new DigitsInterval(4L, 7L),
                new DigitsInterval(20L, 23L)
        ));
        intervalService.mergeDigitInterval(intervals);
        DigitsInterval minInterval = intervalService.getMinDigitInterval();
        Assertions.assertNotNull(minInterval);
        Assertions.assertEquals(minInterval.getStart(), 20L);
        Assertions.assertEquals(minInterval.getEnd(), 23L);
    }

    @Test
    @Transactional
    void saveAndGetLetter() {
        List<LetterInterval> intervals = new ArrayList<>(List.of(
                new LetterInterval('a', 'c'),
                new LetterInterval('d', 'k'),
                new LetterInterval('a', 'u'),
                new LetterInterval('v', 'z')
        ));
        intervalService.mergeLetterInterval(intervals);
        LetterInterval minInterval = intervalService.getMinLetterInterval();
        Assertions.assertNotNull(minInterval);
        Assertions.assertEquals(minInterval.getStart(), 'v');
        Assertions.assertEquals(minInterval.getEnd(), 'z');
    }
}
