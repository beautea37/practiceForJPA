package jpa.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemForm {

    //공통 값
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    //Book
    private String author;
    private String isbn;
    
    //Album
    private String artist;
    private String etc;



}
