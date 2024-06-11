package com.example.service.transaction;

import com.example.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionStatisticService {
    Map<Integer, Double> getTransactionsIn7Day();
    Map<Integer, Double> getTransactionsIn12Months();
    Map<Integer, Double> getTransactionsIn3Years();
    Double getTodayTransactionRevenue();
    Double getThisMonthTransactionRevenue();
    Double getThisYearTransactionRevenue();
    List<Transaction> getTodayTransaction();
    List<Transaction> getMonthTransaction();
    List<Transaction> getYearTransaction();
}
