package com.ngtszlong.eztryclothes_company.Clothes;

public class ClothesList {
    private String Image;
    private String Name_Chi;
    private String Name_Eng;
    private String uid;
    private String No;

    public ClothesList(String image, String name_Chi, String name_Eng, String uid, String no) {
        Image = image;
        Name_Chi = name_Chi;
        Name_Eng = name_Eng;
        this.uid = uid;
        No = no;
    }

    public ClothesList() {
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }
}
