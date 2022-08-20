package com.app.netbank.repos;

import com.app.netbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = "select * from users where users.email = :emailOrId or users.id = :emailOrId")
    User getUserDetails(@Param("emailOrId") String emailOrId);

    @Query(nativeQuery = true, value = "select users.id from users where users.email = :emailOrNum or account_number = :emailOrNum")
    Long getUserIdByEmailOrNumber(@Param("emailOrNum") String emailOrNum);

    @Query(nativeQuery = true, value = "select email from users")
    List<String> getAllUserEmails();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update users set balance = balance - :amount where users.email = :email")
    void subtractMoneyByEmail(@Param("amount") Integer amount, @Param("email") String email);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update users set balance = balance + :amount where users.email = :email")
    void addMoneyByEmail(@Param("amount") Integer amount, @Param("email") String email);

    //TODO: Fix this! Transactions
    @Query(nativeQuery = true, value = "select * from transactions where email = :emailOrNumber or account_number = :emailOrNumber")
    User searchUser(@Param("emailOrNumber") String emailOrNumber);
}
