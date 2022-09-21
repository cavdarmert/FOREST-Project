package com.codingdojo.forest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.forest.models.Game;
import com.codingdojo.forest.repositories.GameRepository;
@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;
	
	public List<Game> allGames(){
		return gameRepository.findAll();
	}
	
	public Game findGame(Long id) {
		Optional<Game> optionalGame = gameRepository.findById(id);
		if(optionalGame.isPresent()) {
			return optionalGame.get();
		}else {
			return null;
		}
	}

}
