package com.mindhub.wireit;

import com.mindhub.wireit.models.*;
import com.mindhub.wireit.models.enums.OrderStatus;
import com.mindhub.wireit.models.enums.ProductCategory;
import com.mindhub.wireit.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

@SpringBootApplication
@PropertySource("classpath:.env")
public class WireItApplication {

	public static void main(String[] args) {
		SpringApplication.run(WireItApplication.class, args);
	}

	@Bean // notacion para ejecutarlo apenas inicia la app
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AddressRepository addresRepository,
									  PurchaseOrderRepository purchaseOrderRepository,
									  ProductOrderRepository productOrderRepository,
									  ProductRepository productRepository,
									  SupplierRepository supplierRepository) {
		return args -> {
			Client client = new Client("Melba","Morel","melba@mindhub.com",25524320);
			clientRepository.save(client);
			System.out.println(client);

			Address address = new Address("Av.Hola","+5491120302020","Argentina","Buenos Aires","CABA",1439);
			client.addAddress(address);

			addresRepository.save(address);

			Product product = new Product("Lavadora","MadMax","https://d1pjg4o0tbonat.cloudfront.net/content/dam/midea-aem/cl/lavanderia/lavadoras/lavadora-carga-frontal-8kg-mf100w80-w/W_2674x4011_2.jpg/jcr:content/renditions/cq5dam.web.5000.5000.jpeg","Lavadora blanca de hasta 50kg MadMax, la mejor calidad", ProductCategory.MONITOR,20000,0);
			productRepository.save(product);

			Supplier supplier = new Supplier("MadMax",(short)20);
			supplier.addProduct(product);
			supplierRepository.save(supplier);

			ProductOrder productOrder = new ProductOrder((byte) 10);

			PurchaseOrder purchaseOrder = new PurchaseOrder("01/2024/0001", LocalDate.now(),"Casa amarilla", OrderStatus.CREATED,0,1000);

			// Establecer relaciones
			productOrder.setProduct(product);
			purchaseOrder.addProductOrder(productOrder);

			client.addOrders(purchaseOrder);


			// Guardar entidades
			purchaseOrderRepository.save(purchaseOrder);
			productOrderRepository.save(productOrder);
			clientRepository.save(client);
			System.out.println("llegue al final");
		};
	}
}
