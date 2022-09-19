package com.speedorder.orderapp.service.task;

import com.speedorder.orderapp.entity.Customer;
import com.speedorder.orderapp.entity.Order;
import com.speedorder.orderapp.entity.Product;

import java.util.*;
import java.util.stream.Stream;

public interface TaskService {
    Stream<Product> collectProductsByCategory(String category);

    Stream<Product> collectProductsByCategoryAndMinimumPrice(String category, double price);

    Stream<Order> collectOrdersWithProductsByCategory(String category);

    Stream<Product> collectProductsWithCategoryAndDiscount(String category, int discount);

    Stream<Product> collectProductsOrderedByCustomerAndSpecificOrderDateCalendar(int tier, String startDate, String endDate);

    Stream<Product> collectCheapestProductsByCategory(String category, int numberOfProducts);

    Stream<Order> collectLatestOrders(int numberOfOrders);

    Stream<Product> collectOrdersWithOrderedDate(String orderedDate);

    Optional<Double> collectLumpSumOfOrdersPlacedInSpecificDate(String orderedDate);

    OptionalDouble collectAverageOrdersPlacedInSpecificDate(String orderedDate);

    Optional<DoubleSummaryStatistics> collectStatisticFiguresForProductsByCategory(String category);

    Map<Long, Integer> collectMapWithOrderIdAndOrdersProduct();

    Map<Customer, List<Order>> collectOrdersGroupedByCustomer();

    Map<Order, Double> collectMapOrderRecordWithTotalSum();
}
