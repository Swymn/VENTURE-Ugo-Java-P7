package com.nnk.springboot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */
@SpringBootTest
class PasswordEncodeTest {

    @Test
    void testPassword() {
        final var encoder = new BCryptPasswordEncoder();
        final var pw = encoder.encode("123456");
        System.out.println("[ "+ pw + " ]");
    }
}
