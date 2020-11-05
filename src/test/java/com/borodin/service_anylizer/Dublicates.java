package com.borodin.service_anylizer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Dublicates {



    @Test
    public void duplicates() throws Exception {
        List<Integer> items = Arrays.asList(1, 1, 2, 2, 2, 2);

        Map<Integer, Long> result = items.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Assert.assertEquals(Long.valueOf(2), result.get(1));
        Assert.assertEquals(Long.valueOf(4), result.get(2));
    }

}
