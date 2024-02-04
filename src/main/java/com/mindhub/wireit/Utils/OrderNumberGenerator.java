package com.mindhub.wireit.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.mindhub.wireit.repositories.PurchaseOrderRepository;

public class OrderNumberGenerator {

    private static int numeroOrden = 1;
    private static String ultimoMes = "";

    public static String OrderNumberGenerator(PurchaseOrderRepository purchaseOrderRepository) {

        LocalDate actualDate = LocalDate.now();
        String formatDate = actualDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));

        do {
            if (!formatDate.equals(ultimoMes)) {
                numeroOrden = 1;
                ultimoMes = formatDate;
            }

            String ordenNumberFormar = String.format("%04d", numeroOrden);

            String orderNumber = formatDate + "/" + ordenNumberFormar;

            // Verifica la existencia del número de orden en el repositorio
            boolean exists = purchaseOrderRepository.existsByOrderNumber(orderNumber);

            if (!exists) {
                // Si no existe, incrementa el contador y devuelve el número de orden generado
                numeroOrden++;
                return orderNumber;
            }

            // Si existe, incrementa el contador y vuelve a intentar generar un nuevo número de orden
            numeroOrden++;
        } while (true); // Bucle infinito (se romperá cuando se encuentre un número de orden único)
    }
}
