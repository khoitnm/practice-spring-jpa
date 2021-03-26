# Problem 1
### Problem 
``` 
Caused by: com.microsoft.sqlserver.jdbc.SQLServerException: Invalid object name 'sample_entity'
```

### Explanation
It looks like it cannot create the table `sample_entity`

# Problem 2
### Problem
```
org.springframework.transaction.CannotCreateTransactionException: Could not open JPA EntityManager for transaction; nested exception is java.lang.NullPointerException 
```

### Explanation
To make it short, that's because `SecurityContext.getOrganizationId` return null.

More detail explanation:
- The problem is JPA cannot create a transaction from a null connection
- And the connection is null because of `MultiTenantConnectionProviderImpl.getConnection(tenantId)` return null.
- And the result of that method is null because the input `tenantId` is empty.
- And that input `tenantId` is empty because of the logic inside `CurrentTenantIdentifierResolverImpl.resolveCurrentTenantIdentifier`: 
    ``` 
      public String resolveCurrentTenantIdentifier() {
        final String organizationId = SecurityContext.getOrganizationId(); //The result of it is null.
        if (organizationId != null) {
          return organizationId;
        }
        return "";
      }
    ```