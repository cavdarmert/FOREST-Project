package com.codingdojo.forest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.forest.models.Monster;
import com.codingdojo.forest.repositories.MonsterRepository;

@Service
public class MonsterService {
	@Autowired
	private MonsterRepository monsterRepository;
	
	public List<Monster> allMonsters(){
		return monsterRepository.findAll();
	}
	
	public Monster findMonster(Long id) {
		Optional<Monster> optionalMonster = monsterRepository.findById(id);
		if(optionalMonster.isPresent()) {
			return optionalMonster.get();
		}else {
			return null;
		}
	}

}
