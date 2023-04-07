package com.sng;

import com.sng.util.Sentinel;

import java.io.Serializable;

public class T implements Serializable, Sentinel<T> {
    private static final T SENTINEL = new T(-1, "", "", 0, 0);

    private int code;
    private String description;
    private String supplier;
    private float price;
    private float vat;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getVat() {
        return vat;
    }

    public void setVat(float vat) {
        this.vat = vat;
    }

    public static T getClassSentinel() {
        return SENTINEL;
    }

    public T() {
        this(0, "", "", 0, 0);
    }

    public T(int code, String description, String supplier, float price, float vat) {
        this.code = code;
        this.description = description;
        this.supplier = supplier;
        this.price = price;
        this.vat = vat;
    }

    /**
     * Read Product from user input.
     * Will create a Product from user's input.
     *
     * @return product created from user's input, null if an error was encountered
     */
    public static T readFromUserInput() {
        T product = new T();
        product.setCode(0);

        System.out.println("[Product user input]");

        Integer code = null;
        while (code == null) {
            try {
                System.out.print("Code: ");
                code = Integer.parseInt(LT.readLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] input is not a number");
            }
        }
        product.setCode(code);

        System.out.print("Description: ");
        product.setDescription(LT.readLine());

        System.out.print("Supplier: ");
        product.setSupplier(LT.readLine());

        Float price = null;
        while (price == null) {
            try {
                System.out.print("Price: ");
                price = Float.parseFloat(LT.readLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] input is not a number");
            }
        }
        product.setPrice(price);

        Float vat = null;
        while (vat == null) {
            try {
                System.out.print("VAT: ");
                vat = Float.parseFloat(LT.readLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] input is not a number");
            }
        }
        product.setVat(vat);

        return product;
    }

    /**
     * Checks if the Product is a sentinel
     *
     * @return true if the Product is a sentinel, false otherwise
     */
    @Override
    public boolean isSentinel() {
        return this.code == SENTINEL.getCode();
    }

    @Override
    public T getSentinel() {
        return SENTINEL;
    }

    @Override
    public String toString() {
        return "Product{" + "code=" + code + ", description='" + description + '\'' + ", supplier='" + supplier + '\'' + ", price=" + price + ", vat=" + vat + '}';
    }
}
