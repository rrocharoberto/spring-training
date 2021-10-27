package com.roberto.ecom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import com.roberto.ecom.domain.Address;
import com.roberto.ecom.domain.Category;
import com.roberto.ecom.domain.City;
import com.roberto.ecom.domain.Customer;
import com.roberto.ecom.domain.Order;
import com.roberto.ecom.domain.OrderItem;
import com.roberto.ecom.domain.Payment;
import com.roberto.ecom.domain.PaymentBullet;
import com.roberto.ecom.domain.PaymentCard;
import com.roberto.ecom.domain.Product;
import com.roberto.ecom.domain.State;
import com.roberto.ecom.domain.enums.CustomerType;
import com.roberto.ecom.domain.enums.PaymentStatus;
import com.roberto.ecom.domain.enums.UserProfile;
import com.roberto.ecom.repositories.AddressRepository;
import com.roberto.ecom.repositories.CategoryRepository;
import com.roberto.ecom.repositories.CityRepository;
import com.roberto.ecom.repositories.CustomerRepository;
import com.roberto.ecom.repositories.OrderItemRepository;
import com.roberto.ecom.repositories.OrderRepository;
import com.roberto.ecom.repositories.PaymentRepository;
import com.roberto.ecom.repositories.ProductRepository;
import com.roberto.ecom.repositories.StateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EcomApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EcomApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//moved to DBService
	}
}
