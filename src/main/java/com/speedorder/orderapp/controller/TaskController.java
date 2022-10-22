package com.speedorder.orderapp.controller;


import com.speedorder.orderapp.entity.Customer;
import com.speedorder.orderapp.entity.Order;
import com.speedorder.orderapp.entity.Product;
import com.speedorder.orderapp.service.task.TaskService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;


    //    Exercise 1 — Obtain a list of products belongs to category “Books” with price > 100
    @ApiOperation(value = "returns a list of products belongs to input category and with input min price")
    @GetMapping("/products/{category}")
    public ResponseEntity<List<Product>> getProductsByCategoryAndMinimumPrice(@PathVariable("category") String category, @RequestParam double minPrice) {
        return ResponseEntity.ok(taskService.collectProductsByCategoryAndMinimumPrice(category, minPrice));

    }


    //    Exercise 2 — Obtain a list of order with products belong to category “Baby”
    @ApiOperation(value = "returns a list of orders with products belong to input category")
    @GetMapping("/products/{category}/orders")
    public ResponseEntity<List<Order>> getOrdersWithProductsByCategory(@PathVariable("category") String category) {
        return ResponseEntity.ok(taskService.collectOrdersWithProductsByCategory(category));


    }


    //    Exercise 3 — Obtain a list of product with category = “Toys” and then apply 10% discount
    @ApiOperation(value = "returns a list of orders with products belong to input category and then apply the input discount")
    @GetMapping("/products/{category}/discount")
    public ResponseEntity<List<Product>> getProductsWithCategoryAndDiscount(@PathVariable("category") String category,
                                                                            @RequestParam("discount") int percentage) {
        return ResponseEntity.ok(taskService.collectProductsWithCategoryAndDiscount(category, percentage));

    }

    //    Exercise 4 — Obtain a list of products ordered by customer of tier 2 between 01-Feb-2021 and 01-Apr-2021
    @ApiOperation(value = "returns a list of products ordered by customer of input tier between input starting date and ending date")
    @GetMapping("/customers/{tier}/orders/products")
    public ResponseEntity<List<Product>> getProductsOrderedByCustomerAndSpecificOrderDateCalendar(@PathVariable("tier") int tier,
                                                                                                  @RequestParam String startDate,
                                                                                                  @RequestParam String endDate) {

        return ResponseEntity.ok(taskService.collectProductsOrderedByCustomerAndSpecificOrderDateCalendar(tier, startDate, endDate));

    }

    //    Exercise 5 — Get the cheapest products of “Books” category
    @ApiOperation(value = "returns a list of the cheapest products of the input category and with input limit size")
    @GetMapping("/products/{category}/cheapest")
    public ResponseEntity<List<Product>> getCheapestProductsByCategory(@PathVariable("category") String category,
                                                                       @RequestParam("numberOfProducts") int numberOfProducts) {

        return ResponseEntity.ok(taskService.collectCheapestProductsByCategory(category, numberOfProducts));

    }

    //    Exercise 6 — Get the 3 most recent placed order
    @ApiOperation(value = "returns a  list of the most recent placed orders with number of orders limitation")
    @GetMapping("/orders/latest/{numberOfOrders}")
    public ResponseEntity<List<Order>> getLatestOrders(@PathVariable("numberOfOrders") int numberOfOrders) {
        return ResponseEntity.ok(taskService.collectLatestOrders(numberOfOrders));

    }

    //    Exercise 7 — Get a list of orders which were ordered on 15-Mar-2021, log the order records to the console and then return its product list
    @ApiOperation(value = "returns a list of orders which were ordered on input date, log the order records to the console and then return its product list")
    @GetMapping("/orders/products/")
    public ResponseEntity<List<Product>> getProductsWithOrderedDate(@RequestParam String orderedDate) {
        return ResponseEntity.ok(taskService.collectOrdersWithOrderedDate(orderedDate));
    }

    //    Exercise 8 — Calculate total lump sum of all orders placed in Feb 2021
    @ApiOperation(value = "returns the total lump sum of all orders placed in input date")
    @GetMapping("/orders/sum")
    public ResponseEntity<Double> getLumpSumOfOrdersPlacedInSpecificDate(@RequestParam String orderedDate) {
        return ResponseEntity.ok(taskService.collectLumpSumOfOrdersPlacedInSpecificDate(orderedDate));
    }

    //    Exercise 9 — Calculate order average payment placed on 14-Mar-2021 [ Δεν υπαρχει order για αυτο το date]
    @ApiOperation(value = "returns the order average payment placed on input date")
    @GetMapping("/orders/average")
    public ResponseEntity<Double> getAverageOrdersPlacedInSpecificDate(@RequestParam String orderedDate) {
        return ResponseEntity.ok(taskService.collectAverageOrdersPlacedInSpecificDate(orderedDate));
    }

    //    Exercise 10 — Obtain a collection of statistic figures (i.e. sum, average, max, min, count) for all products of category “Books”
    @ApiOperation(value = "returns a collection of statistic figures (i.e. sum, average, max, min, count) for all products of input category")
    @GetMapping("/products/statistics/{category}")
    public ResponseEntity<DoubleSummaryStatistics> getStatisticFiguresForProductsByCategory(@PathVariable("category") String category) {
        return ResponseEntity.ok(taskService.collectStatisticFiguresForProductsByCategory(category));

    }

    //    Exercise 11 — Obtain a data map with order id and order’s product count
    @ApiOperation(value = "returns a data map with order id and order’s product count")
    @GetMapping("/orders/count/products")
    public ResponseEntity<Map<Long, Integer>> getMapWithOrderIdAndOrdersProduct() {
        return ResponseEntity.ok(taskService.collectMapWithOrderIdAndOrdersProductCount());

    }

    //    Exercise 12 — Produce a data map with order records grouped by customer
    @ApiOperation(value = "returns a data map with order records grouped by customer")
    @GetMapping("/customers/orders")
    public ResponseEntity<Map<Customer, List<Order>>> getOrdersGroupedByCustomer() {
        return ResponseEntity.ok(taskService.collectOrdersGroupedByCustomer());


    }

    //    Exercise 13 — Produce a data map with order record and product total sum
    @ApiOperation(value = "returns a data map with order record and product total sum")
    @GetMapping("/orders/product/sum")
    public ResponseEntity<Map<Order, Double>> getMapOrderRecordWithTotalSum() {
        return ResponseEntity.ok(taskService.collectMapOrderRecordWithTotalSum());


    }

}
