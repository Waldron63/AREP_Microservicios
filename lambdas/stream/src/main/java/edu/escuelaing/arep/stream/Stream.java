/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.escuelaing.arep.stream;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maritzamonsalvebautista
 */
public class Stream {
    private Long id;
    private String name;

    private List<Long> posts = new ArrayList<>();

    public Stream(){}

    public Stream(List<Long> posts){
        this.posts = posts;
    }

    public Stream(Long id, String name, List<Long> posts){
        this.id = id;
        this.posts = posts;
        this.name = name;
    }

    public Long getId(){
        return id;
    }

    public List<Long> getPosts(){
        return posts;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setUsername(List<Long> posts){
        this.posts = posts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosts(List<Long> posts) {
        this.posts = posts;
    }
}
