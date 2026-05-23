# Spring Angular E-Commerce

Applicazione web enterprise sviluppata utilizzando Angular e Spring Boot.

Il progetto simula una piattaforma e-commerce con autenticazione JWT, gestione utenti, prodotti, carrello e ordini.

---

# Tecnologie Utilizzate

## Backend

* Java 21
* Spring Boot 3.3.10
* Spring Security
* JWT Authentication
* Spring Data JPA / Hibernate
* Maven
* PostgreSQL

## Frontend

* Angular 20
* TypeScript

---

# Funzionalità Principali

* Registrazione utenti
* Login autenticato tramite JWT
* Pannello admin gestione
* Gestione prodotti 
* Carrello
* Creazione ordini
* Gestione stato ordini
* Reset password tramite token

---

# Sicurezza

L'autenticazione è implementata tramite JWT (JSON Web Token).

Spring Security utilizza:

* SecurityFilterChain
* JwtAuthenticationFilter
* AuthenticationManager
* PasswordEncoder

L'applicazione utilizza un approccio stateless.

Il frontend Angular utilizza un HttpInterceptor per allegare automaticamente il Bearer Token alle richieste protette.

---

# Avvio del Progetto

## Prerequisiti

* Java 21+
* Node.js 18+
* PostgreSQL

---

# Backend

```bash
cd backend
mvn spring-boot:run
```

Backend disponibile su:

```text
http://localhost:9095
```

---

# Frontend

```bash
cd frontend
npm install
ng serve
```

Frontend disponibile su:

```text
http://localhost:4200
```

---

# Configurazione

Configurare il database PostgreSQL nel file:

```text
application.properties
```

Esempio:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/database
spring.datasource.username=postgres
spring.datasource.password=password
```

---

# Configurazione Backend

Prima di avviare il backend è necessario configurare il file:

```text
application-local.properties
```

Inserire le credenziali PostgreSQL, la secret key JWT e le credenziali email.

Esempio:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/database
spring.datasource.username=postgres
spring.datasource.password=password

jwt.secret=your_secret_key

spring.mail.username=email@gmail.com
spring.mail.password=password
```

