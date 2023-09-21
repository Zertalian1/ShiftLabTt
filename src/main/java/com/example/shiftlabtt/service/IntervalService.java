package com.example.shiftlabtt.service;

import com.example.shiftlabtt.model.DigitsInterval;
import com.example.shiftlabtt.model.Interval;
import com.example.shiftlabtt.model.LetterInterval;
import com.example.shiftlabtt.repository.LetterIntervalRepository;
import com.example.shiftlabtt.repository.DigitsIntervalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IntervalService {
    private final LetterIntervalRepository letterIntervalRepository;
    private final DigitsIntervalRepository digitsIntervalRepository;

    private <V extends Interval<V>> List<V> algorithm(List<V> intervals) {
        for (int i = 1; i < intervals.size(); i++) {
            if (intervals.get(i).intersect(intervals.get(i - 1))) {
                intervals.set(i - 1, intervals.get(i).union(intervals.get(i - 1)));
                intervals.remove(i);
                i--;
            }
        }
        return intervals;
    }

    public void mergeDigitInterval(List<DigitsInterval> intervals) {
        intervals.sort(Comparator.comparingLong(DigitsInterval::getStart));
        List<DigitsInterval> finalIntervals = algorithm(intervals);
        for (DigitsInterval interval : finalIntervals) {
            try {
                digitsIntervalRepository.save(interval);
            } catch (Exception ignore) {
            }
        }
    }

    public void mergeLetterInterval(List<LetterInterval> intervals) {
        intervals.sort(Comparator.comparing(LetterInterval::getStart));
        List<LetterInterval> finalIntervals = algorithm(intervals);
        for (LetterInterval interval : finalIntervals) {
            try {
                letterIntervalRepository.save(interval);
            } catch (Exception ignore) {
            }
        }
    }

    public DigitsInterval getMinDigitInterval() {
        return digitsIntervalRepository.getDigitsIntervalByMinLength();
    }

    public LetterInterval getMinLetterInterval() {
        return letterIntervalRepository.getLettersIntervalByMinLength();
    }
}
