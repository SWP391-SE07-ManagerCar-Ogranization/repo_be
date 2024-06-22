package com.example.controller;


import com.example.service.statistic.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("/public/statistic/getTransactionsInLast7Days")
    public List<Double> getTransactionsInLast7Days(){
        return statisticService.getTransactionsIn7Day();
    }

    @GetMapping("/public/statistic/getTransactionsInLast12Months")
    public List<Double> getTransactionsInLast12Months(){
        return statisticService.getTransactionsIn12Months();
    }

    @GetMapping("/public/statistic/getTransactionsInLast3Years")
    public List<Double> getTransactionsInLast3Years(){
        return statisticService.getTransactionsIn3Years();
    }

    @GetMapping("/public/statistic/getTodayTransactionRevenue")
    public Double getTodayTransactionRevenue(){
        return statisticService.getTodayTransactionRevenue();
    }

    @GetMapping("/public/statistic/getThisMonthTransactionRevenue")
    public Double getThisMonthTransactionRevenue(){
        return statisticService.getThisMonthTransactionRevenue();
    }

    @GetMapping("/public/statistic/getThisYearTransactionRevenue")
    public Double getThisYearTransactionRevenue(){
        return statisticService.getThisYearTransactionRevenue();
    }

    @GetMapping("/public/statistic/getTodayTrip")
    public int getTodayTrip(){
        return statisticService.getTodayTransaction().size();
    }

    @GetMapping("/public/statistic/getThisMonthTrip")
    public int getThisMonthTrip(){
        return statisticService.getMonthTransaction().size();
    }

    @GetMapping("/public/statistic/getThisYearTrip")
    public int getThisYearTrip(){
        return statisticService.getYearTransaction().size();
    }

    @GetMapping("/public/statistic/getTripsInLast12Months")
    public List<Integer> getTripsInLast12Months(){
        return statisticService.getTripIn12Months();
    }
}
