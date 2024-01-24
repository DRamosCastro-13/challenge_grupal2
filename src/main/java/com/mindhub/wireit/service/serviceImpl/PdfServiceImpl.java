package com.mindhub.wireit.service.serviceImpl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mindhub.wireit.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PdfServiceImpl implements PdfService {
    @Override
    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Crear una tabla con dos celdas
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        // Celda para la imagen
        PdfPCell cellImage = new PdfPCell();
        Image img = Image.getInstance("https://i.imgur.com/b9ZhApT.png");
        img.scaleToFit(PageSize.A4.getWidth() - 350, PageSize.A4.getHeight() - 300);
        cellImage.addElement(img);
        cellImage.setBorder(Rectangle.NO_BORDER);
        table.addCell(cellImage);

        // Celda para la fecha de facturación
        PdfPCell cellFecha = new PdfPCell();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaFacturacion = "Fecha de Facturación: " + dateFormat.format(new Date());
        Paragraph fechaParagraph = new Paragraph(fechaFacturacion);
        fechaParagraph.setAlignment(Element.ALIGN_RIGHT);

        // Alineación vertical de la celda
        cellFecha.addElement(fechaParagraph);
        cellFecha.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellFecha.setBorder(Rectangle.NO_BORDER);
        table.addCell(cellFecha);

        // Establecer la altura de las celdas
        cellImage.setFixedHeight(Math.max(cellImage.getHeight(), cellFecha.getHeight()));
        cellFecha.setFixedHeight(Math.max(cellImage.getHeight(), cellFecha.getHeight()));

        // Agregar la tabla al documento
        document.add(table);

        document.close();

    }
}
