package com.example.service.statistic;



import com.example.entity.UserTransaction;

import java.util.List;

public interface StatisticService {
    List<Double> getTransactionsIn7Day();
    List<Double> getTransactionsIn12Months();
    List<Integer> getTripIn12Months();
    List<Double> getTransactionsIn3Years();
    Double getTodayTransactionRevenue();
    Double getThisMonthTransactionRevenue();
    Double getThisYearTransactionRevenue();
    List<UserTransaction> getTodayTransaction();
    List<UserTransaction> getMonthTransaction();
    List<UserTransaction> getYearTransaction();
}
