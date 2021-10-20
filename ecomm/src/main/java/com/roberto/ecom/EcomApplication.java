package com.roberto.ecom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

@SpringBootApplication
public class EcomApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository catRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CityRepository cityRepo;

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	AddressRepository addressRepo;

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private OrderItemRepository orderItemRepo;

	public static void main(String[] args) {
		SpringApplication.run(EcomApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// working with Products and Categories
		Category cat1 = new Category(null, "Cat1");
		Category cat2 = new Category(null, "Cat2");

		Product prod1 = new Product(null, "Product 1", 2000.0);
		Product prod2 = new Product(null, "Product 2", 800.0);
		Product prod3 = new Product(null, "Product 3", 80.0);

		cat1.addProducts(prod1, prod2, prod3);
		cat2.addProducts(prod2);

		prod1.addCategories(cat1);
		prod2.addCategories(cat1, cat2);
		prod3.addCategories(cat1);

		catRepo.saveAll(Arrays.asList(cat1, cat2));
		productRepo.saveAll(Arrays.asList(prod1, prod2, prod3));

		// working with Cities and States
		State st1 = new State(null, "Minas Gerais");
		State st2 = new State(null, "São Paulo");

		City city1 = new City(null, "Cachoeira de Minas", st1);
		City city2 = new City(null, "Campinas", st2);
		City city3 = new City(null, "Osasco", st2);

		st1.addCities(city1);
		st2.addCities(city2, city3);

		stateRepo.saveAll(Arrays.asList(st1, st2));
		cityRepo.saveAll(Arrays.asList(city1, city2, city3));

		// Customer and its informations
		Customer c1 = new Customer("1", "C1", "c1@gmail", CustomerType.NATURAL_PERSON);

		c1.addPhones("1111", "2222");

		Customer c2 = new Customer("2", "C2", "c2@gmail", CustomerType.LEGAL_PERSON);
		c2.addPhones("3333", "4444");

		Address a1 = new Address(null, "R1", 1, "111", "B1", "11111", c1, city1);
		Address a2 = new Address(null, "R2", 2, "222", "B2", "22222", c1, city2);
		Address a3 = new Address(null, "R3", 3, "333", "B3", "33333", c2, city3);

		c1.addAddress(a1, a2);
		c2.addAddress(a3);

		customerRepo.saveAll(Arrays.asList(c1, c2));
		addressRepo.saveAll(Arrays.asList(a1, a2, a3));

		// Orders
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		Order o1 = new Order(null, LocalDateTime.parse("2021-10-18 13:15:00", dateTimeFormatter), c1, a1);
		Order o2 = new Order(null, LocalDateTime.parse("2021-10-19 19:21:00", dateTimeFormatter), c1, a2);

		Payment pay1 = new PaymentCard(null, PaymentStatus.PAID,      o1, 6);
		Payment pay2 = new PaymentBullet(null, PaymentStatus.PENDING, o2
						, null, LocalDate.parse("2021-10-20", dateFormatter));
		
		o1.setPayment(pay1);
		o2.setPayment(pay2);

		c1.addOrders(o1, o2);

		orderRepo.saveAll(Arrays.asList(o1, o2));
		//paymentRepo.saveAll(Arrays.asList(pay1, pay2)); //não precisa por causa do CascadeType.ALL no pedido

		OrderItem it1 = new OrderItem(o1, prod1, 0.0, 1, 2000.0);
		OrderItem it2 = new OrderItem(o1, prod3, 0.0, 2, 80.0);
		OrderItem it3 = new OrderItem(o2, prod2, 100.0, 2, 800.0);

		o1.addItems(it1, it2);
		o2.addItems(it3);

		prod1.addItems(it1);
		prod2.addItems(it2);
		prod3.addItems(it2);
		
		orderItemRepo.saveAll(Arrays.asList(it1, it2, it3));
	}
}
