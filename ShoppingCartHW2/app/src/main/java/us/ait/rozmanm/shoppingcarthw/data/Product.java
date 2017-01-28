package us.ait.rozmanm.shoppingcarthw.data;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Morgan on 11/7/2016.
 */
public class Product extends SugarRecord implements Serializable {
    private String name;
    private int price;
    private boolean purchased;
    private String category;
    private String description;

    public Product() {}

    public Product(String name, int price, boolean purchased, String category, String description) {
        this.name = name;
        this.price = price;
        this.purchased = purchased;
        this.category = category;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
