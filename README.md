# Restaurant Voting [![Codacy Badge](https://app.codacy.com/project/badge/Grade/1325ba37f28440bba62bdcd5416d162a)](https://www.codacy.com/gh/oleglunko/restaurant-voting/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=oleglunko/restaurant-voting&amp;utm_campaign=Badge_Grade)

## Graduation project for TopJava

### Spring Boot, Hibernate, Maven, H2, Swagger

###

### Task:

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

### Documentation:

> http://localhost:8080/swagger-ui.html
