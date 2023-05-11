package org.cdl;

import org.cdl.object.BookingItem;
import org.cdl.object.PriceScheme;
import org.cdl.object.Product;
import org.cdl.object.ShoppingBasket;
import org.cdl.service.CartService;
import org.cdl.service.CartServiceImpl;
import org.cdl.service.SetupService;
import org.cdl.service.SetupServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

/**
 * Main class
 *
 * @author deshan
 * @since 1.0
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        CartService cartService = new CartServiceImpl();
        SetupService setupService = new SetupServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            logger.info("################ WELCOME ##################");
            logger.info("Press ENTER to start(q to quit): ");
            String start = scanner.nextLine();
            if ("q".equals(start)) {
                break;
            }
            /* ################### setup new Item ################## */
            setupItem(setupService, scanner);

            /* #################### setup new price rule ##################### */
            setupPriceRule(setupService, scanner);

            /* ##################### checkout items ######################## */
            checkout(cartService, setupService, scanner);
        }

    }

    private static void setupItem(SetupService setupService, Scanner scanner) {
        logger.info("################ SETUP ITEM #####################");

        logger.info("Do you need to setup a new product(Y/N)?: ");
        String isSetupProduct = scanner.nextLine();
        /* validate answer */
        while (!List.of("Y", "N").contains(isSetupProduct)) {
            logger.info("Please ENTER a valid input(Y/N): Do you need to setup a new product(Y/N)?");
            isSetupProduct = scanner.nextLine();
        }

        while ("Y".equals(isSetupProduct)) {
            /* **************** (1) product code ******************** */
            logger.info("Enter the product code(A,B, C,...): ");
            String productCode = scanner.nextLine();
            /* validate input */
            while (!productCode.matches("[A-Z]")) {
                logger.info("Please ENTER a valid product code(Uppercase Letter): ");
                productCode = scanner.nextLine();
            }

            /* **************** (2) unit price ******************** */
            logger.info("Enter the unit price(Ex: 50P, £2.50/ Default is £): ");
            String unitPriceStr = scanner.nextLine();
            double unitPrice;
            /* validate input */
            if (unitPriceStr.startsWith("£")) {
                unitPriceStr = unitPriceStr.substring(1);
                unitPrice = Double.parseDouble(unitPriceStr);
            } else if (unitPriceStr.endsWith("P")) {
                unitPriceStr = unitPriceStr.substring(0, unitPriceStr.length() - 1);
                unitPrice = Double.parseDouble(unitPriceStr) / 100;
            } else {
                unitPrice = Double.parseDouble(unitPriceStr);
            }
            Product product = new Product(productCode);
            product.setUnitPrice(unitPrice);
            PriceScheme priceScheme = new PriceScheme(product);
            priceScheme.setQuantity(1);
            priceScheme.setPrice(unitPrice);
            setupService.addScheme(priceScheme);

            logger.info("Do you need to setup a new product(Y/N)?: ");
            isSetupProduct = scanner.nextLine();
            /* validate answer */
            while (!List.of("Y", "N").contains(isSetupProduct)) {
                logger.info("Please ENTER a valid input(Y/N): Do you need to setup a new product(Y/N)?");
                isSetupProduct = scanner.nextLine();
            }
        }
    }

    private static void setupPriceRule(SetupService setupService, Scanner scanner) {
        logger.info("################ SETUP RULE #####################");

        logger.info("Do you need to setup a new price schemes(Y/N)? ");
        String isSetupScheme = scanner.nextLine();
        /* validate answer */
        while (!List.of("Y", "N").contains(isSetupScheme)) {
            logger.info("Please ENTER a valid input(Y/N): Do you need to setup a new price schemes(Y/N)?");
            isSetupScheme = scanner.nextLine();
        }

        while ("Y".equals(isSetupScheme)) {

            /* **************** (1) product code ******************** */
            logger.info("Enter the product code(A,B, C,...): ");
            String productCode = scanner.nextLine();
            /* validate input */
            while (!productCode.matches("[A-Z]")) {
                logger.info("Please ENTER a valid product code(Uppercase Letter): ");
                productCode = scanner.nextLine();
            }
            Product product = setupService.readSchemes(productCode).get(0).getProduct();
            PriceScheme priceScheme = new PriceScheme(product);

            /* **************** (2) quantity ******************** */
            logger.info("Enter the quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            priceScheme.setQuantity(quantity);

            /* **************** (3) special price ******************** */
            logger.info("Enter the special price(Ex: 50P, £2.50/ Default is £): ");
            String priceStr = scanner.nextLine();
            double price;
            /* validate input */
            if (priceStr.startsWith("£")) {
                priceStr = priceStr.substring(1);
                price = Double.parseDouble(priceStr);
            } else if (priceStr.endsWith("P")) {
                priceStr = priceStr.substring(0, priceStr.length() - 1);
                price = Double.parseDouble(priceStr) / 100;
            } else {
                price = Double.parseDouble(priceStr);
            }
            priceScheme.setPrice(price);
            setupService.addScheme(priceScheme);

            logger.info("Do you need to setup a new price schemes(Y/N): ");
            isSetupScheme = scanner.nextLine();
            /* validate answer */
            while (!List.of("Y", "N").contains(isSetupScheme)) {
                logger.info("Please ENTER a valid input(Y/N): Do you need to setup a new price schemes(Y/N)?");
                isSetupScheme = scanner.nextLine();
            }
        }
    }

    private static void checkout(CartService cartService, SetupService setupService, Scanner scanner) {
        logger.info("################ SCAN ITEMS #####################");

        ShoppingBasket shoppingBasket = cartService.createShoppingBasket();

        /* **************** scan item ******************** */
        logger.info("Enter the product code of the item(Enter END to complete the transaction): ");
        String itemCode = scanner.nextLine();
        /* validate input */
        while (!itemCode.matches("[A-Z]")) {
            logger.info("Please ENTER a valid product code(Uppercase Letter): ");
            itemCode = scanner.nextLine();
        }
        while (!"END".equals(itemCode)) {
            Product product = setupService.readSchemes(itemCode).get(0).getProduct();
            BookingItem bookingItem = new BookingItem(product);
            bookingItem.addQuantity(1);
            shoppingBasket = cartService.addItem(shoppingBasket.getSessionId(), bookingItem);
            logger.info(String.format("Running Total : %s", shoppingBasket.getTotalPrice()));

            /* **************** scan item ******************** */
            logger.info("Enter the product code of the item(Enter END to complete the transaction): ");
            itemCode = scanner.nextLine();
            /* validate input */
            while (!itemCode.matches("[A-Z]") && !"END".equals(itemCode)) {
                logger.info("Please ENTER a valid product code(Uppercase Letter): ");
                itemCode = scanner.nextLine();
            }
        }

        /* ##################### payment ######################## */
        logger.info("################ PAYMENT #####################");
        logger.info(String.format("The Total Amount To Pay: %s", shoppingBasket.getTotalPrice()));
        logger.info("############# THANK YOU ################\n\n");
    }

}
