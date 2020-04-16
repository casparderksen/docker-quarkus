Base58 UUID

https://developers.redhat.com/blog/2019/04/12/migrating-java-applications-to-quarkus-lessons-learned/

Plant UML documentation
Plant UML architecture test

Custom JRE
Java9 modular application

Mem-options: use all Docker mem
-XX:MaxRAMFraction=1

JWKS (Json Web Key Set) endpoint voor AuthService
https://tools.ietf.org/html/rfc6749#section-4.2 : implicit flow -> use auth server
https://tools.ietf.org/html/rfc6749#section-4.3 : password credentials grant -> migrate from basic/form
https://tools.ietf.org/html/rfc6749#section-4.4 : client credentials grant -> SAML bridge/Spnego Jaspic Kerberos


   Since this access token request utilizes the resource owner's
   password, the authorization server MUST protect the endpoint against
   brute force attacks (e.g., using rate-limitation or generating
   alerts).
   => Metric+alert for failed logins
   => Rate limiting? Reactive?
   
https://docs.spring.io/spring-security-kerberos/docs/1.0.1.RELEASE/reference/htmlsingle/#springsecuritykerberos
Appendix B: crash course to Kerberos
https://oauth.net/code/java/

Cucumber

Minio: https://access.redhat.com/containers/?tab=overview#/registry.connect.redhat.com/opsmx/ubi8-oes-minio

Move util to separate module

Code coverage, exclude lombok/mapstruct

Hibernate debug logging of values

Immutable domain objects, versions, event sourcing

External config service, URL as config source

Check: https://github.com/phillip-kruger/microprofile-demo

Monitoring with Prometheus stack

Security. authorize endpoint, MP-JWT, JWTenizer for integration. Adam Bien statustest

Logging with FLK stack (fluentd fraction)

OpenTracing and Jaeger
https://docs.thorntail.io/2.2.1.Final/#distributed-tracing_thorntail

JWT
  * see https://docs.payara.fish/documentation/microprofile/jwt.html
  * see https://github.com/javaee-samples/microprofile1.2-samples/tree/master/jwt-auth

Mockito

Cloud events

MP reactive messaging, reactive streams

EventStore with Minio for payloads

Kafka

K8S / Istio

Akka -> distributed counter? with persistence? / Runtime config

@password/@secret -> MP extension / JEE8/Elytron credential store
TLS password from credential store

To run startup code:
https://github.com/phillip-kruger/microprofile-demo/blob/master/profiling/src/main/java/com/github/phillipkruger/profiling/startup/BootstrapService.java

Prometheus stack:
https://github.com/vegasbrianc/prometheus
https://finestructure.co/blog/2016/5/16/monitoring-with-prometheus-grafana-docker-part-1
https://developers.redhat.com/blog/2017/10/25/monitor-eclipse-microprofile-1-2-server-prometheus/



  elytron:
    credential-stores:
      MyCS:
        type: KeyStoreCredentialStore
        location: security/credential-store.jceks
        credential-reference: h2-password
        password: changeit


---

Check: diagramming tool
https://github.com/eclipse/microprofile-reactive-streams-operators header Diagrams

---

Self-signed cert:


# Generate self signed TLS certificate
RUN mkdir /opt/security \
    && keytool -genkeypair \
           -keyalg RSA \
           -keysize 1024 \
           -validity 365 \
           -alias localhost \
           -dname "CN=localhost" \
           -storetype pkcs12 \
           -keystore /opt/security/keystore.jks \
           -keypass changeit \
           -storepass changeit

## HTTPS

Enable https by specifying the `https` profile:

    $ java -jar target/myapp-thorntail.jar -Sh2 -Shttps

See [project-https.yml](myapp/src/main/resources/project-https.yml) for an example https configuration
(adapt to your needs). Https is not configured by default, because storing passwords and certificates
in archives/containers is insecure and not portable across environments. Furthermore, https could be
offloaded by Nginx, or Istio when deploying to Kubernetes.

To generate a self-signed certificate, run `gen_keystore.sh` in [myapp/security](myapp/security).

To run the Docker container with https enabled, mount a host volume containing `keystore.jsk` at
 `/opt/security` and specify `-Shttps` as command-line argument. The `mvn docker:run -Pdocker`
target is configured for running with https enabled.


--
Document JEE8 security
- https://www.baeldung.com/java-ee-8-security
- https://developer.ibm.com/tutorials/j-javaee8-security-api-3/
- https://dzone.com/articles/an-overview-of-java-ee-8s-security-api