# otus-java-2017-11-ksevasilyeva

hw #09

myORM

Create a db table with fields:
```
id bigint(20) NOT NULL auto_increment
name varchar(255)
age int(3)
```

Implement ab abstract class ```DataSet``` with  ```long id```  field.
Add ```UserDataSet``` (with table fields) class and extend DataSet

Implement an ```Executor``` which saves DataSet classes to DB and reads it by Id

```
<T extends DataSet> void save(T user){…}
<T extends DataSet> T load(long id, Class<T> clazz){…}
```