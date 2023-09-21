package com.example.shiftlabtt.model;

public interface Interval<V extends Interval<V>>{
    boolean intersect(V b);
    V union(V b);
}
