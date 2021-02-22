# Custom date and time (de)serializer

### Description
This is simple example of custom serialization and deserialization of LocalDateTime class using Spring.

Assume the frequent situation: we have to receive dates from clients who are in different time zones, process their requests and save data to database using server timezone. To get rid of necessary calls of methods to transform date from one time zone to another, we can entrust this case to spring by writing our own mechanism of converting dates.

### Example
Suppose our server's time zone is UTC+00. Client sends us date `2020-02-22T10:00:00+05:00`. In our controller we don't need to call any methods to transform date to server time zone, and we'll already work with date `2020-02-22T05:00:00`, because time of our server is 5 hours less.

We also don't need to customize date while sending response. It'll be automatically transformed and when we get date `2020-02-22T10:00` from database, client will see `2020-02-22T10:00+00:00` format 