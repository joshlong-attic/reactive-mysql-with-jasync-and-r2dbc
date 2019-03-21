package com.example.rx;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;
import com.github.jasync.sql.db.mysql.util.URLParser;
import io.r2dbc.spi.ConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class BootifulReactiveMySqlApplication {

	public static void main(String args[]) {
		System.setProperty("spring.main.lazy-initialization", "true");
		SpringApplication.run(BootifulReactiveMySqlApplication.class, args);
	}
}

@Log4j2
@Component
class Initializer {

	private final OrderRepository or;

	Initializer(OrderRepository or) {
		this.or = or;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void go() {
		this.or.findAll().subscribe(log::info);
	}
}

@EnableR2dbcRepositories
@Configuration
class MySqlR2dbConfiguration extends AbstractR2dbcConfiguration {

	@Override
	public ConnectionFactory connectionFactory() {
		String url = "mysql://orders:orders@127.0.0.1:3306/orders";
		return new JasyncConnectionFactory(new MySQLConnectionFactory(
			URLParser.INSTANCE.parseOrDie(url, StandardCharsets.UTF_8)));
	}
}

interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("orders")
class Order {

	@Id
	private Long id;

	private String fn;
}
