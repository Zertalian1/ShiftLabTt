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

    /**
     * Алгоритм, пдеобразующий список интервалов, путём объединения персесекающихся интервалов в один
     * @param intervals отсортированный список интервалов
     * @return преобразованный список интервалов, тип списка соответствует изначальному типу
     * @param <V> - класс, реализующий интерфейс Interval
     */
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

    /**
     * Сохранение численных интервалов с объединением пересекающихся
     * @param intervals список интервалов
     */
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

    /**
     * Сохранение буквенных интервалов с объединением пересекающихся
     * @param intervals список интервалов
     */
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

    /**
     * Получение минимального численного интервала
     * @return минимальный интервал
     */
    public DigitsInterval getMinDigitInterval() {
        return digitsIntervalRepository.getDigitsIntervalByMinLength();
    }

    /**
     * Получение минимального буквенного интервала
     * @return минимальный интервал
     */
    public LetterInterval getMinLetterInterval() {
        return letterIntervalRepository.getLettersIntervalByMinLength();
    }
}
