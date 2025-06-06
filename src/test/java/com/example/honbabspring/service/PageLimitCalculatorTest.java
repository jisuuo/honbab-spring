package com.example.honbabspring.service;

import com.example.honbabspring.global.util.PageLimitCalculator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PageLimitCalculatorTest {

    @Test
    void calculatePageLimit() {
        calculatePageLimitTest(1L, 30L, 10L, 301L);
        calculatePageLimitTest(7L, 30L, 10L, 301L);
        calculatePageLimitTest(10L, 30L, 10L, 301L);
        calculatePageLimitTest(11L, 30L, 10L, 601L);
        calculatePageLimitTest(12L, 30L, 10L, 601L);


    }

    void calculatePageLimitTest(Long page, Long pageSize, Long movablePageCount, Long expected) {
        Long result = PageLimitCalculator.calculatePageLimit(page, pageSize, movablePageCount);
        assertThat(result).isEqualTo(expected);
    }

}