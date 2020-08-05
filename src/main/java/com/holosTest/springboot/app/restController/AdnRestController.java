package com.holosTest.springboot.app.restController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.holosTest.springboot.app.service.AdnService;

import ch.qos.logback.core.db.dialect.MySQLDialect;

@RestController
public class AdnRestController {
	
	@Autowired
	AdnService adnService;
	
	@PostMapping(value="/api/mutant", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validar (@RequestBody Map<String, String[]> dna){
		Map<String, String> response = new HashMap<String, String>();
				
		Boolean isMutant;
		try {
			isMutant = adnService.isMutant(dna.get("dna"));
			
			if (isMutant == true) {
				response.put("success", isMutant.toString());
				return new ResponseEntity<>(response, HttpStatus.OK);
				
			}else {
				response.put("success", isMutant.toString());
				return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
			}
			
		} catch (Exception e) {
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		}
		
		
	}
	
	@GetMapping(value= "/api/stats", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> stats (){
		Map<String, String> response = new HashMap<String, String>();
		Integer humans = adnService.countHumans();
		Integer mutants = adnService.countMutants();
		Double ratio = 0.0;
		if(humans != 0) {
			ratio =  (double) (mutants / humans);
		}
		response.put("count_mutant_dna", humans.toString());
		response.put("count_human_dna", mutants.toString());
		response.put("ratio" , ratio.toString());
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
