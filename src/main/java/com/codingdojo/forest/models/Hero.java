package com.codingdojo.forest.models;

import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="heroes")
public class Hero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private Integer level;
	
//	@Size(min=3, message="Author must be at least 3 characters long")
//	private Boolean isTimeToLevelUp;
	
	private Integer experience;
		
	private Integer targetExperience;
	
	private Integer gold;
	
	private Integer diamond;
	
	private Integer health;
	
	private Integer maxHealth;
	
	private Integer attackPower;
	
	private Integer defensePower;
	
	private Integer criticalChance;
	
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "heroes_items", 
        joinColumns = @JoinColumn(name = "hero_id"), 
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;
    
    public Hero() {}
	
	public Hero(User user) {
		this.name = user.getUserName();
		this.level=1;
		this.experience = 0;
		this.targetExperience = this.nextLevelXpAmount();
		this.gold = 200;
		this.diamond = 0;
		this.health = 100;
		this.maxHealth = 100;
		this.attackPower = 30;
		this.defensePower = 20;
		this.criticalChance = 0;
		this.user = user;
		
	}
	
	public Integer armorDefenseSum () {
		Integer sumArmorDefense = 0;
		for (int i = 0; i < items.size(); i++) {
			sumArmorDefense += (items.get(i).getDefensePowerAddition());
        }
		return sumArmorDefense;
	}
	
	public Integer weaponAttackSum () {
		Integer sumWeaponAttack = 0;
		for (int i = 0; i < items.size(); i++) {
			sumWeaponAttack += (items.get(i).getAttackPowerAddition());
        }
		return sumWeaponAttack;
	}
	
	public Integer criticalChanceSum () {
		Integer sumCriticalChance = 0;
		for (int i = 0; i < items.size(); i++) {
			sumCriticalChance += (items.get(i).getCriticalChanceAddition());
        }
		return sumCriticalChance;
	}
	
	public Boolean canAddItemToInventory (Item item) {
		Integer goldBuffertoSurvive = 200;
		if (item.getCategory().equals("pot") && this.gold >= item.getPrice()) {
			return true;
			
		}
		for (int i = 0; i < items.size(); i++) {
			if (item.getCategory().equals(items.get(i).getCategory()) || (this.gold - goldBuffertoSurvive) < item.getPrice()) {;
				return false;
			}
        }
		return true;		
	}
	
	public Item addItemToInventory (Item item) {
		if (this.canAddItemToInventory(item) == true) {
			this.gold = this.gold - item.getPrice();
			items.add(item);
			return item;
			
		}
		else return item;
		
	}
	
	public Item removeItemFromInventory (Item item) {
		items.remove(item);
		return item;
		
	}
	
	
	
	
	public Boolean attackToMonster(Monster monster, ArrayList<String> actions) {
			actions.clear();
			
			while(this.health>0 && monster.getHealth()>0) {
				
				monster.setHealth( (int) (monster.getHealth() + monster.getDefensePower()*0.2 - (this.attackPower + this.weaponAttackSum())));
				this.health= (int) (this.health + (this.defensePower + this.armorDefenseSum())*0.2 - monster.getAttackPower());
				
				String damageDealt = (int)(this.attackPower + this.weaponAttackSum() - monster.getDefensePower()*0.2) + " ";
				String damageTaken = (int)(monster.getAttackPower() - (this.defensePower + this.armorDefenseSum())*0.2) + " ";
				String actionsSum = "You DEALT " + damageDealt + "damage AND TAKEN " + damageTaken + "damage";
				actions.add(actionsSum);
				

			}
			
			if (monster.getHealth()<=0 && this.health>0 ) {
				
				
				System.out.println(monster.getHealth());
				this.gold = this.gold + monster.getGoldReward();
				this.experience = this.experience + monster.getXpReward();

				
				monster.setHealth(monster.getMaxHealth());
				

				return true;
			}
			
			else {
				this.health = 0;
				this.gold = this.gold - monster.getXpReward() / 2;
				monster.setHealth(monster.getMaxHealth());
				return false;
				
			}
			
		}
		
	
	public Integer nextLevelXpAmount() {
		Double exponent = 1.4;
		Double xpBase = 50.0;
		Integer totalXPP =0;
		
		for (int level = 1; level < this.level+1; level++) {
			totalXPP += (int)(xpBase *  Math.pow(level + 1, exponent));
								 			 
		}
		return totalXPP;
	}
	
	public Boolean isTimeToLevelUp() {
		if (this.experience >= this.nextLevelXpAmount()) {
			return true;
		} 
		else return false;
	}
	
	public Boolean levelUpActions() {
		if (this.isTimeToLevelUp()==true) {
		
			this.level = (this.level + 1);
			this.gold = (int)(this.gold + 100 * Math.pow(this.level, 1.3));
			this.diamond = (this.diamond + 1);
			this.maxHealth = ((int)(this.maxHealth * 1.11));
			this.health = ((int)(this.health + this.maxHealth * 0.12));
			this.attackPower = ((int)(this.attackPower * 1.11));
			this.defensePower = ((int)(this.defensePower * 1.07));
			this.criticalChance = ((int)(this.criticalChance + 3));
			
			return true;
		}
		
		else {
			return false;
		}

	}
	
	public Boolean healHeroWithGold() {
		if (this.gold > 20 && (this.health<this.maxHealth)) {
			this.gold = this.gold - 20;
			this.health = this.maxHealth;
			return true;
		}
		else return false;
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
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public Integer getTargetExperience() {
		return targetExperience;
	}

	public void setTargetExperience(Integer targetExperience) {
		this.targetExperience = targetExperience;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Integer getDiamond() {
		return diamond;
	}

	public void setDiamond(Integer diamond) {
		this.diamond = diamond;
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
	
	public Integer getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(Integer criticalChance) {
		this.criticalChance = criticalChance;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
