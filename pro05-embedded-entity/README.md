This module shows sample code how to create `one-to-many` relationship by using `@Embeddable` annotation.
However, I don't really like this.
In this example, we also try to put a JSON column inside the embeddable table (`PersonLiving`).

# Challenges
1. Cannot put `Instant` filed into the JSON column (`LivingEvent` class) inside the embeddable table (`PersonLiving`).
2. When you defined an Embeddable table, 
you may not define the id field (then the PK is the combination of 2 fields `PersonLiving.cityId` & `PersonLiving.personId`), and it will make you unable to define the Repository for that @Embeddable entity, 
hence you cannot query on that entity.