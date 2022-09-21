package com.codingdojo.forest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.forest.models.Hero;
import com.codingdojo.forest.models.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
	List<Item> findAll();
//	List<Item> findByUser(User user);
	
	
	List<Item> findByHeroes(Hero hero);



}
