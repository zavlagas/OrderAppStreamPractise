package com.speedorder.orderapp.service.task;

import com.speedorder.orderapp.entity.Customer;
import com.speedorder.orderapp.entity.Order;
import com.speedorder.orderapp.entity.Product;
import com.speedorder.orderapp.exception.custom.ResourceNotFoundException;
import com.speedorder.orderapp.service.order.OrderService;
import com.speedorder.orderapp.service.product.ProductService;
import com.speedorder.orderapp.util.OptionalCollection;
import com.speedorder.orderapp.util.OptionalMap;
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
    public List<Product> collectProductsByCategoryAndMinimumPrice(String category, double price) {
        return OptionalCollection
                .of(collectProductsByCategory(category)
                        .filter(product -> product.getPrice() > price).collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no data with this category [ %s ] on our database", category)));
    }

    @Override
    public List<Order> collectOrdersWithProductsByCategory(String category) {
        return OptionalCollection.of(
                        orderService.findAll()
                                .stream()
                                .filter(order -> order
                                        .getProducts()
                                        .stream()
                                        .anyMatch(product -> product.getCategory().equalsIgnoreCase(category)))
                                .collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no data with this category [ %s ] on our database", category)));

    }


    @Override
    public List<Product> collectProductsWithCategoryAndDiscount(String category, int percentage) {
        return OptionalCollection.of(collectProductsByCategory(category).map(ProductUtils.discount(percentage)).collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no data with this category [ %s ] on our database to discount", category)));
    }

    @Override
    public List<Product> collectProductsOrderedByCustomerAndSpecificOrderDateCalendar(int tier, String startDate, String endDate) {
        return OptionalCollection.of(orderService.findAll()
                        .stream()
                        .filter(order -> order.getCustomer().getTier().equals(tier))
                        .filter(OrderUtils.filterCalendar(startDate, endDate))
                        .flatMap(order -> order.getProducts().stream())
                        .distinct()
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no data with this category [ %d , %s , %s ] on our database", tier, startDate, endDate)));
    }

    @Override
    public List<Product> collectCheapestProductsByCategory(String category, int numberOfProducts) {
        return OptionalCollection
                .of(collectProductsByCategory(category)
                        .sorted(Comparator.comparing(Product::getPrice))
                        .limit(numberOfProducts)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no data with this category [ %s ] on our database", category)));
    }

    @Override
    public List<Order> collectLatestOrders(int numberOfOrders) {

        return OptionalCollection
                .of(orderService.findAll()
                        .stream()
                        .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                        .limit(numberOfOrders)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException("There are no orders placed yet"));


    }

    @Override
    public List<Product> collectOrdersWithOrderedDate(String orderedDate) {

        return OptionalCollection
                .of(orderService.findAll()
                        .stream()
                        .filter(OrderUtils.filterCalendar(orderedDate))
                        .peek(order -> System.out.println(order.toString()))
                        .flatMap(order -> order.getProducts().stream())
                        .distinct().collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No orders placed in specific date [ %s ]", orderedDate)));
    }

    @Override
    public Double collectLumpSumOfOrdersPlacedInSpecificDate(String orderedDate) {

        return Optional.of(
                        orderService.findAll()
                                .stream()
                                .filter(OrderUtils.filterCalendarOnSpecificMonthOfYear(orderedDate))
                                .flatMap(order -> order.getProducts().stream())
                                .mapToDouble(Product::getPrice)
                                .sum())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No orders placed in specific date [ %s ]", orderedDate)));
    }

    @Override
    public double collectAverageOrdersPlacedInSpecificDate(String orderedDate) {
        return orderService.findAll()
                .stream()
                .filter(OrderUtils.filterCalendar(orderedDate))
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .average()
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("Found no products to calculate average for date [ %s ] ", orderedDate)));
    }

    @Override
    public DoubleSummaryStatistics collectStatisticFiguresForProductsByCategory(String category) {

        return Optional.of(collectProductsByCategory(category)
                        .mapToDouble(Product::getPrice)
                        .summaryStatistics())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Found no products in category [ %s ] to calculate statistics ", category)));
    }

    @Override
    public Map<Long, Integer> collectMapWithOrderIdAndOrdersProductCount() {
        return OptionalMap.of(orderService.findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                Order::getId,
                                Order::getProductsQuantity
                        )
                )).orElseThrow(() -> new ResourceNotFoundException("There are no orders placed yet"));
    }

    @Override
    public Map<Customer, List<Order>> collectOrdersGroupedByCustomer() {
        return OptionalMap.of(orderService.findAll()
                        .stream()
                        .collect(Collectors.groupingBy(Order::getCustomer)))
                .orElseThrow(() -> new ResourceNotFoundException("There are no customers in our data"));
    }

    @Override
    public Map<Order, Double> collectMapOrderRecordWithTotalSum() {
        return OptionalMap.of(orderService.findAll()
                .stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                order -> order.getProducts()
                                        .stream()
                                        .mapToDouble(Product::getPrice)
                                        .sum()
                        )
                )).orElseThrow(() -> new ResourceNotFoundException("There are no orders placed yet"));
    }

}
