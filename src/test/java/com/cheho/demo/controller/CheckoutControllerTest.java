package com.cheho.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckoutControllerTest {

    private Logger logger = LoggerFactory.getLogger(CheckoutControllerTest.class);

    @Autowired
    BeanFactory beanFactory;


    @Test
    void chainsawTest() throws Exception {
        logger.info("Running basic chainsaw test...");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkoutDate = LocalDate.of(2024, 9, 1);
        int rentalLength = 12;
        int discount=20;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "CHNS",
                discount
                );
        InvoicePrinter invoicePrinter = checkoutController.checkout();
        assertEquals(9, invoicePrinter.getChargeDays());
        assertEquals(13.41, invoicePrinter.getPreDiscountCharge());
        assertTrue(invoicePrinter.getCheckoutDate().isEqual(checkoutDate));

    }

    @Test
    void jackhammerTest() throws Exception {
        logger.info("Running basic jackhammer test...");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkoutDate = LocalDate.of(2024, 7, 2);
        int rentalLength = 10;
        int discount=5;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "JAKD",
                discount
        );
        InvoicePrinter invoicePrinter = checkoutController.checkout();
        assertEquals("DeWalt", invoicePrinter.getToolBrand());
        assertEquals(7, invoicePrinter.getChargeDays());
        assertTrue(invoicePrinter.getCheckoutDate().isEqual(checkoutDate));
    }

    @Test
    void ladderTest() throws Exception {
        logger.info("Running basic ladder test...");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkoutDate = LocalDate.of(2023, 7, 2);
        int rentalLength = 5;
        int discount=12;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "LADW",
                discount
        );
        InvoicePrinter invoicePrinter = checkoutController.checkout();
        assertEquals("Werner", invoicePrinter.getToolBrand());
        assertTrue(invoicePrinter.getCheckoutDate().isEqual(checkoutDate));
    }

    @Test
    void invalidDiscountTest() throws Exception {
        logger.info("Running invalidDiscountTest()");

        LocalDate checkoutDate = LocalDate.of(2023, 7, 2);
        int rentalLength = 5;
        int discount=101;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "LADW",
                discount
        );
        assertThrows(Exception.class, () -> checkoutController.checkout(),
                "Expected to throw an exception when the discount is out of range.");
    }

    @Test
    void invalidRentalDaysTest() throws Exception {

        logger.info("Running invalidRentalDaysTest...");

        LocalDate checkoutDate = LocalDate.of(2023, 9, 2);
        int rentalLength = 0;
        int discount=20;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "LADW",
                discount
        );
        assertThrows(Exception.class, () -> checkoutController.checkout(),
                "Expected to throw an exception when the discount is out of range.");
    }

    @Test
    void invalidToolCodeTest() throws Exception {
        logger.info("Running invalidToolCodeTest...");

        LocalDate checkoutDate = LocalDate.of(2023, 9, 2);
        int rentalLength = 10;
        int discount=20;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "ABCD",
                discount
        );
        assertThrows(Exception.class, () -> checkoutController.checkout(),
                "Expected to throw an exception when tool code is not part in the system.");
    }

    @Test
    void testCase1() throws Exception {
        logger.info("Running unit test case 1...");
        
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        int rentalLength = 5;
        int discount=101;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "JAKR",
                discount
        );

        // Discount is out of range.  Expect an exception thrown.
        assertThrows(Exception.class, checkoutController::checkout,
                "Expected to throw an exception when the discount is out of range.");
    }

    @Test
    void testCase2() throws Exception {

        logger.info("Running unit test case 2...");

        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        LocalDate returnDate = LocalDate.of(2020, 7, 5);

        int rentalLength = 3;
        int discount=10;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "LADW",
                discount
        );

        // July 4th and 5th of 2020 were on the weekend.
        // July 4th discount fell on July 3rd Friday
        // Weekend charge.
        // No Holiday charge
        InvoicePrinter invoicePrinter = checkoutController.checkout();
        assertEquals("LADW", invoicePrinter.getToolCode());
        assertEquals("Ladder", invoicePrinter.getToolType());
        assertEquals("Werner", invoicePrinter.getToolBrand());
        assertEquals(checkoutDate, invoicePrinter.getCheckoutDate());
        assertEquals(returnDate, invoicePrinter.getDueDate());
        assertEquals(1.99, invoicePrinter.getDailyRentalCharge());
        assertEquals(2, invoicePrinter.getChargeDays());
        assertEquals(1.99*2, invoicePrinter.getPreDiscountCharge());
        assertEquals(1.99*2*0.1, invoicePrinter.getDiscountAmount());
        assertEquals(10, invoicePrinter.getDiscountPercentage());
        assertEquals(1.99*2*0.9, invoicePrinter.getFinalCharge());
    }

    @Test
    void testCase3() throws Exception {
        logger.info("Running unit test case 3...");

        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        LocalDate returnDate = LocalDate.of(2015, 7, 7);

        int rentalLength = 5;
        int discount=25;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "CHNS",
                discount
        );
        // 2 days were on the weekend (July 4 and 5)
        // Holiday charge
        InvoicePrinter invoicePrinter = checkoutController.checkout();
        assertEquals("CHNS", invoicePrinter.getToolCode());
        assertEquals("Chainsaw", invoicePrinter.getToolType());
        assertEquals("Stihl", invoicePrinter.getToolBrand());
        assertEquals(checkoutDate, invoicePrinter.getCheckoutDate());
        assertEquals(returnDate, invoicePrinter.getDueDate());
        assertEquals(1.49, invoicePrinter.getDailyRentalCharge());
        assertEquals(3, invoicePrinter.getChargeDays());
        assertEquals(1.49*3, invoicePrinter.getPreDiscountCharge());
        assertEquals(1.49*3*0.25, invoicePrinter.getDiscountAmount());
        assertEquals(25, invoicePrinter.getDiscountPercentage());
        assertEquals(1.49*3*0.75, invoicePrinter.getFinalCharge());
    }

    @Test
    void testCase4() throws Exception {
        logger.info("Running unit test case 4...");

        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        LocalDate returnDate = LocalDate.of(2015, 9, 9);

        int rentalLength = 6;
        int discount=0;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "JAKD",
                discount
        );
        // September 5th and 6th were on the weekend
        // Septmber 7th is labor day
        // Expect 3 days of no charge
        InvoicePrinter invoicePrinter = checkoutController.checkout();
        assertEquals("JAKD", invoicePrinter.getToolCode());
        assertEquals("Jackhammer", invoicePrinter.getToolType());
        assertEquals("DeWalt", invoicePrinter.getToolBrand());
        assertEquals(checkoutDate, invoicePrinter.getCheckoutDate());
        assertEquals(returnDate, invoicePrinter.getDueDate());
        assertEquals(2.99, invoicePrinter.getDailyRentalCharge());
        assertEquals(3, invoicePrinter.getChargeDays());
        assertEquals(2.99*3, invoicePrinter.getPreDiscountCharge());
        assertEquals(0, invoicePrinter.getDiscountAmount());
        assertEquals(0, invoicePrinter.getDiscountPercentage());
        assertEquals(2.99*3, invoicePrinter.getFinalCharge());
    }

    @Test
    void testCase5() throws Exception {
        logger.info("Running unit test case 5...");

        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        LocalDate returnDate = LocalDate.of(2015, 7, 11);

        int rentalLength = 9;
        int discount=0;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "JAKR",
                discount
        );
        // 3 days were on the weekend (July 4th, 5th)
        // July 3rd would be no charge for July 4th weekend
        // Expect 3 days no charge, since 11th is the due date even though it was a Saturday (debatable)
        InvoicePrinter invoicePrinter = checkoutController.checkout();
        assertEquals("JAKR", invoicePrinter.getToolCode());
        assertEquals("Jackhammer", invoicePrinter.getToolType());
        assertEquals("Ridgid", invoicePrinter.getToolBrand());
        assertEquals(checkoutDate, invoicePrinter.getCheckoutDate());
        assertEquals(returnDate, invoicePrinter.getDueDate());
        assertEquals(2.99, invoicePrinter.getDailyRentalCharge());
        assertEquals(6, invoicePrinter.getChargeDays());
        assertEquals(2.99*6, invoicePrinter.getPreDiscountCharge());
        assertEquals(0, invoicePrinter.getDiscountAmount());
        assertEquals(0, invoicePrinter.getDiscountPercentage());
        assertEquals(2.99*6, invoicePrinter.getFinalCharge());
    }

    @Test
    void testCase6() throws Exception {
        logger.info("Running unit test case 6...");

        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        LocalDate returnDate = LocalDate.of(2015, 7, 6);

        int rentalLength = 4;
        int discount=50;
        CheckoutController checkoutController = beanFactory.getBean(CheckoutController.class,
                checkoutDate,
                rentalLength,
                "JAKD",
                discount
        );
        // 3 days were on the weekend (July 4th, 5th)
        // July 3rd would be no charge for July 4th weekend
        InvoicePrinter invoicePrinter = checkoutController.checkout();
        assertEquals("JAKD", invoicePrinter.getToolCode());
        assertEquals("Jackhammer", invoicePrinter.getToolType());
        assertEquals("DeWalt", invoicePrinter.getToolBrand());
        assertEquals(checkoutDate, invoicePrinter.getCheckoutDate());
        assertEquals(returnDate, invoicePrinter.getDueDate());
        assertEquals(2.99, invoicePrinter.getDailyRentalCharge());
        assertEquals(1, invoicePrinter.getChargeDays());
        assertEquals(2.99*1, invoicePrinter.getPreDiscountCharge());
        assertEquals(2.99*1*0.5, invoicePrinter.getDiscountAmount());
        assertEquals(50, invoicePrinter.getDiscountPercentage());
        assertEquals(2.99*1*0.5, invoicePrinter.getFinalCharge());
    }
}