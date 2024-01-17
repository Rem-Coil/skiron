package com.remcoil.skiron;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SkironApplicationTests {

    @Test
    void contextLoads() throws InterruptedException {
        List<LocalDateTime> times = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            times.add(LocalDateTime.now());
            Thread.sleep(1000);
        }

        List<LocalDateTime> list = times.stream().sorted().toList();
        System.out.println(list);
    }

}
