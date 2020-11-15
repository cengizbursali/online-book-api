package com.getir.onlinebookapi.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ClockTest {

    @Test
    public void it_should_get_now() {
        //Given
        Clock.freeze(LocalDateTime.of(2069, 3, 1, 3, 1, 31));
        final LocalDateTime now = Clock.now();

        //When
        final LocalDateTime actualNow = Clock.now();

        //Then
        Assertions.assertThat(actualNow).isEqualTo(now);
        Clock.unfreeze();
    }

    @Test
    public void it_should_freeze_but_different_times() {
        //Given
        final LocalDateTime now = Clock.now();
        Clock.freeze(LocalDateTime.of(2069, 3, 1, 3, 1, 31));

        //When
        final LocalDateTime actualNow = Clock.now();

        //Then
        Assertions.assertThat(actualNow).isNotEqualTo(now);
        Clock.unfreeze();
    }

    @Test
    public void it_should_unfreeze() {
        //Given
        Clock.freeze(LocalDateTime.of(2069, 3, 1, 3, 1, 31));
        final LocalDateTime now = Clock.now();

        //When
        Clock.unfreeze();
        final LocalDateTime actualNow = Clock.now();

        //Then
        Assertions.assertThat(actualNow).isNotEqualTo(now);
    }

    @Test
    public void it_should_freeze() {
        //Given
        final Date date = DateUtils.convertLocalDateTimeToDate(Clock.now());

        //When
        Clock.freeze(date);

        //Then
        assertThat(date).isEqualTo(DateUtils.convertLocalDateTimeToDate(Clock.now()));
        Clock.unfreeze();
    }

    @Test
    public void it_should_be_equal_or_before_than_now() {
        // Given
        Clock.freeze();

        // When
        LocalDateTime result = Clock.now();

        // Then
        Clock.unfreeze();
        assertThat(result).isBeforeOrEqualTo(LocalDateTime.now());
    }
}