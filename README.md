BarkHour Ticket Purchase Web Application
===========================================

<img width="1359" alt="barkoursc" src="https://user-images.githubusercontent.com/60201466/170530690-ee45683d-9118-4af5-b803-962f2dbc3a57.png">

Looking for dog-friendly events and things to do with your dog? BarkHour is an EventBrite web application for dog people. This project implements a two-tier ticket purchase web application with a Java (Jetty/Servlets), front end and an SQL backend. BarkHour uses [Thymeleaf](https://www.thymeleaf.org/) to generate HTML. It's also supported with Javascript, CSS and Bootstrap templates.

### Features

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
