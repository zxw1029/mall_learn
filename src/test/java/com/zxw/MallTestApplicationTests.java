package com.zxw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MallTestApplicationTests {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
        //test
        System.out.println("test===" + passwordEncoder.encode("test"));
        System.out.println("admin===" + passwordEncoder.encode("admin"));
        System.out.println("macro===" + passwordEncoder.encode("macro"));
        System.out.println("productAdmin===" + passwordEncoder.encode("productAdmin"));
        System.out.println("orderAdmin===" + passwordEncoder.encode("orderAdmin"));
    }

}
