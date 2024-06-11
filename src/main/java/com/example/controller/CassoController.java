package com.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment/casso")
public class CassoController {

    private static final int ORDER_MONEY = 100000;
    private static final int ACCEPTABLE_DIFFERENCE = 10000;
    private static final String MEMO_PREFIX = "DH";
    private static final String HEADER_SECURE_TOKEN = "eogrBiWqaq";

    @PostMapping("/handler")
    public ResponseEntity<String> paymentHandler(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        if (requestBody.isEmpty()) {
            return new ResponseEntity<>("Request thiếu body", HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> jsonBody = (Map<String, Object>) requestBody.get("data");
        if (jsonBody == null || (int) requestBody.get("error") != 0) {
            return new ResponseEntity<>("Có lỗi xay ra ở phía Casso", HttpStatus.BAD_REQUEST);
        }

        String secureToken = request.getHeader("Secure-Token");
        if (!HEADER_SECURE_TOKEN.equals(secureToken)) {
            return new ResponseEntity<>("Thiếu Secure Token hoặc secure token không khớp", HttpStatus.FORBIDDEN);
        }

        for (Map.Entry<String, Object> entry : jsonBody.entrySet()) {
            Map<String, Object> transaction = (Map<String, Object>) entry.getValue();
            String description = (String) transaction.get("description");
            Integer orderId = parseOrderId(description);

            if (orderId == null) {
                return new ResponseEntity<>("Không nhận dạng được order_id từ nội dung chuyển tiền: " + description, HttpStatus.BAD_REQUEST);
            }

            int paid = (int) transaction.get("amount");
            String orderNote = String.format("Casso thông báo nhận <b>%d</b> VND, nội dung <B>%s</B> chuyển vào <b>STK %s</b>",
                    paid, description, transaction.get("bank_sub_acc_id"));

            if (paid < ORDER_MONEY - ACCEPTABLE_DIFFERENCE) {
                return new ResponseEntity<>(orderNote + ". Trạng thái đơn hàng đã được chuyển từ Tạm giữ sang Thanh toán thiếu.", HttpStatus.OK);
            } else if (paid <= ORDER_MONEY + ACCEPTABLE_DIFFERENCE) {
                return new ResponseEntity<>(orderNote + ". Trạng thái đơn hàng đã được chuyển từ Tạm giữ sang Đã thanh toán.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(orderNote + ". Trạng thái đơn hàng đã được chuyển từ Tạm giữ sang Thanh toán dư.", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Xử lý hoàn tất", HttpStatus.OK);
    }

    private Integer parseOrderId(String description) {
        String regex = MEMO_PREFIX + "\\d+";
        if (description.matches(".*" + regex + ".*")) {
            String orderCode = description.replaceAll("[^\\d]", "");
            return Integer.parseInt(orderCode);
        }
        return null;
    }
}

