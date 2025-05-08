package com.algaworks.ecommerce.ddl;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ExecuteDDLTests {
	@Test
	public void testExecuteDDL() {
		Map<String, String> overwriteProperties = new HashMap<>();

		overwriteProperties.put("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost/shop_ecommerce?createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC");
		
		// <!-- <property name="jakarta.persistence.schema-generation.database.action" value="drop-and-create"/> -->
		overwriteProperties.put("jakarta.persistence.schema-generation.database.action", "drop-and-create");

		// <!-- <property name="jakarta.persistence.schema-generation.create-source" value="metadata-then-script"/> -->
		// <!-- <property name="jakarta.persistence.schema-generation.drop-source" value="metadata-then-script"/> -->
		overwriteProperties.put("jakarta.persistence.schema-generation.create-source", "metadata-then-script");
		overwriteProperties.put("jakarta.persistence.schema-generation.drop-source", "metadata-then-script");
		
        // <!-- <property name="jakarta.persistence.schema-generation.create-script-source" value="META-INF/database/create-metadata-then-script.sql"/> -->
        // <!-- <property name="jakarta.persistence.schema-generation.drop-script-source" value="META-INF/database/drop-metadata-then-script.sql"/> -->
		overwriteProperties.put("jakarta.persistence.schema-generation.create-script-source", "META-INF/database/create-metadata-then-script.sql");
		overwriteProperties.put("jakarta.persistence.schema-generation.drop-script-source", "META-INF/database/drop-metadata-then-script.sql");
		
		// <!-- <property name="jakarta.persistence.sql-load-script-source" value="META-INF/database/seeds.sql"/> -->
		overwriteProperties.put("jakarta.persistence.sql-load-script-source", "META-INF/database/seeds.sql");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU", overwriteProperties);
		entityManagerFactory.close();
	}
}
