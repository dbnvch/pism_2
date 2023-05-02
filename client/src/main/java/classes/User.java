package classes;

import java.io.Serializable;

public class User extends Person implements Serializable  {
    private static final long serialVersionUID = 6529685098267757690L;
    String surname;
    String name;
    String phone;
    public User(String login,String type) {this.login=login;this.type = type;}
public User(){}
//    public User(String login, String password) {
//        this.login = login;
//        this.password = password;
//    }
//public User(String login, String password,String type) {
//    this.login = login;
//    this.password = password;
//    this.type=type;
//}
public User(String surname,String name, String phone)
{
    this.surname=surname;
    this.name=name;
    this.phone=phone;

}
//    public User( String login,String type)
//    {
//        this.login=login;
//        this.type=type;
//      //  this.phone=phone;
//
//    }

//    public User(String login, String password,String type) {
//        this.login = login;
//        this.password = password;
//        this.type=type;
//    }
    public User(String surname,String name, String phone,String login,String  password)
    {
        this.surname=surname;
        this.name=name;
        this.phone=phone;
        this.login = login;
        this.password = password;
    }
    public User(String surname,String name, String phone,String login,String  password,String type)
    {
        this.surname=surname;
        this.name=name;
        this.phone=phone;
        this.login = login;
        this.password = password;
        this.type=type;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname= surname;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
