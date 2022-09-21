package com.codingdojo.forest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.forest.models.Hero;
import com.codingdojo.forest.models.Item;
import com.codingdojo.forest.repositories.ItemRepository;

@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> allItems(){
		return itemRepository.findAll();
	}
	
	
	public List<Item> myItems(Hero hero){
		return itemRepository.findByHeroes(hero);
	}
	
	
	
	
	
//	public List<Item> myItems(User user){
//		return itemRepository.findByUserId(user.getId());
//	}
	
	public Item findItem(Long id) {
		Optional<Item> optionalItem = itemRepository.findById(id);
		if(optionalItem.isPresent()) {
			return optionalItem.get();
		}else {
			return null;
		}
	}


}
