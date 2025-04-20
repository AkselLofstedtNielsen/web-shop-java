package org.example.java_labbwebshop.order;

import org.example.java_labbwebshop.user.User;

public class UserBuilderTest {
    public static void main(String[] args) {
        User user = User.builder()
                .email("micke@example.com")
                .password("Test1234")
                .build();

        if (user.getOrders() == null) {
            System.out.println("Orders is null");
        } else {
            System.out.println("Orders is initialized");
        }
    }
}

