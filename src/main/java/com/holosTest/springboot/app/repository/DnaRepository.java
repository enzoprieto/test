package com.holosTest.springboot.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.holosTest.springboot.app.entity.Dna;

@Repository
public class DnaRepository {
	
	@Autowired
	private MongoOperations mongoOperation;
	
	public Dna guardar (Dna dna) {
		return mongoOperation.save(dna);
	}
	
	public Dna findByDna (String dna[]) {
		Query q = new Query();
		q.addCriteria(Criteria.where("dna").is(dna));
		return mongoOperation.findOne(q, Dna.class);
	}
	
	public List<Dna> findHumans () {
		Query q = new Query();
		q.addCriteria(Criteria.where("isMutant").is(false));
		return mongoOperation.find(q, Dna.class);
	}
	
	public List<Dna> findMutants () {
		Query q = new Query();
		q.addCriteria(Criteria.where("isMutant").is(true));
		return mongoOperation.find(q, Dna.class);
	}

}
