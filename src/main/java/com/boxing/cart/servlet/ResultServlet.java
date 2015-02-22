package com.boxing.cart.servlet;

import com.boxing.cart.system.Cart;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;

public class ResultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inputInformation = formatInputInformation(req);
        String totalPrice;
        
        try {
            totalPrice = calculateTotalPrice(inputInformation);
        } catch (ParseException e) {
            throw new ServletException(e);
        }

        ServletOutputStream outputStream = resp.getOutputStream();
        resp.setContentType("text/html;charset=UTF-8");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write("<div>The total price is " + totalPrice + ".</div>");
        outputStreamWriter.write("<a href=\"index\">Back</a>");
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }

    private String formatInputInformation(HttpServletRequest req) {
        String discountInformation = req.getParameter("discount_information");
        String itemInformation = req.getParameter("item_information");
        String settlementInformation = req.getParameter("settlement_information");
        String couponInformation = req.getParameter("coupon_information");

        discountInformation = discountInformation.replaceAll(",","\n");
        itemInformation = itemInformation.replaceAll(",","\n");
        couponInformation = couponInformation.replaceAll(",","\n");

        return discountInformation + "\n\n" + itemInformation + "\n\n" + settlementInformation + "\n" + couponInformation;
    }

    private String calculateTotalPrice(String inputInformation) throws ParseException {
        Cart cart = new Cart();
        return cart.showTotalPrice(inputInformation);
    }
}
