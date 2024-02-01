package com.mindhub.wireit.service.serviceImpl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mindhub.wireit.models.*;
import com.mindhub.wireit.repositories.PurchaseOrderRepository;
import com.mindhub.wireit.service.ClientService;
import com.mindhub.wireit.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PdfServiceImpl implements PdfService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private ClientService clientService;

    @Override
    public ResponseEntity<String> generatePDF(String orderNumber, HttpServletResponse response, Authentication authentication) throws IOException {

        if (authentication == null || authentication.getName() == null) {
            return new ResponseEntity<>("You need to be logged in to create PDF of your order.", HttpStatus.FORBIDDEN);
        }
        // Obtener el cliente autenticado
        Client client = clientService.getAuthenticatedClient(authentication.getName());

        if (client == null) {
            return new ResponseEntity<>("You need to Sing up in the plataform to create PDF of your order.", HttpStatus.FORBIDDEN);
        }

        List<PurchaseOrder> purchaseOrders = client.getPurchaseOrders();

        for (PurchaseOrder po : purchaseOrders) {
            if (po.getOrderNumber().equals(orderNumber)) {
                // Verificar si el correo electrónico del cliente autenticado coincide con el correo asociado a la orden
                if (authentication.getName().equals(po.getClient().getEmail())) {
                    response.setContentType("application/pdf");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
                    String currentDateTime = dateFormat.format(new Date());
                    String headerKey = "Content-Disposition";
                    String headerValue = "attachment; filename=order_" + currentDateTime + ".pdf";
                    response.setHeader(headerKey, headerValue);
                    export(response, orderNumber);
                    return new ResponseEntity<>("PDF generated successfully.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Unauthorized: Email mismatch for the specified order.", HttpStatus.UNAUTHORIZED);
                }
            }
        }
        // Si llega aquí, significa que no se encontró la orden con el número especificado
        return new ResponseEntity<>("Order not found.", HttpStatus.NOT_FOUND);
    }


    //metodo para el precio
    private String formatPrice(double price) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormatter.format(price);
    }


    @Override
    public ByteArrayOutputStream export(HttpServletResponse response, String orderNumber) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findByOrderNumber(orderNumber);

            PdfWriter.getInstance(document, byteArrayOutputStream);
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
            fontTable.setSize(12);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String fechaFacturacion = "Ordered on: " + dateFormat.format(new Date());
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

            // Sección 2: Detalles del cliente
            Set<Client> clients = Collections.singleton(purchaseOrder.getClient());

            for (Client client : clients) {
                Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                boldFont.setSize(13);

                // Crear párrafos con información del cliente
                Paragraph addressParagraph = new Paragraph(String.format("Address: %s", client.getAddresses().stream().map(Address::getAddress).collect(Collectors.joining(", "))), boldFont);
                addressParagraph.setSpacingAfter(5f);
                Paragraph countryParagraph = new Paragraph(String.format("Country: %s", client.getAddresses().stream().map(Address::getCountry).collect(Collectors.joining(", "))), boldFont);
                countryParagraph.setSpacingAfter(5f);
                Paragraph provinceParagraph = new Paragraph(String.format("Province: %s", client.getAddresses().stream().map(Address::getProvince).collect(Collectors.joining(", "))), boldFont);
                provinceParagraph.setSpacingAfter(5f);
                Paragraph cityParagraph = new Paragraph(String.format("City: %s", client.getAddresses().stream().map(Address::getCity).collect(Collectors.joining(", "))), boldFont);
                cityParagraph.setSpacingAfter(5f);
                int zipCode = client.getAddresses().stream().mapToInt(Address::getZipCode).sum();
                Paragraph zipCodeParagraph = new Paragraph(String.format("Zip Code: %d", zipCode), boldFont);
                zipCodeParagraph.setSpacingAfter(5f);

                // Añadir los párrafos al documento

                document.add(addressParagraph);
                document.add(countryParagraph);
                document.add(provinceParagraph);
                document.add(cityParagraph);
                document.add(zipCodeParagraph);
            }

            document.add(new Paragraph(" "));

            // Sección 3: Detalles de la orden
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTitle.setSize(16);

            Paragraph order = new Paragraph("Purchase Order: " + purchaseOrder.getOrderNumber(), fontTitle);
            order.setAlignment(Paragraph.ALIGN_MIDDLE);
            order.setAlignment(Element.ALIGN_MIDDLE | Element.ALIGN_CENTER);
            order.setSpacingAfter(15f);
            document.add(order);

            document.add(new Paragraph(" "));

            //tabla de facturación con sus precios y productos
            PdfPTable tableItems = new PdfPTable(3);
            tableItems.setWidthPercentage(100);

            // Crear celda para "Items Ordered" con borde
            PdfPCell cellItemsHeader = new PdfPCell(new Paragraph("Items Ordered", fontTable));
            cellItemsHeader.setBorder(Rectangle.BOTTOM);
            cellItemsHeader.setPaddingBottom(10f);
            tableItems.addCell(cellItemsHeader);

            // Crear celda para "quantity" con borde
            PdfPCell cellQuantityHeader = new PdfPCell(new Paragraph("Quantity", fontTable));
            cellQuantityHeader.setBorder(Rectangle.BOTTOM);
            cellQuantityHeader.setPaddingBottom(10f);
            cellQuantityHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableItems.addCell(cellQuantityHeader);

            // Crear celda para "Price" con borde
            PdfPCell cellPriceHeader = new PdfPCell(new Paragraph("Price", fontTable));
            cellPriceHeader.setBorder(Rectangle.BOTTOM);
            cellPriceHeader.setPaddingBottom(10f);
            cellPriceHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableItems.addCell(cellPriceHeader);

            List<ProductOrder> productOrders = purchaseOrder.getProductOrders();
            for (ProductOrder productOrder : productOrders) {
                Product product = productOrder.getProduct();
                String itemName = product.getName();
                double itemPrice = product.getPrice();
                int quantity = productOrder.getQuantity();

                // Crear celda para el nombre del producto con borde
                PdfPCell itemCell = new PdfPCell(new Phrase(itemName, fontTable));
                itemCell.setBorder(Rectangle.BOTTOM);
                itemCell.setPaddingBottom(5f);
                tableItems.addCell(itemCell);

                // Crear celda para la cantidad del producto con borde
                PdfPCell quantityCell = new PdfPCell(new Phrase(String.valueOf(quantity), fontTable));
                quantityCell.setBorder(Rectangle.BOTTOM);
                quantityCell.setPaddingBottom(5f);
                quantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);  // Alinea al centro
                tableItems.addCell(quantityCell);

                // Crear celda para el precio del producto con borde
                PdfPCell priceCell = new PdfPCell(new Phrase(formatPrice(itemPrice), fontTable));
                priceCell.setBorder(Rectangle.BOTTOM);
                priceCell.setPaddingBottom(5f);
                priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);  // Alinea a la derecha
                tableItems.addCell(priceCell);
            }

            document.add(tableItems);

            document.add(new Paragraph(" "));

            Paragraph totalParagraph = new Paragraph();

            Font fontTotalAmount = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTotalAmount.setSize(13);
            double totalAmountValue = purchaseOrder.getTotalAmount();

            totalParagraph.add(new Phrase("SubTotal Amount: " + formatPrice(totalAmountValue)));
            totalParagraph.add(Chunk.NEWLINE);

            totalParagraph.add(new Phrase("Discount: " + purchaseOrder.getDiscount() + "%"));
            totalParagraph.add(Chunk.NEWLINE);

            totalParagraph.add(new Phrase("Total Amount: " + formatPrice(purchaseOrder.getTotalToPay()), fontTotalAmount));

            totalParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(totalParagraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return byteArrayOutputStream;
    }
}

