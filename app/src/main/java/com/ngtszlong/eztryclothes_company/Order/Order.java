package com.ngtszlong.eztryclothes_company.Order;

public class Order {
    private String companyuid;
    private String uid;
    private String no;
    private String image;
    private String name;
    private String quantity;
    private String price;
    private String discount;
    private String str;
    private String date;
    private String address;

    public Order(String companyuid, String uid, String no, String image, String name, String quantity, String price, String discount, String str, String date, String address) {
        this.companyuid = companyuid;
        this.uid = uid;
        this.no = no;
        this.image = image;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.str = str;
        this.date = date;
        this.address = address;
    }

    public Order() {
    }

    public String getCompanyuid() {
        return companyuid;
    }

    public void setCompanyuid(String companyuid) {
        this.companyuid = companyuid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
