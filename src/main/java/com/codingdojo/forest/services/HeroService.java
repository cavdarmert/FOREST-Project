package com.codingdojo.forest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.forest.models.Hero;
import com.codingdojo.forest.models.User;
import com.codingdojo.forest.repositories.HeroRepository;

@Service
public class HeroService {
	@Autowired
	 private HeroRepository heroRepository;
	
	public List<Hero> allHeroes(){
		return heroRepository.findAll();
	}
	
	public Hero myHero(User user){
		return heroRepository.findByUserId(user.getId());
	}
	
	public Hero createHero(Hero hero) {
		return heroRepository.save(hero);
	}
	
	public Hero updateHero(Hero hero) {
		return heroRepository.save(hero);
	}
	
//	public List<Item> myItems(User user){
//		return itemRepository.findByUserId(user.getId());
//	}
	
//	public List<Book> myBooks(User user){
//		return bookRepository.findById(user.getId());
//	}
	
	
//	public void deleteBook(Book book) {
//		bookRepository.delete(book);
//	}
	
//	public Book findBook(Long id) {
//		Optional<Book> optionalBook = bookRepository.findById(id);
//		if(optionalBook.isPresent()) {
//			return optionalBook.get();
//		}else {
//			return null;
//		}
//	}
	

}
