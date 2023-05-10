package org.cdl;

import org.cdl.object.BookingItem;
import org.cdl.object.Product;
import org.cdl.object.ShoppingBasket;
import org.cdl.service.CartService;
import org.cdl.service.CartServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author deshan
 * @since 1.0
 */
public class Main {
    private static Map<String, Product> productMap = new HashMap<>();

    public static void main(String[] args) {
        CartService cartService = new CartServiceImpl(new HashMap<>());

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("################ welcome ##################");

            System.out.print("Do you need to setup price schemes(Y/N): ");
            String isSetup = scanner.nextLine();
            while ("Y".equals(isSetup)) {
                System.out.print("Enter the product code(A,B, C,...): ");
                String productCode = scanner.nextLine();
                System.out.print("Enter the unit price: ");
                Double unitPrice = Double.valueOf(scanner.nextLine());
                Product product = new Product(productCode);
                product.setUnitPrice(unitPrice);
                productMap.put(productCode, product);
                System.out.print("Do you need to setup price schemes(Y/N): ");
                isSetup = scanner.nextLine();
            }

            ShoppingBasket shoppingBasket = cartService.createShoppingBasket();

            System.out.println("Enter the product code of the item(Enter END to complete the transaction): ");
            String itemCode = scanner.nextLine();
            while (!"END".equals(itemCode)) {
                Product product = productMap.get(itemCode);
                BookingItem bookingItem = new BookingItem(product);
                bookingItem.addQuantity(1);
                shoppingBasket = cartService.addItem(shoppingBasket.getSessionId(), bookingItem);
                System.out.println("Running Total : " + shoppingBasket.getTotalPrice());
                System.out.println("Enter the product code of the item(Enter END to complete the transaction): ");
                itemCode = scanner.nextLine();
            }
            System.out.println("The Total Amount To Pay: " + shoppingBasket.getTotalPrice());
            System.out.println("############# Thank You ################\n\n");

        }


    }
}
