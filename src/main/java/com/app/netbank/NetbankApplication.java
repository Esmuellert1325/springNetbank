package com.app.netbank;

import com.app.netbank.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@SpringBootApplication
public class NetbankApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public NetbankApplication(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(NetbankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String sql = "INSERT INTO users (email, account_number, first_name, last_name, password, roles, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, "admin@netbank.com", null, "Admin", "Main", passwordEncoder.encode("admin"), "ROLE_ADMIN", null);
		jdbcTemplate.update(sql, "zoli@netbank.com", "22222222-22222222-22222222", "Zoli", "Nagy", passwordEncoder.encode("zoli"), "ROLE_USER", 245000);
		jdbcTemplate.update(sql, "evelin@netbank.com", "33333333-33333333-33333333", "Evelin", "Varga", passwordEncoder.encode("evelin"), "ROLE_USER", 450000);
		jdbcTemplate.update(sql, "tibi@netbank.com", "44444444-44444444-44444444", "Tibor", "Szőke", passwordEncoder.encode("tibi"), "ROLE_USER", 750000);

		String tranSql = "insert into transactions (amount, to_user_email, transaction_date, user_id) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(tranSql, 8000, "zoli@netbank.com", "2022-07-19 20:25:28.703000", 3);
	}
}
