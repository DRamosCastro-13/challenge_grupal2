package com.mindhub.wireit.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderNumberGenerator {

    private static int numeroOrden = 1;
    private static String ultimoMes = "";

    public static String OrderNumberGenerator(){

        LocalDate actualDate = LocalDate.now();
        String formatDate = actualDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));

        if (!formatDate.equals(ultimoMes)) {
            numeroOrden = 1;
            ultimoMes = formatDate;
        }

        String ordenNumberFormar = String.format("%04d", numeroOrden);

        String OrderNumberGenerator = formatDate + "/" + ordenNumberFormar;
        numeroOrden++;

    return OrderNumberGenerator;
    }
}
