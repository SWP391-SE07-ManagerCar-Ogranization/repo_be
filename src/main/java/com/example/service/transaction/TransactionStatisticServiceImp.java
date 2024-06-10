package com.example.service.transaction;

import com.example.entity.Transaction;
import com.example.repository.TransactionStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class TransactionStatisticServiceImp implements TransactionStatisticService {
    @Autowired
    TransactionStatisticRepository transactionStatisticRepository;

    @Override
    public Map<Integer, Double> getTransactionsIn7Day() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        Map<Integer, Double> revenue = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            double total = 0;
            List<Transaction> result = transactionStatisticRepository.getTransactionByDate(calendar.getTime());
            for (int j = 0; j < result.size(); j++) {
                total += result.get(j).getAmount();
            }
            revenue.put(i+1,total);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return revenue;
    }

    @Override
    public Map<Integer, Double> getTransactionsIn12Months() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -11);
        Map<Integer, Double> revenue = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            double total = 0;
            List<Transaction> result = transactionStatisticRepository.getTransactionByMonth(calendar.getTime());
            for (int j = 0; j < result.size(); j++) {
                total += result.get(j).getAmount();
            }
            revenue.put(i+1,total);
            calendar.add(Calendar.MONTH, 1);
        }
        return revenue;
    }

    @Override
    public Map<Integer, Double> getTransactionsIn3Years() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -2);
        Map<Integer, Double> revenue = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            double total = 0;
            List<Transaction> result = transactionStatisticRepository.getTransactionByYear(calendar.getTime());
            for (int j = 0; j < result.size(); j++) {
                total += result.get(j).getAmount();
            }
            revenue.put(i+1,total);
            calendar.add(Calendar.YEAR, 1);
        }
        return revenue;
    }

    @Override
    public Double getTodayTransactionRevenue() {
        Calendar calendar = Calendar.getInstance();
        List<Transaction> result = transactionStatisticRepository.getTransactionByDate(calendar.getTime());
        double revenue = 0;
        for (int i = 0; i < result.size(); i++) {
            revenue += result.get(i).getAmount();
        }
        return revenue;
    }

    @Override
    public Double getThisMonthTransactionRevenue() {
        Calendar calendar = Calendar.getInstance();
        List<Transaction> result = transactionStatisticRepository.getTransactionByMonth(calendar.getTime());
        double revenue = 0;
        for (int i = 0; i < result.size(); i++) {
            revenue += result.get(i).getAmount();
        }
        return revenue;
    }

    @Override
    public Double getThisYearTransactionRevenue() {
        Calendar calendar = Calendar.getInstance();
        List<Transaction> result = transactionStatisticRepository.getTransactionByYear(calendar.getTime());
        double revenue = 0;
        for (int i = 0; i < result.size(); i++) {
            revenue += result.get(i).getAmount();
        }
        return revenue;
    }

    @Override
    public List<Transaction> getTodayTransaction() {
        Calendar calendar = Calendar.getInstance();
        return transactionStatisticRepository.getTransactionByDate(calendar.getTime());
    }

    @Override
    public List<Transaction> getMonthTransaction() {
        Calendar calendar = Calendar.getInstance();
        return transactionStatisticRepository.getTransactionByMonth(calendar.getTime());
    }

    @Override
    public List<Transaction> getYearTransaction() {
        Calendar calendar = Calendar.getInstance();
        return transactionStatisticRepository.getTransactionByYear(calendar.getTime());
    }
}
