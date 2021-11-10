package entities;

import org.jetbrains.annotations.NotNull;

public class WaybillPosition  {

    private int id;
    private int waybill;
    private int price;
    private int product;
    private int amount;

    public WaybillPosition(int id, int waybill, int price, int product, int amount) {
        this.id = id;
        this.waybill = waybill;
        this.price = price;
        this.product = product;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWaybill() {
        return waybill;
    }

    public void setWaybill(int waybill) {
        this.waybill = waybill;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
