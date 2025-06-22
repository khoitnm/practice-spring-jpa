/**
 * This package is used to automatically set the tenantId (organizationId) to the entity
 * before persisting it to the database (via JPA).
 * <p/>
 * This package is just an optional approach, but it is useful to ensure that
 * every entity has a tenantId set before being saved.
 * <p/>
 * Please view more in {@link org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant.jpa_auto_set_tenant.TenantEntityListener}
 */
package org.tnmk.practice_spring_jpa.pro08_multi_tenant_session_context.common.multitenant.jpa_auto_set_tenant;