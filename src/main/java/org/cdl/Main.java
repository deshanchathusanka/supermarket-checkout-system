package org.cdl;

import org.cdl.object.BookingItem;
import org.cdl.object.PriceScheme;
import org.cdl.object.Product;
import org.cdl.object.ShoppingBasket;
import org.cdl.service.CartService;
import org.cdl.service.CartServiceImpl;
import org.cdl.service.SetupService;
import org.cdl.service.SetupServiceImpl;

import java.util.Scanner;

/**
 * Main class
 *
 * @author deshan
 * @since 1.0
 */
public class Main {

    public static void main(String[] args) {
        CartService cartService = new CartServiceImpl();
        SetupService setupService = new SetupServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("################ welcome ##################");
            System.out.print("Press ENTER to start(q to quit): ");
            String start = scanner.nextLine();
            if ("q".equals(start)) {
                break;
            }

            /* ################### setup new Item ################## */
            System.out.print("Do you need to setup a new product(Y/N): ");
            String isSetupProduct = scanner.nextLine();
            while ("Y".equals(isSetupProduct)) {
                System.out.print("Enter the product code(A,B, C,...): ");
                String productCode = scanner.nextLine();
                System.out.print("Enter the unit price: ");
                Double unitPrice = Double.valueOf(scanner.nextLine());
                Product product = new Product(productCode);
                product.setUnitPrice(unitPrice);

                PriceScheme priceScheme = new PriceScheme(product);
                priceScheme.setQuantity(1);
                priceScheme.setPrice(unitPrice);
                setupService.addScheme(priceScheme);

                System.out.print("Do you need to setup a new product(Y/N): ");
                isSetupProduct = scanner.nextLine();
            }

            /* #################### setup new price rule ##################### */
            System.out.print("Do you need to setup a new price schemes(Y/N): ");
            String isSetupScheme = scanner.nextLine();
            while ("Y".equals(isSetupScheme)) {
                System.out.print("Enter the product code(A,B, C,...): ");
                String productCode = scanner.nextLine();
                Product product = setupService.readSchemes(productCode).get(0).getProduct();
                PriceScheme priceScheme = new PriceScheme(product);

                System.out.print("Enter the quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine());
                priceScheme.setQuantity(quantity);

                System.out.print("Enter the special price: ");
                double price = Double.parseDouble(scanner.nextLine());
                priceScheme.setPrice(price);
                setupService.addScheme(priceScheme);

                System.out.print("Do you need to setup a new price schemes(Y/N): ");
                isSetupScheme = scanner.nextLine();
            }

            /* ##################### checkout items ######################## */
            ShoppingBasket shoppingBasket = cartService.createShoppingBasket();
            System.out.print("Enter the product code of the item(Enter END to complete the transaction): ");
            String itemCode = scanner.nextLine();
            while (!"END".equals(itemCode)) {
                Product product = setupService.readSchemes(itemCode).get(0).getProduct();
                BookingItem bookingItem = new BookingItem(product);
                bookingItem.addQuantity(1);
                shoppingBasket = cartService.addItem(shoppingBasket.getSessionId(), bookingItem);
                System.out.print("Running Total : " + shoppingBasket.getTotalPrice());
                System.out.print("Enter the product code of the item(Enter END to complete the transaction): ");
                itemCode = scanner.nextLine();
            }
            System.out.println("The Total Amount To Pay: " + shoppingBasket.getTotalPrice());
            System.out.println("############# Thank You ################\n\n");
        }

    }
}
