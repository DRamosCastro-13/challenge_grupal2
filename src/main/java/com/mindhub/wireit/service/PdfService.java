package com.mindhub.wireit.service;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface PdfService {

  ResponseEntity<String> generatePDF(String orderNumber, HttpServletResponse response, Authentication authentication) throws IOException;
  ByteArrayOutputStream export(HttpServletResponse response, String orderNumber) throws IOException;
}
