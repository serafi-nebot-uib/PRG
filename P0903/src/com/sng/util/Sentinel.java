package com.sng.util;

public interface Sentinel<T> {
    boolean isSentinel();
    T getSentinel();
}
