package com.codingdojo.forest.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=3, message="Title must be at least 3 characters long")
	private String name;
	
	@Size(min=3, message="Author must be at least 3 characters long")
	private Integer price;
	
	@Size(min=3, message="Author must be at least 3 characters long")
	private String category;
	
	@Size(min=3, message="Author must be at least 3 characters long")
	private Integer healingPercent;
	
	@Size(min=3, message="Author must be at least 3 characters long")
	private Integer attackPowerAddition;
	
	@Size(min=3, message="Author must be at least 3 characters long")
	private Integer defensePowerAddition;
	
	@Size(min=3, message="Author must be at least 3 characters long")
	private Integer criticalChanceAddition;
	
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "heroes_items", 
        joinColumns = @JoinColumn(name = "item_id"), 
        inverseJoinColumns = @JoinColumn(name = "hero_id")
    )
    private List<Hero> heroes;
    
    public Item() {}
    
    public Item(String name, Integer price, String category, Integer healingPercent, Integer attackPowerAddition, Integer defensePowerAddition, Integer criticalChanceAddition) {
		this.name = name;
		this.price = price;
		this.category = category;
		this.healingPercent = healingPercent;
		this.attackPowerAddition = attackPowerAddition;
		this.defensePowerAddition = defensePowerAddition;
		this.criticalChanceAddition = criticalChanceAddition;
		
//		hero id or something? 
				
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getHealingPercent() {
		return healingPercent;
	}

	public void setHealingPercent(Integer healingPercent) {
		this.healingPercent = healingPercent;
	}

	public Integer getAttackPowerAddition() {
		return attackPowerAddition;
	}

	public void setAttackPowerAddition(Integer attackPowerAddition) {
		this.attackPowerAddition = attackPowerAddition;
	}

	public Integer getDefensePowerAddition() {
		return defensePowerAddition;
	}

	public void setDefensePowerAddition(Integer defensePowerAddition) {
		this.defensePowerAddition = defensePowerAddition;
	}

	public Integer getCriticalChanceAddition() {
		return criticalChanceAddition;
	}

	public void setCriticalChanceAddition(Integer criticalChanceAddition) {
		this.criticalChanceAddition = criticalChanceAddition;
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

	public List<Hero> getHeroes() {
		return heroes;
	}

	public void setHeroes(List<Hero> heroes) {
		this.heroes = heroes;
	}

}
