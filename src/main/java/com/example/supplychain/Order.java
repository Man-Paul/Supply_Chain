package com.example.supplychain;

public class Order {

    public static boolean placeOrder(Product product, String customerEmail){
        String orderQuery = String.format("INSERT INTO orders (quantity,customer_id,product_id,status) VALUES(1, (SELECT cid FROM customer WHERE email='%s'), %s, 'ORDERED')",
                customerEmail,product.getId());
        DatabaseConnection dbconn = new DatabaseConnection();
        if(dbconn.insertOrderData(orderQuery)>=1)
            return true;
        System.out.println(orderQuery);
        return false;
    }


}
