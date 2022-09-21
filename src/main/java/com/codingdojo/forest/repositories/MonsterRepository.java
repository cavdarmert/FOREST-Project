package com.codingdojo.forest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.forest.models.Monster;

@Repository
public interface MonsterRepository extends CrudRepository<Monster, Long> {
	List<Monster> findAll();


}
