package com.example.service.statistic;

import com.example.entity.Transaction;
import com.example.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class StatisticServiceImp implements StatisticService {
    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public List<Double> getTransactionsIn7Day() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -6);
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            double total = 0;
            List<Transaction> trans = statisticRepository.getTransactionByDate(calendar.getTime());
            for (int j = 0; j < trans.size(); j++) {
                total += trans.get(j).getAmount();
            }
            result.add(total);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    @Override
    public List<Double> getTransactionsIn12Months() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -11);
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            double total = 0;
            List<Transaction> trans = statisticRepository.getTransactionByMonth(calendar.getTime());
            for (int j = 0; j < trans.size(); j++) {
                total += trans.get(j).getAmount();
            }
            result.add(total);
            calendar.add(Calendar.MONTH, 1);
        }
        return result;
    }

    @Override
    public List<Integer> getTripIn12Months() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -11);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int total = 0;
            List<Transaction> trans = statisticRepository.getTransactionByMonth(calendar.getTime());
            for (int j = 0; j < trans.size(); j++) {
                total += 1;
            }
            result.add(total);
            calendar.add(Calendar.MONTH, 1);
        }
        return result;
    }

    @Override
    public List<Double> getTransactionsIn3Years() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -2);
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            double total = 0;
            List<Transaction> trans = statisticRepository.getTransactionByYear(calendar.getTime());
            for (int j = 0; j < trans.size(); j++) {
                total += trans.get(j).getAmount();
            }
            result.add(total);
            calendar.add(Calendar.YEAR, 1);
        }
        return result;
    }

    @Override
    public Double getTodayTransactionRevenue() {
        Calendar calendar = Calendar.getInstance();
        List<Transaction> result = statisticRepository.getTransactionByDate(calendar.getTime());
        double revenue = 0;
        for (int i = 0; i < result.size(); i++) {
            revenue += result.get(i).getAmount();
        }
        return revenue;
    }

    @Override
    public Double getThisMonthTransactionRevenue() {
        Calendar calendar = Calendar.getInstance();
        List<Transaction> result = statisticRepository.getTransactionByMonth(calendar.getTime());
        double revenue = 0;
        for (int i = 0; i < result.size(); i++) {
            revenue += result.get(i).getAmount();
        }
        return revenue;
    }

    @Override
    public Double getThisYearTransactionRevenue() {
        Calendar calendar = Calendar.getInstance();
        List<Transaction> result = statisticRepository.getTransactionByYear(calendar.getTime());
        double revenue = 0;
        for (int i = 0; i < result.size(); i++) {
            revenue += result.get(i).getAmount();
        }
        return revenue;
    }

    @Override
    public List<Transaction> getTodayTransaction() {
        Calendar calendar = Calendar.getInstance();
        return statisticRepository.getTransactionByDate(calendar.getTime());
    }

    @Override
    public List<Transaction> getMonthTransaction() {
        Calendar calendar = Calendar.getInstance();
        return statisticRepository.getTransactionByMonth(calendar.getTime());
    }

    @Override
    public List<Transaction> getYearTransaction() {
        Calendar calendar = Calendar.getInstance();
        return statisticRepository.getTransactionByYear(calendar.getTime());
    }
}
