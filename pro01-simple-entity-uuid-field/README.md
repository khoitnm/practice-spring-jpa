# Important Note
One important note when working with UUID field in MySQL and Spring Data.

By default, Spring will store UUID field as `BINARY(255)`.
However, it will cause a problem that we won't be able to execute `findBy some UUID field()`, it will return empty list/null item.
That's why we have to config `BINARY(16)` in the column definition.
