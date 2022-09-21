package com.codingdojo.forest.models;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="games")
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    private String name;
    private String priceType;
    private String rewardType;
	private Integer percentChance;
	private Integer ticketPrice;
	private Integer rewardAmount;
	
public Game() {}
	
	public Game(String name, String priceType,String rewardType, Integer percentChance, Integer ticketPrice, Integer rewardAmount) {
		this.name = name;
		this.priceType = priceType;
		this.rewardType = rewardType;
		this.percentChance = percentChance;
		this.ticketPrice = ticketPrice;
		this.rewardAmount = rewardAmount;
		
	}
	
	public Boolean playGoldtoGold10PercentChance (Hero hero) {
		Integer ticket = new Random().nextInt(100/this.percentChance);
		hero.setGold(hero.getGold() - this.ticketPrice);

		if (ticket==0) {
			hero.setGold(hero.getGold() + this.rewardAmount);
			return true;
			
		} else return false; 
	}
	
	public Boolean playGoldToGold50PercentChance (Hero hero) {
		Integer ticket = new Random().nextInt(100/this.percentChance);
		hero.setGold(hero.getGold() - this.ticketPrice);

		if (ticket==0) {
			hero.setGold(hero.getGold() + this.rewardAmount);
			return true;
			
		} else return false; 
	}
	
	public Boolean playDiamondToGold25PercentChance (Hero hero) {
		Integer ticket = new Random().nextInt(100/this.percentChance);
		hero.setDiamond(hero.getDiamond() - this.ticketPrice);

		if (ticket==0) {
			hero.setGold(hero.getGold() + this.rewardAmount);
			return true;
			
		} else return false; 
	}
	
	public Boolean playGoldToDiamond25PercentChance (Hero hero) {
		Integer ticket = new Random().nextInt(100/this.percentChance);
		hero.setGold(hero.getGold() - this.ticketPrice);

		if (ticket==0) {
			hero.setDiamond(hero.getDiamond() + this.rewardAmount);
			return true;
			
		} else return false; 
	}
	
	
	
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPercentChance() {
		return percentChance;
	}

	public void setPercentChance(Integer percentChance) {
		this.percentChance = percentChance;
	}

	public Integer getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Integer ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Integer getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(Integer rewardAmount) {
		this.rewardAmount = rewardAmount;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getRewardType() {
		return rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
    
	

}
