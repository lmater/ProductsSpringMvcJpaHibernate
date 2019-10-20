package com.lmater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.lmater.dao.ProduitRepository;
import com.lmater.entities.Produit;

@SpringBootApplication
public class CatMvc4Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CatMvc4Application.class, args);
		ProduitRepository produitRepository = ctx.getBean(ProduitRepository.class);
		produitRepository.save(new Produit("unic 1", 110.3, 3));
		produitRepository.save(new Produit("impr 1", 110, 10));
		produitRepository.save(new Produit("scan 1", 116, 8));
		produitRepository.save(new Produit("ecra 1", 150, 5));
		produitRepository.save(new Produit("phone 1", 99, 5));
		produitRepository.save(new Produit("Iphone 1", 98, 5));
		produitRepository.findAll().forEach(p -> System.out.println(p.getDesignation()));
	}

}
