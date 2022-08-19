package com.app.netbank.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String toUserEmail;

    @Column(nullable = false)
    private Date transactionDate;

    @Column(nullable = false)
    private Integer amount;

//    @ManyToMany(mappedBy = "transactions")
//    private Set<User> users = new HashSet<User>();
}
