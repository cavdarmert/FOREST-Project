package com.codingdojo.forest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.forest.models.Hero;

@Repository
public interface HeroRepository extends CrudRepository<Hero, Long> {
	List<Hero> findAll();
	Hero findByUserId(Long userId);


}
