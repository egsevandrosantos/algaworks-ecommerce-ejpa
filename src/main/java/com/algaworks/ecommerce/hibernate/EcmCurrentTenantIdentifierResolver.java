package com.algaworks.ecommerce.hibernate;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class EcmCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    // In a web system, each request is a thread
    // So, ThreadLocal separate by request (thread)
    private static final ThreadLocal<String> t1 = new ThreadLocal<>();

    public static void setTenantIdentifier(String tenantId) {
        t1.set(tenantId);
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return t1.get();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
