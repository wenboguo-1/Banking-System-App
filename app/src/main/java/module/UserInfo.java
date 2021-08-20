package module;

import java.io.Serializable;

public class UserInfo implements Serializable {
     private int id;
     private int age;
     private char gender;
     private String name;
     private int p_pin;
    public UserInfo(){

    }
    public UserInfo(int id, int age, char gender, int p_pin) {
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.p_pin = p_pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setP_pin(int p_pin) {
        this.p_pin = p_pin;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public char getGender() {
        return gender;
    }

    public int getP_pin() {
        return p_pin;
    }
}
