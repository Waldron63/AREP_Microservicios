/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.escuelaing.arep.user;

/**
 *
 * @author maritzamonsalvebautista
 */
public class User {
    
    private Long id;
    private String name;
    private String password;

    public User(){}

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public User(Long id,String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Long getId(){
        return id;
    }

    public String geName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
