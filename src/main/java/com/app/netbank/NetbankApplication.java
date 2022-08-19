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
		String sql = "INSERT INTO users (email, first_name, last_name, password, roles, balance) VALUES (?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, "admin@netbank.com", "Admin", "Main", passwordEncoder.encode("admin"), "ROLE_ADMIN", null);
		jdbcTemplate.update(sql, "zoli@netbank.com", "Zoli", "Nagy", passwordEncoder.encode("zoli"), "ROLE_USER", 245000);
		jdbcTemplate.update(sql, "evelin@netbank.com", "Evelin", "Varga", passwordEncoder.encode("evelin"), "ROLE_USER", 450000);
	}
}
