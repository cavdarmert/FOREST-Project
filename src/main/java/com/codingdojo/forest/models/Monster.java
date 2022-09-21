package com.codingdojo.forest.models;

import java.util.Date;

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
@Table(name="monsters")
public class Monster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
	private String name;
	private String image;
	private Integer health;
	private Integer maxHealth;
	private Integer attackPower;
	private Integer defensePower;
	private Integer xpReward;
	private Integer goldReward;
	private Integer diamondReward;
	
	public Monster() {}
	
	public Monster(String name, String image, Integer health, Integer maxHealth, Integer attackPower, Integer defensePower, Integer xpReward, Integer goldReward, Integer diamondReward ) {
		this.name = name;
		this.image = image;
		this.health = health;
		this.maxHealth = maxHealth;
		this.attackPower = attackPower;
		this.defensePower = defensePower;
		this.xpReward = xpReward;
		this.goldReward = goldReward;
		this.diamondReward = diamondReward;
		
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
	
	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getHealth() {
		return health;
	}

	public void setHealth(Integer health) {
		this.health = health;
	}

	public Integer getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(Integer maxHealth) {
		this.maxHealth = maxHealth;
	}

	public Integer getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(Integer attackPower) {
		this.attackPower = attackPower;
	}

	public Integer getDefensePower() {
		return defensePower;
	}

	public void setDefensePower(Integer defensePower) {
		this.defensePower = defensePower;
	}

	public Integer getXpReward() {
		return xpReward;
	}

	public void setXpReward(Integer xpReward) {
		this.xpReward = xpReward;
	}

	public Integer getGoldReward() {
		return goldReward;
	}

	public void setGoldReward(Integer goldReward) {
		this.goldReward = goldReward;
	}

	public Integer getDiamondReward() {
		return diamondReward;
	}

	public void setDiamondReward(Integer diamondReward) {
		this.diamondReward = diamondReward;
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

}


