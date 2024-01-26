package com.mindhub.wireit.service.serviceImpl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mindhub.wireit.models.*;
import com.mindhub.wireit.repositories.PurchaseOrderRepository;
import com.mindhub.wireit.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PdfServiceImpl implements PdfService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public void export(HttpServletResponse response, String orderNumber) throws IOException {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByOrderNumber(orderNumber);

        if (purchaseOrder != null) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Sección 1: Tabla con imagen y fecha de facturación
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            PdfPCell cellImage = new PdfPCell();
            Image img = Image.getInstance("https://i.imgur.com/b9ZhApT.png");
            img.scaleToFit(PageSize.A4.getWidth() - 350, PageSize.A4.getHeight() - 300);
            cellImage.addElement(img);
            cellImage.setBorder(Rectangle.NO_BORDER);
            table.addCell(cellImage);

            PdfPCell cellFecha = new PdfPCell();
            Font fontTable = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTable.setSize(16);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String fechaFacturacion = "Invoice date: " + dateFormat.format(new Date());
            Paragraph fechaParagraph = new Paragraph(fechaFacturacion, fontTable);
            fechaParagraph.setAlignment(Element.ALIGN_RIGHT);
            cellFecha.addElement(fechaParagraph);
            cellFecha.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellFecha.setBorder(Rectangle.NO_BORDER);
            table.addCell(cellFecha);

            cellImage.setFixedHeight(Math.max(cellImage.getHeight(), cellFecha.getHeight()));
            cellFecha.setFixedHeight(Math.max(cellImage.getHeight(), cellFecha.getHeight()));

            document.add(table);

            // Añadir espacio entre secciones
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // Sección 2: Detalles de la orden
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTitle.setSize(18);

            Paragraph order = new Paragraph("Purchase Order: " + purchaseOrder.getOrderNumber(), fontTitle);
            order.setAlignment(Paragraph.ALIGN_MIDDLE);
            order.setSpacingAfter(15f);
            document.add(order);

            Font fontTotalAmount = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTotalAmount.setSize(16);
            double totalAmountValue = purchaseOrder.getTotalAmount();

            Paragraph totalToPay = new Paragraph("Amount to pay: $"+purchaseOrder.getTotalToPay(),fontTotalAmount);
            Paragraph totalAmount = new Paragraph("Total Amount: $" + totalAmountValue);
            totalAmount.setAlignment(Paragraph.ALIGN_LEFT);
            totalAmount.setSpacingAfter(15f);
            totalToPay.setAlignment(Paragraph.ALIGN_LEFT);
            totalToPay.setSpacingAfter(15f);
            document.add(totalAmount);
            document.add(totalToPay);

            //tabla de facturación con sus precios y productos
            PdfPTable tableItems = new PdfPTable(3);
            tableItems.setWidthPercentage(100);

            // Crear celda para "Items Ordered" con borde
            PdfPCell cellItemsHeader = new PdfPCell();
            cellItemsHeader.addElement(new Paragraph("Items Ordered", fontTable));
            cellItemsHeader.setBorder(Rectangle.BOTTOM);
            cellItemsHeader.setPaddingBottom(10f);
            tableItems.addCell(cellItemsHeader);

            PdfPCell cellQuantityHeader = new PdfPCell();
            cellQuantityHeader.addElement(new Paragraph("Quantity", fontTable));
            cellQuantityHeader.setBorder(Rectangle.BOTTOM);
            cellQuantityHeader.setPaddingBottom(10f);
            tableItems.addCell(cellQuantityHeader);

            // Crear celda para "Price" con borde
            PdfPCell cellPriceHeader = new PdfPCell();
            cellPriceHeader.addElement(new Paragraph("Price", fontTable));
            cellPriceHeader.setBorder(Rectangle.BOTTOM);
            cellPriceHeader.setPaddingBottom(10f);
            tableItems.addCell(cellPriceHeader);

            List<ProductOrder> productOrders = purchaseOrder.getProductOrders();
            for (ProductOrder productOrder : productOrders) {
                Product product = productOrder.getProduct();
                String itemName = product.getName();
                double itemPrice = product.getPrice();
                int quantity = productOrder.getQuantity();
                // Crear celda para el nombre del producto con borde
                PdfPCell itemCell = new PdfPCell();
                itemCell.addElement(new Paragraph(itemName, fontTable));
                itemCell.setBorder(Rectangle.NO_BORDER);
                itemCell.setPaddingBottom(5f);
                tableItems.addCell(itemCell);

                PdfPCell quantityCell = new PdfPCell();
                quantityCell.addElement(new Paragraph(String.valueOf(quantity), fontTable));
                quantityCell.setBorder(Rectangle.NO_BORDER);
                quantityCell.setPaddingBottom(5f);
                tableItems.addCell(quantityCell);

                // Crear celda para el precio del producto con borde
                PdfPCell priceCell = new PdfPCell();
                priceCell.addElement(new Paragraph("$" + itemPrice, fontTable));
                priceCell.setBorder(Rectangle.NO_BORDER);
                priceCell.setPaddingBottom(5f);
                tableItems.addCell(priceCell);
            }

            document.add(tableItems);

            // Añadir espacio entre secciones
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // Sección 3: Detalles del cliente
            Set<Client> clients = Collections.singleton(purchaseOrder.getClient());

            for (Client client : clients) {
                Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                boldFont.setSize(16);

                // Crear párrafos con información del cliente
                Paragraph clientParagraph = new Paragraph(String.format("Client: %s %s", client.getFirstName(), client.getLastName()), boldFont);
                clientParagraph.setSpacingAfter(10f); // Ajusta el valor según tus necesidades
                Paragraph emailParagraph = new Paragraph(String.format("Email: %s", client.getEmail()), boldFont);
                emailParagraph.setSpacingAfter(10f);
                Paragraph phoneParagraph = new Paragraph(String.format("Phone: %s", client.getAddresses().stream().map(Address::getPhone).collect(Collectors.joining(", "))), boldFont);
                phoneParagraph.setSpacingAfter(10f);
                Paragraph addressParagraph = new Paragraph(String.format("Address: %s", client.getAddresses().stream().map(Address::getAddress).collect(Collectors.joining(", "))), boldFont);
                addressParagraph.setSpacingAfter(10f);
                Paragraph countryParagraph = new Paragraph(String.format("Country: %s", client.getAddresses().stream().map(Address::getCountry).collect(Collectors.joining(", "))), boldFont);
                countryParagraph.setSpacingAfter(10f);
                Paragraph provinceParagraph = new Paragraph(String.format("Province: %s", client.getAddresses().stream().map(Address::getProvince).collect(Collectors.joining(", "))), boldFont);
                provinceParagraph.setSpacingAfter(10f);
                Paragraph cityParagraph = new Paragraph(String.format("City: %s", client.getAddresses().stream().map(Address::getCity).collect(Collectors.joining(", "))), boldFont);
                cityParagraph.setSpacingAfter(10f);
                int zipCode = client.getAddresses().stream().mapToInt(Address::getZipCode).sum();
                Paragraph zipCodeParagraph = new Paragraph(String.format("Zip Code: %d", zipCode), boldFont);
                zipCodeParagraph.setSpacingAfter(10f);

                // Añadir los párrafos al documento
                document.add(clientParagraph);
                document.add(emailParagraph);
                document.add(phoneParagraph);
                document.add(addressParagraph);
                document.add(countryParagraph);
                document.add(provinceParagraph);
                document.add(cityParagraph);
                document.add(zipCodeParagraph);
            }

            document.close();
        }
    }
}

