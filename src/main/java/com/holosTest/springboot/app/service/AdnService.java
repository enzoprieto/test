package com.holosTest.springboot.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holosTest.springboot.app.entity.Dna;
import com.holosTest.springboot.app.repository.DnaRepository;

@Service
public class AdnService {
	
	@Autowired
	private DnaRepository dnaRepository;

	public void validarCaracteres (String adn[]) throws Exception {
		String caracter;
		Integer acumulador = adn[0].length();;
		for (int i = 0; i < adn.length; i++) {
			
			if (acumulador < adn[i].length() || acumulador > adn[i].length()) {
				throw new Exception("La cadena: " + i + 1 + " No tiene el mismo largo que el resto.");
			}
			acumulador = adn[i].length();
			for (int z = 0; z < adn[i].length(); z++) {
				caracter = adn[i].substring(z, z + 1).toUpperCase();
				if (!caracter.equals("A") && !caracter.equals("T") && !caracter.equals("C") && !caracter.equals("G")) {
					throw new Exception("Caracter: " + caracter + " no valido.");
				}
			}

		}
	}
	
	public Integer validarFilas(String adn[]) {

		String caracter = "";
		String acumulador = "";
		Integer contadorMatch = 0;
		Integer cantidadSecuencias = 0;
		for (int i = 0; i < adn.length; i++) {
			for (int z = 0; z < adn[i].length(); z++) {
				caracter = adn[i].substring(z, z + 1);
				if (acumulador.equals(caracter)) {
					contadorMatch = contadorMatch + 1;
					if (contadorMatch >= 3) {
						cantidadSecuencias = cantidadSecuencias + 1;
					}
				}else {
					contadorMatch = 0;
				}
				acumulador = caracter;
			}
			
			contadorMatch = 0;
			acumulador = "";
		}
		return cantidadSecuencias;
	}
	
	public Integer validarColumnas(String adn[], Integer cantidad) {
		
		String caracter = "";
		String acumulador = "";
		Integer contadorMatch = 0;
		Integer cantidadSecuencias = cantidad;
		
		for(int i = 0; i < adn[0].length(); i++) {
			for (int z = 0; z < adn.length; z++) {
				caracter = adn[z].substring(i, i + 1);
				if (acumulador.equals(caracter)) {
					contadorMatch = contadorMatch + 1;
					if (contadorMatch >= 3) {
						cantidadSecuencias = cantidadSecuencias + 1;
					}
				}else {
					contadorMatch = 0;
				}
				acumulador = caracter;
			}
			contadorMatch = 0;
			acumulador = "";
		}
		
		return cantidadSecuencias;

	}
	
	public Integer validarDiagonal(String adn[], Integer cantidad) {
		
		String caracter = "";
		String acumulador = "";
		Integer contadorMatch = 0;
		Integer cantidadSecuencias = cantidad;
		Integer x = 0;
		for(int i = 0; i < adn[0].length(); i++) {
			x = i;
			for (int z = 0; z < adn.length; z++) {
					if(x < adn[0].length()) {
						caracter = adn[z].substring(x, x + 1);
						if (acumulador.equals(caracter)) {
							contadorMatch = contadorMatch + 1;
							if (contadorMatch >= 3) {
								cantidadSecuencias = cantidadSecuencias + 1;
							}
						}else {
							contadorMatch = 0;
						}
						acumulador = caracter;
					}
				x++;
			}
			contadorMatch = 0;
			acumulador = "";
			
		}
		
		
		return cantidadSecuencias;
	}
	
	public Boolean isMutant (String dna[]) throws Exception {
		Boolean isMutant = false;
		Integer cantidadSecuencias = 0;
		
		validarCaracteres(dna);
		cantidadSecuencias = validarFilas(dna);
		cantidadSecuencias = validarColumnas(dna, cantidadSecuencias);
		cantidadSecuencias = validarDiagonal(dna, cantidadSecuencias);
		if(cantidadSecuencias > 1) {
			isMutant = true;
		}
		
		save(dna, isMutant);
		
		return isMutant;
	}
	
	public Dna findByDna (String dna[]) {
		return dnaRepository.findByDna(dna);
	}
	
	public Dna save (String dna[], Boolean isMutant) throws Exception {
		
		Dna adn = new Dna();
		adn.setDna(dna);
		adn.setIsMutant(isMutant);
		
		if(findByDna(adn.getDna()) == null) {
			dnaRepository.guardar(adn);
		}else {
			throw new Exception("Esa cadena ya se encuentra en la base de datos");
		}
		
		return adn;
	}
	
	public Integer countHumans () {
		return dnaRepository.findHumans().size();
	}
	
	public Integer countMutants () {
		return dnaRepository.findMutants().size();
	}

}
