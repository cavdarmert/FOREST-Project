package com.codingdojo.forest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.forest.models.Game;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
	List<Game> findAll();

}
