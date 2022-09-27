package com.speedorder.orderapp.service.task;

import com.speedorder.orderapp.entity.Customer;
import com.speedorder.orderapp.entity.Order;
import com.speedorder.orderapp.entity.Product;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface TaskService {
    Stream<Product> collectProductsByCategory(String category);

    List<Product> collectProductsByCategoryAndMinimumPrice(String category, double price);

    List<Order> collectOrdersWithProductsByCategory(String category);

    List<Product> collectProductsWithCategoryAndDiscount(String category, int percentage);

    List<Product> collectProductsOrderedByCustomerAndSpecificOrderDateCalendar(int tier, String startDate, String endDate);

    List<Product> collectCheapestProductsByCategory(String category, int numberOfProducts);

    List<Order> collectLatestOrders(int numberOfOrders);

    List<Product> collectOrdersWithOrderedDate(String orderedDate);

    Double collectLumpSumOfOrdersPlacedInSpecificDate(String orderedDate);

    double collectAverageOrdersPlacedInSpecificDate(String orderedDate);

    DoubleSummaryStatistics collectStatisticFiguresForProductsByCategory(String category);

    Map<Long, Integer> collectMapWithOrderIdAndOrdersProductCount();

    Map<Customer, List<Order>> collectOrdersGroupedByCustomer();

    Map<Order, Double> collectMapOrderRecordWithTotalSum();
}
