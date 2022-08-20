package com.app.netbank.repos;

import com.app.netbank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into transactions(amount, to_user_email, transaction_date, user_id) values (:amount, :toUserEmail, :transactionDate, :fromId)")
    void insertTransaction(
        @Param("amount") Integer amount,
        @Param("toUserEmail") String toUserEmail,
        @Param("transactionDate") Date transactionDate,
        @Param("fromId") Long fromId
    );

    @Query(nativeQuery = true, value = "select * from transactions where user_id = :userId or to_user_email = :toUserEmail")
    List<Transaction> getAllTransactionsByUserId(@Param("userId") Long userId, @Param("toUserEmail") String toUserEmail);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from transactions where id = :transId")
    void removeTransactionById(@Param("transId") Long transId);
}
