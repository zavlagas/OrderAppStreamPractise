package com.speedorder.orderapp.service.task

import com.speedorder.orderapp.entity.Product
import com.speedorder.orderapp.service.order.OrderService
import com.speedorder.orderapp.service.product.ProductService
import com.speedorder.orderapp.util.CategoryEnum
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

import java.util.stream.Collectors

@SpringBootTest
class TaskServiceImplTest extends Specification {
    @Shared
    private List<Product> products;

    def "setupSpec"() {
        this.products = List.of(
                new Product("Product 1", CategoryEnum.BABY, 1),
                new Product("Product 6", CategoryEnum.BABY, 6),
                new Product("Product 6", CategoryEnum.BABY, 12,),
                new Product("Product 7", CategoryEnum.BOOKS, 7),
                new Product("Product 2", CategoryEnum.BOOKS, 2),
                new Product("Product 2", CategoryEnum.BOOKS, 14),
                new Product("Product 3", CategoryEnum.GAMES, 3),
                new Product("Product 8", CategoryEnum.GAMES, 8),
                new Product("Product 8", CategoryEnum.GAMES, 16),
                new Product("Product 4", CategoryEnum.GROCERY, 4),
                new Product("Product 9", CategoryEnum.GROCERY, 9),
                new Product("Product 9", CategoryEnum.GROCERY, 18),
                new Product("Product 5", CategoryEnum.TOYS, 5),
                new Product("Product 10", CategoryEnum.TOYS, 10),
                new Product("Product 10", CategoryEnum.TOYS, 20)
        )
    }


    def "Collect Products By Category When The Category is Found And Not Found"() {
        given: "a product and order service"
        ProductService productService = Stub(ProductService);
        OrderService orderService = Stub(OrderService);
        and: "a task service"
        TaskService taskService = new TaskServiceImpl(productService, orderService)
        when: "finding all products with the input category"
        productService.findAll() >> products;
        List<Product> categoryProducts = taskService
                .collectProductsByCategory(category)
                .collect(Collectors.toList())

        then: "the size of the categoryProducts"
        categoryProducts.size() == size
        and: "the category of each product must match the input category"
        categoryProducts.each { product -> product.getCategory() == category }

        where: "scenarios are"

        category                           || size
        CategoryEnum.TOYS.getCategory()    || 3
        CategoryEnum.GROCERY.getCategory() || 3
        CategoryEnum.GAMES.getCategory()   || 3
        CategoryEnum.BOOKS.getCategory()   || 3
        CategoryEnum.BABY.getCategory()    || 3
    }

    def "Collect Products By Category And Minimum Price"() {
        given: "a product and order service"
        ProductService productService = Stub(ProductService);
        OrderService orderService = Stub(OrderService);
        and: "a task service"
        TaskService taskService = new TaskServiceImpl(productService, orderService)
        when: "finding all products with the input category"
        productService.findAll() >> products;
        List<Product> categoryProducts = taskService
                .collectProductsByCategoryAndMinimumPrice(category, minPrice);

        then: "the price of each product must be bigger than the min price"
        categoryProducts.each { product -> product.getPrice() > minPrice }

        where: "scenarios are"

        category                           | minPrice
        CategoryEnum.BABY.getCategory()    | 1
        CategoryEnum.BABY.getCategory()    | 6
        CategoryEnum.BABY.getCategory()    | 12
        CategoryEnum.BOOKS.getCategory()   | 7
        CategoryEnum.BOOKS.getCategory()   | 2
        CategoryEnum.BOOKS.getCategory()   | 14
        CategoryEnum.GAMES.getCategory()   | 3
        CategoryEnum.GAMES.getCategory()   | 8
        CategoryEnum.GAMES.getCategory()   | 16
        CategoryEnum.GROCERY.getCategory() | 4
        CategoryEnum.GROCERY.getCategory() | 9
        CategoryEnum.GROCERY.getCategory() | 18
        CategoryEnum.TOYS.getCategory()    | 5
        CategoryEnum.TOYS.getCategory()    | 10
        CategoryEnum.TOYS.getCategory()    | 20
    }

    def "CollectOrdersWithProductsByCategory"() {
    }

    def "CollectProductsWithCategoryAndDiscount"() {
    }

    def "CollectProductsOrderedByCustomerAndSpecificOrderDateCalendar"() {
    }

    def "CollectCheapestProductsByCategory"() {
    }

    def "CollectLatestOrders"() {
    }

    def "CollectOrdersWithOrderedDate"() {
    }

    def "CollectLumpSumOfOrdersPlacedInSpecificDate"() {
    }

    def "CollectAverageOrdersPlacedInSpecificDate"() {
    }

    def "CollectStatisticFiguresForProductsByCategory"() {
    }

    def "CollectMapWithOrderIdAndOrdersProductCount"() {
    }

    def "CollectOrdersGroupedByCustomer"() {
    }

    def "CollectMapOrderRecordWithTotalSum"() {
    }
}
