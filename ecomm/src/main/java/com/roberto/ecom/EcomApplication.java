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

	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(EcomApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// working with Products and Categories
		Category cat1 = new Category(null, "Cat1");
		Category cat2 = new Category(null, "Cat2");
		// new products for retrieve test
		Category cat3 = new Category(null, "Cat3");
		Category cat4 = new Category(null, "Cat4");
		Category cat5 = new Category(null, "Cat5");
		Category cat6 = new Category(null, "Cat6");
		Category cat7 = new Category(null, "Cat7");

		Product p1 = new Product(null, "Product 1", 2000.0);
		Product p2 = new Product(null, "Product 2", 800.0);
		Product p3 = new Product(null, "Product 3", 80.0);
		// new products for retrieve test
		Product p4 = new Product(null, "P4", 300.0);
		Product p5 = new Product(null, "P5", 50.0);
		Product p6 = new Product(null, "P6", 200.0);
		Product p7 = new Product(null, "P7", 1200.0);
		Product p8 = new Product(null, "P8", 800.0);
		Product p9 = new Product(null, "P9", 100.0);
		Product p10 = new Product(null, "P10", 180.0);
		Product p11 = new Product(null, "P11", 90.0);

		cat1.addProducts(p1, p2, p3);
		cat2.addProducts(p2, p4);
		cat3.addProducts(p5, p6);
		cat4.addProducts(p1, p2, p3, p7);
		cat5.addProducts(p8);
		cat6.addProducts(p9, p10);
		cat7.addProducts(p11);

		p1.addCategories(cat1, cat4);
		p2.addCategories(cat1, cat2, cat4);
		p3.addCategories(cat1, cat4);
		p4.addCategories(cat2);
		p5.addCategories(cat3);
		p6.addCategories(cat3);
		p7.addCategories(cat4);
		p8.addCategories(cat5);
		p9.addCategories(cat6);
		p10.addCategories(cat6);
		p11.addCategories(cat7);

		catRepo.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepo.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

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
		Customer c1 = new Customer("1", "C1", "rrocha.roberto+ecom_c1@gmail.com", CustomerType.NATURAL_PERSON, passwordEncoder.encode("123"));

		c1.addPhones("1111", "2222");

		Customer c2 = new Customer("2", "C2", "rrocha.roberto+ecom_c2@gmail.com", CustomerType.LEGAL_PERSON, passwordEncoder.encode("456"));
		c2.addPhones("3333", "4444");
		c2.addProfile(UserProfile.ADMIN);

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

		Payment pay1 = new PaymentCard(null, PaymentStatus.PAID, o1, 6);
		Payment pay2 = new PaymentBullet(null, PaymentStatus.PENDING, o2, null,
				LocalDate.parse("2021-10-20", dateFormatter));

		o1.setPayment(pay1);
		o2.setPayment(pay2);

		c1.addOrders(o1, o2);

		orderRepo.saveAll(Arrays.asList(o1, o2));
		// paymentRepo.saveAll(Arrays.asList(pay1, pay2)); //não precisa por causa do
		// CascadeType.ALL no pedido

		OrderItem it1 = new OrderItem(o1, p1, 0.0, 1, 2000.0);
		OrderItem it2 = new OrderItem(o1, p3, 0.0, 2, 80.0);
		OrderItem it3 = new OrderItem(o2, p2, 100.0, 2, 800.0);

		o1.addItems(it1, it2);
		o2.addItems(it3);

		p1.addItems(it1);
		p2.addItems(it2);
		p3.addItems(it2);

		orderItemRepo.saveAll(Arrays.asList(it1, it2, it3));

		// categories to test paging
		Category cat10 = new Category(null, "Cat10");
		Category cat11 = new Category(null, "Cat11");
		Category cat12 = new Category(null, "Cat12");
		Category cat13 = new Category(null, "Cat13");
		Category cat14 = new Category(null, "Cat14");
		Category cat15 = new Category(null, "Cat15");
		Category cat16 = new Category(null, "Cat16");
		Category cat17 = new Category(null, "Cat17");
		Category cat18 = new Category(null, "Cat18");
		Category cat19 = new Category(null, "Cat19");

		catRepo.saveAll(Arrays.asList(cat10, cat11, cat12, cat13, cat14, cat15, cat16, cat17, cat18, cat19));

	}
}
