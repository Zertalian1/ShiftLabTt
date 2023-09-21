package com.example.shiftlabtt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "num_interval", uniqueConstraints = { @UniqueConstraint( columnNames = { "interval_start", "interval_end" } ) })
public class DigitsInterval implements Interval<DigitsInterval>{
    @Id
    @Column(name = "interval_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "interval_start")
    private Long start;
    @Column(name = "interval_end")
    private Long end;

    public DigitsInterval(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean intersect(DigitsInterval b) {
        return this.getStart() <= b.getEnd() && b.getStart() <= this.getEnd();
    }

    @Override
    public DigitsInterval union(DigitsInterval b) {
        Long l = (this.getStart() < b.getStart()) ? this.getStart() : b.getStart();
        Long max = (this.getEnd() > b.getEnd()) ? this.getEnd() : b.getEnd();
        return new DigitsInterval(l, max);
    }

}
