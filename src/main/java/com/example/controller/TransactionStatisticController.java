package com.example.controller;

import com.example.service.transaction.TransactionStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionStatisticController {
    @Autowired
    TransactionStatisticService transactionStatisticService;

    @GetMapping("/public/transaction/getTransactionsInLast7Days")
    public Map<Integer, Double> getTransactionsInLast7Days(){
        return transactionStatisticService.getTransactionsIn7Day();
    }

    @GetMapping("/public/transaction/getTransactionsInLast12Months")
    public Map<Integer, Double> getTransactionsInLast12Months(){
        return transactionStatisticService.getTransactionsIn12Months();
    }

    @GetMapping("/public/transaction/getTransactionsInLast3Years")
    public Map<Integer, Double> getTransactionsInLast3Years(){
        return transactionStatisticService.getTransactionsIn3Years();
    }

    @GetMapping("/public/transaction/getTodayTransactionRevenue")
    public Double getTodayTransactionRevenue(){
        return transactionStatisticService.getTodayTransactionRevenue();
    }

    @GetMapping("/public/transaction/getThisMonthTransactionRevenue")
    public Double getThisMonthTransactionRevenue(){
        return transactionStatisticService.getThisMonthTransactionRevenue();
    }

    @GetMapping("/public/transaction/getThisYearTransactionRevenue")
    public Double getThisYearTransactionRevenue(){
        return transactionStatisticService.getThisYearTransactionRevenue();
    }

    @GetMapping("/public/transaction/getTodayTrip")
    public int getTodayTrip(){
        return transactionStatisticService.getTodayTransaction().size();
    }

    @GetMapping("/public/transaction/getThisMonthTrip")
    public int getThisMonthTrip(){
        return transactionStatisticService.getMonthTransaction().size();
    }

    @GetMapping("/public/transaction/getThisYearTrip")
    public int getThisYearTrip(){
        return transactionStatisticService.getYearTransaction().size();
    }
}
