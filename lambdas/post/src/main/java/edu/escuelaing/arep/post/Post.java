package edu.escuelaing.arep.post;

public class Post {
    private Long id;
    private String message;
    private int likes;
    private Long user;

    public Post(){
    }

    public Post(String message,Long user){
        this.message = message;
        this.user = user;
    }

    public Post(Long id, String message,int likes, Long user){
        this.id = id;
        this.message = message;
        this.likes = likes;
        this.user = user;
    }


    public Long getId(){
        return id;
    }

    public String getMessage(){
        return message;
    }

    public int getLikes(){
        return likes;
    }

    public Long getUser(){
        return user;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setMessage(String message){
        this.message = message;

    }

    public void setLikes(int likes){
        this.likes = likes;
    }

    public void setUser(Long user){
        this.user = user;
    }

}