package com.speedorder.orderapp.controller;


import com.speedorder.orderapp.entity.Customer;
import com.speedorder.orderapp.entity.Order;
import com.speedorder.orderapp.entity.Product;
import com.speedorder.orderapp.exception.custom.ResourceNotFoundException;
import com.speedorder.orderapp.service.task.TaskService;
import com.speedorder.orderapp.util.OptionalCollection;
import com.speedorder.orderapp.util.OptionalMap;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;


    //    Exercise 1 — Obtain a list of products belongs to category “Books” with price > 100
    @ApiOperation(value = "returns a list of products belongs to input category and with input min price")
    @GetMapping("/products/{category}")
    public ResponseEntity<List<Product>> getProductsByCategoryAndMinimumPrice(@PathVariable("category") String category, @RequestParam double minPrice) {
        return ResponseEntity.ok(
                OptionalCollection
                        .of(taskService.collectProductsByCategoryAndMinimumPrice(category, minPrice).collect(Collectors.toList()))
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no data with this category [ %s ] on our database", category)))
        );

    }


    //    Exercise 2 — Obtain a list of order with products belong to category “Baby”
    @ApiOperation(value = "returns a list of orders with products belong to input category")
    @GetMapping("/products/{category}/orders")
    public ResponseEntity<List<Order>> getOrdersWithProductsByCategory(@PathVariable("category") String category) {
        return ResponseEntity.ok(
                OptionalCollection
                        .of(taskService.collectOrdersWithProductsByCategory(category).collect(Collectors.toList()))
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no data with this category [ %s ] on our database", category))));


    }


    //    Exercise 3 — Obtain a list of product with category = “Toys” and then apply 10% discount
    @ApiOperation(value = "returns a list of orders with products belong to input category and then apply the input discount")
    @GetMapping("/products/{category}/discount")
    public ResponseEntity<List<Product>> getProductsWithCategoryAndDiscount(@PathVariable("category") String category,
                                                                            @RequestParam("discount") int discount) {
        return ResponseEntity.ok(
                OptionalCollection
                        .of(taskService.collectProductsWithCategoryAndDiscount(category, discount).collect(Collectors.toList()))
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no data with this category [ %s ] on our database", category))));

    }

    //    Exercise 4 — Obtain a list of products ordered by customer of tier 2 between 01-Feb-2021 and 01-Apr-2021
    @ApiOperation(value = "returns a list of products ordered by customer of input tier between input starting date and ending date")
    @GetMapping("/customers/{tier}/orders/products")
    public ResponseEntity<List<Product>> getProductsOrderedByCustomerAndSpecificOrderDateCalendar(@PathVariable("tier") int tier,
                                                                                                  @RequestParam String startDate,
                                                                                                  @RequestParam String endDate) {

        return ResponseEntity.ok(
                OptionalCollection
                        .of(taskService.collectProductsOrderedByCustomerAndSpecificOrderDateCalendar(tier, startDate, endDate).collect(Collectors.toList()))
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no data with this category [ %d , %s , %s ] on our database", tier, startDate, endDate))));

    }

    //    Exercise 5 — Get the cheapest products of “Books” category
    @ApiOperation(value = "returns a list of the cheapest products of the input category and with input limit size")
    @GetMapping("/products/discount/{category}/{numberOfProducts}")
    public ResponseEntity<List<Product>> getCheapestProductsByCategory(@PathVariable("category") String category,
                                                                       @PathVariable("numberOfProducts") int numberOfProducts) {

        return ResponseEntity.ok(
                OptionalCollection
                        .of(taskService.collectCheapestProductsByCategory(category, numberOfProducts).collect(Collectors.toList()))
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no data with this category [ %s ] on our database", category))));

    }

    //    Exercise 6 — Get the 3 most recent placed order
    @ApiOperation(value = "returns a  list of the most recent placed orders with number of orders limitation")
    @GetMapping("/orders/latest/{numberOfOrders}")
    public ResponseEntity<List<Order>> getLatestOrders(@PathVariable("numberOfOrders") int numberOfOrders) {
        return ResponseEntity.ok(
                OptionalCollection
                        .of(taskService.collectLatestOrders(numberOfOrders).collect(Collectors.toList()))
                        .orElseThrow(() -> new ResourceNotFoundException("There are no orders placed yet")));

    }

    //    Exercise 7 — Get a list of orders which were ordered on 15-Mar-2021, log the order records to the console and then return its product list
    @ApiOperation(value = "returns a list of orders which were ordered on input date, log the order records to the console and then return its product list")
    @GetMapping("/orders/products/")
    public ResponseEntity<List<Product>> getProductsWithOrderedDate(@RequestParam String orderedDate) {
        return ResponseEntity.ok(
                OptionalCollection
                        .of(taskService.collectOrdersWithOrderedDate(orderedDate).collect(Collectors.toList()))
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("No orders placed in specific date [ %s ]", orderedDate)))

        );
    }

    //    Exercise 8 — Calculate total lump sum of all orders placed in Feb 2021
    @ApiOperation(value = "returns the total lump sum of all orders placed in input date")
    @GetMapping("/orders/sum")
    public ResponseEntity<Double> getLumpSumOfOrdersPlacedInSpecificDate(@RequestParam String orderedDate) {
        return ResponseEntity.ok(
                taskService
                        .collectLumpSumOfOrdersPlacedInSpecificDate(orderedDate)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("No orders placed in specific date [ %s ]", orderedDate))));
    }

    //    Exercise 9 — Calculate order average payment placed on 14-Mar-2021 [ Δεν υπαρχει order για αυτο το date]
    @ApiOperation(value = "returns the order average payment placed on input date")
    @GetMapping("/orders/average")
    public ResponseEntity<Double> getAverageOrdersPlacedInSpecificDate(@RequestParam String orderedDate) {
        return ResponseEntity.ok(taskService
                .collectAverageOrdersPlacedInSpecificDate(orderedDate)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Found no products to calculate average for date [ %s ] ", orderedDate))));
    }

    //    Exercise 10 — Obtain a collection of statistic figures (i.e. sum, average, max, min, count) for all products of category “Books”
    @ApiOperation(value = "returns a collection of statistic figures (i.e. sum, average, max, min, count) for all products of input category")
    @GetMapping("/products/statistics/{category}")
    public ResponseEntity<DoubleSummaryStatistics> getStatisticFiguresForProductsByCategory(@PathVariable("category") String category) {
        return ResponseEntity
                .ok(taskService.collectStatisticFiguresForProductsByCategory(category)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("Found no products in category [ %s ] to calculate statistics ", category)))
                );

    }

    //    Exercise 11 — Obtain a data map with order id and order’s product count
    @ApiOperation(value = "returns a data map with order id and order’s product count")
    @GetMapping("/orders/count/products")
    public ResponseEntity<Map<Long, Integer>> getMapWithOrderIdAndOrdersProduct() {
        return ResponseEntity
                .ok(OptionalMap.of(taskService.collectMapWithOrderIdAndOrdersProduct())
                        .orElseThrow(() -> new ResourceNotFoundException("There are no orders placed yet")));

    }

    //    Exercise 12 — Produce a data map with order records grouped by customer
    @ApiOperation(value = "returns a data map with order records grouped by customer")
    @GetMapping("/customers/orders")
    public ResponseEntity<Map<Customer, List<Order>>> getOrdersGroupedByCustomer() {
        return ResponseEntity
                .ok(OptionalMap.of(taskService.collectOrdersGroupedByCustomer())
                        .orElseThrow(() -> new ResourceNotFoundException("There are no customers in our data")));


    }

    //    Exercise 13 — Produce a data map with order record and product total sum
    @ApiOperation(value = "returns a data map with order record and product total sum")
    @GetMapping("/orders/product/sum")
    public ResponseEntity<Map<Order, Double>> getMapOrderRecordWithTotalSum() {
        return ResponseEntity
                .ok(OptionalMap.of(taskService.collectMapOrderRecordWithTotalSum())
                        .orElseThrow(() -> new ResourceNotFoundException("There are no orders placed yet")));


    }

}
