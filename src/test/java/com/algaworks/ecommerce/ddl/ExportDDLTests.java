package com.algaworks.ecommerce.ddl;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ExportDDLTests {
	@Test
	public void testExport() {
		Map<String, String> overwriteProperties = new HashMap<>();
		
		// <!-- <property name="jakarta.persistence.schema-generation.scripts.action" value="drop-and-create"/> -->
        // <!-- <property name="jakarta.persistence.schema-generation.scripts.create-target" value="src/main/resources/META-INF/database/exported-create-script.sql"/> -->
        // <!-- <property name="jakarta.persistence.schema-generation.scripts.drop-target" value="src/main/resources/META-INF/database/exported-drop-script.sql"/> -->
		overwriteProperties.put("jakarta.persistence.schema-generation.scripts.action", "drop-and-create");
		overwriteProperties.put("jakarta.persistence.schema-generation.scripts.create-target", "/tmp/exported-create-script.sql");
		overwriteProperties.put("jakarta.persistence.schema-generation.scripts.drop-target", "/tmp/exported-drop-script.sql");
		
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
