# Vault Transform Secrets Engine with YugabyteDB Demo

This demo application demonstrates data protection capabilities of vault with YugabyteDB. Application shows multiple capabilities available for masking, encrypting and tokenizing data.

In this demo, YugabyteDB and Vault together solve 2 key issues:
1. Protect against credential exposure by using short lived, dynamic credentials.
2. Protect again data exposur in case of exfiltration

## Demo Architecture

![Demo Architecture](assets/vault_transform_demo_yb.svg)

- demo-ui is a simple single page application built with Spring Boot
- demo-api is a Spring Boot application that exposes a REST API for the demo-ui.
  - It calls the Vault agent to transform sensitive data and store the transformed data in MySQL
  - It uses the Spring Cloud Vault library to integrate with the Vault API, i.e. to retrieve dynamic database credentials.
- Vault agent act as a sidecar proxy here to retrieve secrets from Vault.
  - Auto-auth is used to handle the login to Vault and the renewal of the Vault auth token. Using auto-auth removes the responsibility of managing the auth token from the client application, making it easier for application to consume Vault’s API.
  - Vault agent is optional in this case since Spring Cloud Vault library has the ability to authenticate to Vault.
- On the Vault server side 3 secrets engines are enabled: database, transform, and transit
  - The database secrets engine integrates with MySQL to generate dynamic DB credentials for the demo-app
  - The transform secrets engine is configured to use MySQL as an external token storage for tokenization
- YugabyteDB is setup with YSQL. A `demo` database is created on it to store tokenized data. You can use any postgres compatible tool and connect on port `5433` to access SQL interface.

# Prerequisites
- Vault Enterprise License - Contact Hashicorp for a trial license
- Local demo
  - [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- Cloud based demo
  - [Gitpod account](https://gitpod.io) - easy signup with github account

## Gitpod based demo

Setup license variable (one-time)

1. Open  [Gitpod / User Settings / Variables](https://gitpod.io/user/variables)
1. Click on *New Variable* button on top right
1. Fill up following on the *New Varible* window and click *Add Variable*
    1. Name: *VAULT_LICENSE*
    1. Value: *<Vaul license text>*
    1. Scope: *yogendra/vault-transform-demo* (or your own forked repo name)
1. Now you can launch gitpod workspace by clickin buton below

[![Open in Gitpod][logo-gitpod]][gp-launch]

## Local demo

```bash
export VAULT_LICENSE="....."
git clone https://github.com/yogendra/vault-transform-demo.git
cd vault-transform-demo
bin/demo prepare
bin/demo start
```

`bin/demo prepare` is optional. But if you encounter error in the `demo start` due to error in
connecting to alpine repo urls, you can use `prepare` to pull pre-built image.


Access Consoles:
- [Demo UI](http://localhost:8080)
- [Vault](http://localhost:8200)
  - Username: `admin`
  - Password: `passw0rd`
- [YugabyteDB UI](http://localhost:15433)
  - Database: `demo`
  - Username: `demo`
  - Password: `passw0rd`
  - Super User: `yugabyte`
  - Super User Password: `yugabyte`
- [SQL Console](http://localhost:3000)
  - Username: `admin`
  - Password: `passw0rd`

![Demo UI](assets/vault_transform_demo_ui.png)

# Useful commands
```shell
Useful Commands
===============
bin/demo apps-start        - start infra containers
bin/demo add-sample-data   - add sample data
bin/demo build-containers  - build containers
bin/demo help              - this screen
bin/demo dc                - run docker-compose commands
bin/demo infra-start       - start infra containers
bin/demo prepare           - prepare for demoe. pull images for running demo
bin/demo sample-data-clear - clear sample data
bin/demo sample-data-show  - show sample data
bin/demo sample-data-add   - add sample data
bin/demo start             - start infra and apps
bin/demo stop              - stop demo containers
bin/demo vault-shell       - vault shell
bin/demo watch             - look are container logs
bin/demo ysqlsh            - start the YSQLSH shell
bin/demo yugabtyedb-shell  - yugabytedb shell
```

# Reference
- [YugabyteDB Documentation](https://docs.yugabyte.com)
- [Vault Transform Demo](https://github.com/tkaburagi/vault-transformation-demo/tree/master)
- [Vault Transform Tutorial](https://developer.hashicorp.com/vault/tutorials/adp/transform)
- [Vault Tokenization Tutorial](https://developer.hashicorp.com/vault/tutorials/adp/tokenization)
- [Vault Transform Secrets Engine API](https://developer.hashicorp.com/vault/api-docs/secret/transform#transform-secrets-engine-api)
- [Vault Demo by Nicholas Jackson](https://github.com/nicholasjackson/demo-vault)
- [Spring Cloud Vault](https://cloud.spring.io/spring-cloud-vault/reference/html/#_quick_start)




[logo-gitpod]: https://gitpod.io/button/open-in-gitpod.svg
[gp-launch]: https://gitpod.io/#https://github.com/yogendra/vault-transform-demo

