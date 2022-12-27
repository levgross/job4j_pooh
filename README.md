## Pooh JMS Project

### Description:

- This project is analogous to the asynchronous RabbitMQ queue.

- The application starts Socket and waits for clients.

- There are two types of clients: senders (publisher) and recipients (subscriber).

- HTTP is used as the protocol. Messages in JSON format.

- There are two modes: queue, topic.

- There is no synchronization in the code. Everything is done on Executors and concurrent collections.

Examples below are for command line.

#### Queue

POST-query adds element to the queue named "weather".

"queue" is the name of the mode.
```
curl -X POST -d "temperature=18" http://localhost:9000/queue/weather
```

GET-query returns element from the queue named "weather".
```
curl -X GET http://localhost:9000/queue/weather
```

#### Topic

POST-query adds element to the every personal subscriber`s topic named "weather".

"topic" is the name of the mode.
```
curl -X POST http://localhost:9000/topic/weather -d "temperature=18"
```

GET-query returns element from the personal topic named "weather" of the subscriber with the given id = 1.

```
curl -X GET http://localhost:9000/topic/weather/1
```

### Technologies:

- Java Concurrency (classes from the java.util.concurrent package)
- Sockets
- Java IO
- JUnit 5.4.0
- AssertJ 3.23.1

### Environment:
Install:
- JDK 17.0.1
- Maven 4.0.0
- cURL 7.82.0

### Contacts
Feel free to write me if you have some notes.

[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/levgross)&nbsp;&nbsp;
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:levgross@gmail.com)&nbsp;&nbsp;