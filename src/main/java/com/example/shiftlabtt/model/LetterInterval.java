package com.example.shiftlabtt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "let_interval", uniqueConstraints = { @UniqueConstraint( columnNames = { "interval_start", "interval_end" } ) })
public class LetterInterval implements Interval<LetterInterval>{
    @Id
    @Column(name = "interval_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "interval_start")
    private Character start;
    @Column(name = "interval_end")
    private Character end;

    public LetterInterval(Character start, Character end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean intersect(LetterInterval b) {
        return this.getStart() <= b.getEnd() && b.getStart() <= this.getEnd();
    }

    @Override
    public LetterInterval union(LetterInterval b) {
        Character l = (this.getStart() < b.getStart()) ? this.getStart() : b.getStart();
        Character max = (this.getEnd() > b.getEnd()) ? this.getEnd() : b.getEnd();
        return new LetterInterval(l, max);
    }
}
