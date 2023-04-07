package com.sng;

import java.io.Serializable;

public class Contact implements Serializable {
    public static final Contact SENTINEL = new Contact(-1, "", "", "", "", -1);

    private int code;
    private String name;
    private String address;
    private String phone;
    private String email;
    private int age;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Contact(int code, String name, String address, String phone, String email, int age) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.age = age;
    }

    public Contact() {
    }

    @Override
    public String toString() {
        return "Contact{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }

    public boolean isSentinel() {
        return this.code == SENTINEL.getCode();
    }
}
