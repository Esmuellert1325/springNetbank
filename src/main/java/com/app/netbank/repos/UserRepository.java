package com.app.netbank.repos;

import com.app.netbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = "select * from users where users.email = :email")
    User getUserDetails(@Param("email") String email);
}
