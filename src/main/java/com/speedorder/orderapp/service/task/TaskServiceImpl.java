package com.speedorder.orderapp.service.task;

import com.speedorder.orderapp.entity.Customer;
import com.speedorder.orderapp.entity.Order;
import com.speedorder.orderapp.entity.Product;
import com.speedorder.orderapp.service.order.OrderService;
import com.speedorder.orderapp.service.product.ProductService;
import com.speedorder.orderapp.util.OrderUtils;
import com.speedorder.orderapp.util.ProductUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
class TaskServiceImpl implements TaskService {


    private ProductService productService;
    private OrderService orderService;


    public TaskServiceImpl(ProductService productService,
                           OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @Override
    public Stream<Product> collectProductsByCategory(String category) {
        return productService.findAll()
                .stream()
                .filter(product -> product.getCategory()
                        .equalsIgnoreCase(category));
    }

    @Override
    public Stream<Product> collectProductsByCategoryAndMinimumPrice(String category, double price) {
        return collectProductsByCategory(category)
                .filter(product -> product.getPrice() > price);
    }

    @Override
    public Stream<Order> collectOrdersWithProductsByCategory(String category) {
        return orderService.findAll()
                .stream()
                .filter(order -> order
                        .getProducts()
                        .stream()
                        .anyMatch(product -> product.getCategory().equalsIgnoreCase(category)));

    }


    @Override
    public Stream<Product> collectProductsWithCategoryAndDiscount(String category, int discount) {
        return collectProductsByCategory(category).map(ProductUtils.discount(discount));
    }

    @Override
    public Stream<Product> collectProductsOrderedByCustomerAndSpecificOrderDateCalendar(int tier, String startDate, String endDate) {
        return orderService.findAll()
                .stream()
                .filter(order -> order.getCustomer().getTier().equals(tier))
                .filter(OrderUtils.filterCalendar(startDate, endDate))
                .flatMap(order -> order.getProducts().stream())
                .distinct();
    }

    @Override
    public Stream<Product> collectCheapestProductsByCategory(String category, int numberOfProducts) {
        return collectProductsByCategory(category)
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(numberOfProducts);
    }

    @Override
    public Stream<Order> collectLatestOrders(int numberOfOrders) {
        return
                orderService.findAll()
                        .stream()
                        .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                        .limit(numberOfOrders);


    }

    @Override
    public Stream<Product> collectOrdersWithOrderedDate(String orderedDate) {
        return orderService.findAll()
                .stream()
                .filter(OrderUtils.filterCalendar(orderedDate))
                .peek(order -> System.out.println(order.toString()))
                .flatMap(order -> order.getProducts().stream())
                .distinct();
    }

    @Override
    public Optional<Double> collectLumpSumOfOrdersPlacedInSpecificDate(String orderedDate) {
        double sum = orderService.findAll()
                .stream()
                .filter(OrderUtils.filterCalendarOnSpecificMonthOfYear(orderedDate))
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();
        return sum != 0 ? Optional.of(sum) : Optional.empty();
    }

    @Override
    public OptionalDouble collectAverageOrdersPlacedInSpecificDate(String orderedDate) {
        return orderService.findAll()
                .stream()
                .filter(OrderUtils.filterCalendar(orderedDate))
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .average();
    }

    @Override
    public Optional<DoubleSummaryStatistics> collectStatisticFiguresForProductsByCategory(String category) {
        DoubleSummaryStatistics doubleSummaryStatistics = collectProductsByCategory(category)
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
        return doubleSummaryStatistics.getCount() != 0 ? Optional.of(doubleSummaryStatistics) : Optional.empty();
    }

    @Override
    public Map<Long, Integer> collectMapWithOrderIdAndOrdersProduct() {
        return orderService.findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                Order::getId,
                                Order::getProductsQuantity
                        )
                );
    }

    @Override
    public Map<Customer, List<Order>> collectOrdersGroupedByCustomer() {
        return orderService.findAll()
                .stream()
                .collect(Collectors.groupingBy(Order::getCustomer));
    }

    @Override
    public Map<Order, Double> collectMapOrderRecordWithTotalSum() {
        return orderService.findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                order -> order.getProducts()
                                        .stream()
                                        .mapToDouble(Product::getPrice)
                                        .sum()
                        )
                );
    }

}
