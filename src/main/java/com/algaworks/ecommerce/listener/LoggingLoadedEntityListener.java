package com.algaworks.ecommerce.listener;

import jakarta.persistence.PostLoad;

public class LoggingLoadedEntityListener {
	@PostLoad
	public void logging(Object obj) {
		System.out.println("Entity " + obj.getClass().getSimpleName() + " is loaded");
	}
}
