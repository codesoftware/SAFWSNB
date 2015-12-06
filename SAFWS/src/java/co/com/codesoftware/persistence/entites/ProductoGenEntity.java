/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.persistence.entites;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class ProductoGenEntity implements Serializable{
	private String code;
	private int amount;
	private String name;
	private BigDecimal price;
        private BigDecimal priceIva;
	private BigDecimal totalPrice;
	private int id;
	private int type;
	private String routImage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRoutImage() {
        return routImage;
    }

    public void setRoutImage(String routImage) {
        this.routImage = routImage;
    }

    public BigDecimal getPriceIva() {
        return priceIva;
    }

    public void setPriceIva(BigDecimal priceIva) {
        this.priceIva = priceIva;
    }
        
        
    
}
