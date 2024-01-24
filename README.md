add application.yml under src/main/resources
<code>
git:
  personal-token: 
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/compare_karo
  datasource:
    driver-class-name: org.postgresql.Driver
    password: <db-password>
    url: jdbc:postgresql://localhost:5432/m0a00pf?currentSchema=compare_karo
    username: <db-username>

cassandra:
  secure-connect-file: /Users/m0a00pf/Downloads/secure-connect-suggest-cars.zip
  client-id: <cass-client-id>
  client-secret: <cass-client-secret>
  token: <cass-token.
<code>
