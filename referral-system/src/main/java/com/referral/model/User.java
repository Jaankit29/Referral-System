//package com.referral.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Getter // Automatically generates all getters (including getName(), getReferredBy())
//@Setter // Automatically generates all setters
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private String email;
//    private Long referredBy;
//
//    // No-arg constructor
//    public User() {}
//
//    // Constructor with parameters
//    public User(String name, String email, Long referredBy) {
//        this.name = name;
//        this.email = email;
//        this.referredBy = referredBy;
//    }
//}

package com.referral.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter // Lombok will generate getName(), getReferredBy() automatically
@Setter // Lombok will generate setters for all fields
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private Long referredBy;

    // No-arg constructor
    public User() {}

    // Constructor with parameters
    public User(String name, String email, Long referredBy) {
        this.name = name;
        this.email = email;
        this.referredBy = referredBy;
    }
}
