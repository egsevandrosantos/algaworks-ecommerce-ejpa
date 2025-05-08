package com.algaworks.ecommerce.hibernate;

import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.service.spi.Startable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EcmMachineMultiTenantConnectionProvider implements MultiTenantConnectionProvider, ServiceRegistryAwareService, Startable {
    private Map<String, ConnectionProvider> connectionProviders = null;
    private Map<String, Object> properties = null;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return getAnyConnectionProvider().getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        getAnyConnectionProvider().closeConnection(connection);
    }

    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        return connectionProviders.get(Objects.toString(tenantIdentifier)).getConnection();
    }

    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return getAnyConnectionProvider().supportsAggressiveRelease();
    }

    @Override
    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
        this.properties = serviceRegistry.getService(ConfigurationService.class).getSettings();
    }

    @Override
    public void start() {
        connectionProviders = new HashMap<>();

        String user = "root";
        String pass = "admin";

        // In productions, with this approach the ip is different, because is different machines

        String tenantAlgaworksEcommerceName = "algaworks_ecommerce";
        String tenantAlgaworksEcommerceIp = "localhost";

        String tenantShopEcommerceName = "shop_ecommerce";
        String tenantShopEcommerceIp = "localhost";

        String jdbcUrlAlgaworksEcommerce = createJdbcUrl(tenantAlgaworksEcommerceName, tenantAlgaworksEcommerceIp);
        String jdbcUrlShopEcommerce = createJdbcUrl(tenantShopEcommerceName, tenantShopEcommerceIp);

        configureTenant(tenantAlgaworksEcommerceName, jdbcUrlAlgaworksEcommerce, user, pass);
        configureTenant(tenantShopEcommerceName, jdbcUrlShopEcommerce, user, pass);

        this.properties = null;
    }

    private String createJdbcUrl(String tenantName, String ip) {
        return "jdbc:mysql://" + ip + "/" + tenantName + "?createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC";
    }

    private void configureTenant(String tenantName, String url, String user, String pass) {
        Map<String, Object> properties = new HashMap<>(this.properties);

        properties.put("jakarta.persistence.jdbc.url", url);
        properties.put("hibernate.connection.url", url);

        properties.put("jakarta.persistence.jdbc.user", user);
        properties.put("hibernate.connection.username", user);

        properties.put("jakarta.persistence.jdbc.password", pass);
        properties.put("hibernate.connection.password", pass);

        HikariCPConnectionProvider cp = new HikariCPConnectionProvider();
        cp.configure(properties);

        this.connectionProviders.put(tenantName, cp);
    }

    private ConnectionProvider getAnyConnectionProvider() {
        return connectionProviders.values().iterator().next();
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return getAnyConnectionProvider().isUnwrappableAs(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return getAnyConnectionProvider().unwrap(unwrapType);
    }
}
