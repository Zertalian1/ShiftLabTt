package com.example.shiftlabtt.controller;

import com.example.shiftlabtt.model.DigitsInterval;
import com.example.shiftlabtt.model.LetterInterval;
import com.example.shiftlabtt.service.IntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/intervals")
@RequiredArgsConstructor
public class IntervalController {

    private final IntervalService intervalService;

    @PostMapping(value = "/merge", params = "kind=digits")
    public HttpStatus mergeIntervalDigits(
            @RequestBody List<List<Long>> intervals
    ) {
        intervalService.mergeDigitInterval(intervals.stream().map(
                (List<Long> l) -> new DigitsInterval(l.get(0), l.get(1))
        ).collect(Collectors.toList()));
        return HttpStatus.OK;
    }

    @PostMapping(value = "/merge", params = "kind=letters")
    public HttpStatus mergeIntervalLetters(
            @RequestBody List<List<Character>> intervals
    ) {
        intervalService.mergeLetterInterval(intervals.stream().map(
                (List<Character> l) -> new LetterInterval(l.get(0), l.get(1))
        ).collect(Collectors.toList()));
        return HttpStatus.OK;
    }

    @GetMapping(value = "/min", params = "kind=digits")
    public ResponseEntity<List<Long>> minIntervalDigits() {
        DigitsInterval interval = intervalService.getMinDigitInterval();
        if (interval == null){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        return new ResponseEntity<>(List.of(interval.getStart(), interval.getEnd()), HttpStatus.OK);
    }

    @GetMapping(value = "/min", params = "kind=letters")
    public ResponseEntity<List<Character>> minIntervalLetters() {
        LetterInterval interval = intervalService.getMinLetterInterval();
        if (interval == null){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        return new ResponseEntity<>(List.of(interval.getStart(), interval.getEnd()), HttpStatus.OK);
    }
}
