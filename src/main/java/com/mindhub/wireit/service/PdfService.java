package com.mindhub.wireit.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface PdfService {

  void export(HttpServletResponse response, String orderNumber) throws IOException;
}
