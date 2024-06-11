package com.example.service.statistic;



import com.example.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface StatisticService {
    List<Double> getTransactionsIn7Day();
    List<Double> getTransactionsIn12Months();
    List<Integer> getTripIn12Months();
    List<Double> getTransactionsIn3Years();
    Double getTodayTransactionRevenue();
    Double getThisMonthTransactionRevenue();
    Double getThisYearTransactionRevenue();
    List<Transaction> getTodayTransaction();
    List<Transaction> getMonthTransaction();
    List<Transaction> getYearTransaction();
}
