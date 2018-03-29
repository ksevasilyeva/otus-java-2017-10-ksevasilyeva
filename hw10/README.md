# otus-java-2017-11-ksevasilyeva

hw #10

Hibernate ORM

using the previous hw (myORM):
1. refactor your solution: ```DBService (interface DBService, class DBServiceImpl, UsersDAO, UsersDataSet, Executor)```
2. implement ```DBServiceHibernateImpl``` (do not change ```DBSerivice```)
3. add fields to ```UsersDataSet```:
address (OneToOne)
```
class AddressDataSet{
    private String street;
}
```
and phone* (OneToMany)
```
class PhoneDataSet{
    private String number;
}
```


