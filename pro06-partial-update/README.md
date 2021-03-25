# References 
- Good analysis: https://medium.com/techno101/partial-updates-patch-in-spring-boot-63ff35582250
    - Avoid not null patch approach
- Guideline for simple case: https://www.baeldung.com/spring-data-partial-update


# Recommended approaches
For simple case
```
@Modifying
@Query("update Customer u set u.phone = :phone where u.id = :id")
void updatePhone(@Param(value = "id") long id, @Param(value = "phone") String phone);
```

For complicated case
