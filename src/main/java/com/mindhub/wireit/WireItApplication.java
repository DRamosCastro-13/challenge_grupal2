package com.mindhub.wireit;

import com.mindhub.wireit.Utils.OrderNumberGenerator;
import com.mindhub.wireit.models.*;
import com.mindhub.wireit.models.enums.OrderStatus;
import com.mindhub.wireit.models.enums.ProductCategory;
import com.mindhub.wireit.models.enums.Role;
import com.mindhub.wireit.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
@PropertySource("classpath:.env")
public class WireItApplication {

	public static void main(String[] args) {
		SpringApplication.run(WireItApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean // notacion para ejecutarlo apenas inicia la app
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AddressRepository addresRepository,
									  PurchaseOrderRepository purchaseOrderRepository,
									  ProductOrderRepository productOrderRepository,
									  ProductRepository productRepository) {
		return args -> {
			/*Client client = new Client("Melba","Morel","alvarosop23@gmail.com",25524320,passwordEncoder.encode("1234"));
			client.setRole(Role.ADMIN);
			clientRepository.save(client);


			Address address = new Address("Av.Hola","+5491120302020","Argentina","Buenos Aires","CABA",1439);
			client.addAddress(address);

			addresRepository.save(address);

			Product product = new Product("Lavadora","MadMax","https://d1pjg4o0tbonat.cloudfront.net/content/dam/midea-aem/cl/lavanderia/lavadoras/lavadora-carga-frontal-8kg-mf100w80-w/W_2674x4011_2.jpg/jcr:content/renditions/cq5dam.web.5000.5000.jpeg","Lavadora blanca de hasta 50kg MadMax, la mejor calidad", ProductCategory.MONITOR,20000,0,10);
			Product product2 = new Product("Lavadora1","MadMax","https://d1pjg4o0tbonat.cloudfront.net/content/dam/midea-aem/cl/lavanderia/lavadoras/lavadora-carga-frontal-8kg-mf100w80-w/W_2674x4011_2.jpg/jcr:content/renditions/cq5dam.web.5000.5000.jpeg","Lavadora blanca de hasta 50kg MadMax, la mejor calidad", ProductCategory.MONITOR,20000,0,10);
			Product product3 = new Product("Lavadora2","MadMax","https://d1pjg4o0tbonat.cloudfront.net/content/dam/midea-aem/cl/lavanderia/lavadoras/lavadora-carga-frontal-8kg-mf100w80-w/W_2674x4011_2.jpg/jcr:content/renditions/cq5dam.web.5000.5000.jpeg","Lavadora blanca de hasta 50kg MadMax, la mejor calidad", ProductCategory.MONITOR,20000,0,5);
			Product product4 = new Product("Lavadora3","MadMax","https://d1pjg4o0tbonat.cloudfront.net/content/dam/midea-aem/cl/lavanderia/lavadoras/lavadora-carga-frontal-8kg-mf100w80-w/W_2674x4011_2.jpg/jcr:content/renditions/cq5dam.web.5000.5000.jpeg","Lavadora blanca de hasta 50kg MadMax, la mejor calidad", ProductCategory.MONITOR,20000,0,10);
			Product product5 = new Product("Lavadora4","MadMax","https://d1pjg4o0tbonat.cloudfront.net/content/dam/midea-aem/cl/lavanderia/lavadoras/lavadora-carga-frontal-8kg-mf100w80-w/W_2674x4011_2.jpg/jcr:content/renditions/cq5dam.web.5000.5000.jpeg","Lavadora blanca de hasta 50kg MadMax, la mejor calidad", ProductCategory.MONITOR,20000,0,0);
			productRepository.save(product);
			productRepository.save(product2);
			productRepository.save(product3);
			productRepository.save(product4);
			productRepository.save(product5);

			ProductOrder productOrder = new ProductOrder((byte) 10);
			ProductOrder productOrder2 = new ProductOrder((byte) 1);
			ProductOrder productOrder3 = new ProductOrder((byte) 2);
			ProductOrder productOrder4 = new ProductOrder((byte) 6);
			ProductOrder productOrder5 = new ProductOrder((byte) 22);

			PurchaseOrder purchaseOrder = new PurchaseOrder(OrderNumberGenerator.OrderNumberGenerator(), LocalDate.now(),"Casa amarilla", OrderStatus.CREATED,0,(product.getPrice()*productOrder.getQuantity()));

			// Establecer relaciones
			productOrder.setProduct(product);
			productOrder2.setProduct(product2);
			productOrder3.setProduct(product3);
			productOrder4.setProduct(product4);
			productOrder5.setProduct(product5);
			purchaseOrder.addProductOrder(productOrder);
			purchaseOrder.addProductOrder(productOrder2);
			purchaseOrder.addProductOrder(productOrder3);
			purchaseOrder.addProductOrder(productOrder4);
			purchaseOrder.addProductOrder(productOrder5);

			client.addOrders(purchaseOrder);


			// Guardar entidades
			purchaseOrderRepository.save(purchaseOrder);
			productOrderRepository.save(productOrder);
			productOrderRepository.save(productOrder2);
			productOrderRepository.save(productOrder3);
			productOrderRepository.save(productOrder4);
			productOrderRepository.save(productOrder5);
			clientRepository.save(client);

			System.out.println("llegue al final");*/
		};

	}
}
