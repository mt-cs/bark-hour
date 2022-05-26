BarkHour 
===========================================
*Ticket Purchase Web Application*

<img width="1359" alt="barkoursc" src="https://user-images.githubusercontent.com/60201466/170530690-ee45683d-9118-4af5-b803-962f2dbc3a57.png">

**Looking for dog-friendly events and things to do with your dog?**

BarkHour is an EventBrite web application for dog people in San Francisco. This project implements a two-tier ticket purchase web application with a Java (Jetty/Servlets) front end and an SQL backend. BarkHour uses [Thymeleaf](https://www.thymeleaf.org/) to generate HTML. It's also supported with Javascript, CSS, and Bootstrap templates.

## Running Application

Run `BarkourApp.java` as SpringBoot application.

## Features

| Feature         | Description |
| :-------------:| :-----|
| Login with ... and logout | Authenticate users through Slack, provide a signout option and maintain user state appropriately.|
|User account | Allow a user to view and update personal user account information | 
| View user transactions | Display *details* for all events for which the user has purchased tickets. |
| View events | Display a list of all events. |
| View event | Display details for a specific event. |
| Create event | Allow the user to create a new event by entering all appropriate detail. |
| Modify/delete event | Allow a user to modify or delete an event *that s/he has created*.|
| Search | Allow a user to search events for particular phrases or other features. |
| Show *n* events per page | Provide pagination to allow a user to see some specific number of events per page and scroll to the next page. |
| Purchase tickets | Allow the user to purchase tickets for an event. |
| Transfer tickets | Allow the user to transfer tickets to another user. |
| Transaction History | Keep track and displays transaction history |
| SQL DB | A roobust relational database to store *user account*, *event*, and *transaction* data.|

## Dependencies
- MySql
- Spring Boot
- Thymeleaf
- Gson

## Configuration

To modify the SQL database source, we can update:
1. `DBManager.java`
```
  static {
    ds.setUrl("jdbc:mysql://localhost:3306/user026");
    ds.setUsername("user026");
    ds.setPassword("user026");
    ds.setMinIdle(MIN_IDLE);
    ds.setMaxIdle(MAX_IDLE);
  }

```
2. `application.properties`
```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/user026
spring.datasource.username=user026
spring.datasource.password=user026
```

To modify the Slack configuration, we can update `application.properties`:
```
slack.config.redirect_uri=https://0f6a-2601-646-202-27d0-8d2f-58a1-98c8-987g.ngrok.io/home
slack.config.client_id=2464212157.2674770528781
slack.config.client_secret=5897aac8047ec1610f7290ca16103b8e
```
We can set up the Slack redirect URI as a public URI using [ngrok](https://ngrok.com/download).


