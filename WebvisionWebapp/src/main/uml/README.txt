All other uml diagrams don't have any connections. The reason for this is because alot of things are done through Spring
framework and so although there are dependency injection for the repos and services, there was not explicit injection
of a class since Spring handles this with @Autowire as well as handles identifying services with @Service