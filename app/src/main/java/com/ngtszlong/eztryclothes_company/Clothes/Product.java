package com.ngtszlong.eztryclothes_company.Clothes;

public class Product {
    private String No;
    private String Company;
    private String Name_Chi;
    private String Name_Eng;
    private String Gender;
    private String Type;
    private String Color_Chi;
    private String Color_Eng;
    private String Image;
    private String XL;
    private String L;
    private String M;
    private String S;
    private String XS;
    private String Description_Chi;
    private String Description_Eng;
    private String Material_Chi;
    private String Material_Eng;
    private String Quantity;
    private String Price;
    private String Discount;
    private String try_photo;
    private String uid;
    private String ReleaseDate;

    public Product(String no, String company, String name_Chi, String name_Eng, String gender, String type, String color_Chi, String color_Eng, String image, String XL, String l, String m, String s, String XS, String description_Chi, String description_Eng, String material_Chi, String material_Eng, String quantity, String price, String discount, String try_photo, String uid, String releaseDate) {
        No = no;
        Company = company;
        Name_Chi = name_Chi;
        Name_Eng = name_Eng;
        Gender = gender;
        Type = type;
        Color_Chi = color_Chi;
        Color_Eng = color_Eng;
        Image = image;
        this.XL = XL;
        L = l;
        M = m;
        S = s;
        this.XS = XS;
        Description_Chi = description_Chi;
        Description_Eng = description_Eng;
        Material_Chi = material_Chi;
        Material_Eng = material_Eng;
        Quantity = quantity;
        Price = price;
        Discount = discount;
        this.try_photo = try_photo;
        this.uid = uid;
        ReleaseDate = releaseDate;
    }

    public Product() {
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getName_Chi() {
        return Name_Chi;
    }

    public void setName_Chi(String name_Chi) {
        Name_Chi = name_Chi;
    }

    public String getName_Eng() {
        return Name_Eng;
    }

    public void setName_Eng(String name_Eng) {
        Name_Eng = name_Eng;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getColor_Chi() {
        return Color_Chi;
    }

    public void setColor_Chi(String color_Chi) {
        Color_Chi = color_Chi;
    }

    public String getColor_Eng() {
        return Color_Eng;
    }

    public void setColor_Eng(String color_Eng) {
        Color_Eng = color_Eng;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getXL() {
        return XL;
    }

    public void setXL(String XL) {
        this.XL = XL;
    }

    public String getL() {
        return L;
    }

    public void setL(String l) {
        L = l;
    }

    public String getM() {
        return M;
    }

    public void setM(String m) {
        M = m;
    }

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }

    public String getXS() {
        return XS;
    }

    public void setXS(String XS) {
        this.XS = XS;
    }

    public String getDescription_Chi() {
        return Description_Chi;
    }

    public void setDescription_Chi(String description_Chi) {
        Description_Chi = description_Chi;
    }

    public String getDescription_Eng() {
        return Description_Eng;
    }

    public void setDescription_Eng(String description_Eng) {
        Description_Eng = description_Eng;
    }

    public String getMaterial_Chi() {
        return Material_Chi;
    }

    public void setMaterial_Chi(String material_Chi) {
        Material_Chi = material_Chi;
    }

    public String getMaterial_Eng() {
        return Material_Eng;
    }

    public void setMaterial_Eng(String material_Eng) {
        Material_Eng = material_Eng;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getTry_photo() {
        return try_photo;
    }

    public void setTry_photo(String try_photo) {
        this.try_photo = try_photo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }
}
