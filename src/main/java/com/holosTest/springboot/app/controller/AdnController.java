package com.holosTest.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.holosTest.springboot.app.service.AdnService;


@Controller
public class AdnController {
	
	@Autowired
	private AdnService adnService;
	
	@RequestMapping("/")
	public String home (){
		return "home";
	}
	
	@GetMapping("/ismutant")
	public String ismutant (@RequestParam String dna[], RedirectAttributes flash) {
		
		Integer cantidadSecuencias = 0;
		Boolean isMutant;

		try {
			adnService.validarCaracteres(dna);
			cantidadSecuencias = adnService.validarFilas(dna);
			cantidadSecuencias = adnService.validarColumnas(dna, cantidadSecuencias);
			cantidadSecuencias = adnService.validarDiagonal(dna, cantidadSecuencias);
			if(cantidadSecuencias > 1) {
				isMutant = true;
				adnService.save(dna,isMutant);
				flash.addFlashAttribute("success", "Es mutante!! Cantidad de secuencias encontradas: "+cantidadSecuencias.toString());
			}else {
				isMutant = false;
				adnService.save(dna,isMutant);
				flash.addFlashAttribute("success", "Es Humano!! Cantidad de secuencias encontradas: "+cantidadSecuencias.toString());
			}
			
			
		} catch (Exception e) {
			flash.addFlashAttribute("error", "Error: " + e.getMessage());
		}
		
		return "redirect:/";
		
	}
	
	@GetMapping("/stats")
	public String stats (RedirectAttributes flash) {
		
		Integer humans = adnService.countHumans();
		Integer mutants = adnService.countMutants();
		Double ratio = 0.0;
		if(humans != 0) {
			ratio =  (double) (mutants / humans);
		}
		flash.addFlashAttribute("count_mutant_dna", humans.toString());
		flash.addFlashAttribute("cout_human_dna", mutants.toString());
		flash.addFlashAttribute("ratio" , ratio.toString());
		
		return "redirect:/";
	}
	
	

}
