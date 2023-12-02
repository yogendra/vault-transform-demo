# Demo - YugabtyeDB and Vault Integration

## Create Demo terminals

> ℹ️ Done automatically on gitpod



After starting demo, open 2 seprate consoles. One will be used for yugabytedb commands and other will be used for vault commands. Note, that in gitpod environment, this is already created for you.


1. (New Terminal) Launch YugabyteDB consonle

    ```bash
    bin/demo yugabytedb-shell
    ```

    Test this by running ysqlsh command

    ```bash
    ysqlsh -c 'select version()'
    ```

    Sample output
    
    ```
                                                                                            version                                                                          
                
    --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    ---------------
    PostgreSQL 11.2-YB-2.18.4.0-b0 on x86_64-pc-linux-gnu, compiled by clang version 15.0.3 (https://github.com/yugabyte/llvm-project.git 0b8d1183745fd3998d8beffeec8cbe99c1b
    20529), 64-bit
    (1 row)

    ```
1. (New Terminal) Launch Vault console

    ```bash
    bin/demo vault-shell
    ```

    Test this by running vault status command

    ```bash
    vault token lookup
    ```

    Sample output 

    ```
    Key                 Value
    ---                 -----
    accessor            cSWxJdRtMi852gl10EcLE41p
    creation_time       1701490622
    creation_ttl        0s
    display_name        root
    entity_id           n/a
    expire_time         <nil>
    explicit_max_ttl    0s
    id                  hvs.wB2wPEfboMzhWvEeCxPIxakt
    meta                <nil>
    num_uses            0
    orphan              true
    path                auth/token/root
    policies            [root]
    ttl                 0s
    type                service
    ```


## Demos

### Dynamic Credential Management

1. List existing users in the database

    ```bash
    demo ysqlsh  -c '\du'
    ```

    Sample Output

    ```
                                                        List of roles
                         Role name                     |                         Attributes                         | Member of 
    ---------------------------------------------------+------------------------------------------------------------+-----------
     demo                                              | Superuser                                                  | {}
     postgres                                          | Superuser, Create role, Create DB, Replication, Bypass RLS | {}
     v-approle-demoapp-MofCjauBa7PYUeofJYRq-1701490659 | Password valid until 2023-12-02 12:17:44+00                | {}
     yb_db_admin                                       | No inheritance, Cannot login                               | {}
     yb_extension                                      | Cannot login                                               | {}
     yb_fdw                                            | Cannot login                                               | {}
     yugabyte                                          | Superuser, Create role, Create DB, Replication, Bypass RLS | {}
    ```

    You can see an existing dynamic use `v-approle-demoapp-*`. This is created by the our application on the fly. 

1. Get credentials for the `v-approle-demoapp-*` user to try

    ```bash
    ```
    
    Sample Output
    
    ```
    ```
    
    </details>

1. Let us create a new DB user via vault. We will use the root identity to create this user, so the user name will come out a bit different.

    ```bash
    demo vault read database/creds/demoapp    
    ```
    
    Sample Output

    ```
    Key                Value
    ---                -----
    lease_id           database/creds/demoapp/vhM3MTOlYL7ND6VXUiFDwZDq
    lease_duration     8h
    lease_renewable    true
    password           VPdCbH92Twdd-EcnSSoh
    username           v-root-demoapp-65NAzXDOpVrubJX3TU70-1701491916    
    ```
    </summary>

1. Lets use this new user to connect to database



1. List users again from the demo database 

    ```bash
    demo ysqlsh  -c '\du'
    ```
    
    Sample Output    
    
    ```
    
                                                           List of roles
                         Role name                     |                         Attributes                         | Member of 
    ---------------------------------------------------+------------------------------------------------------------+-----------
     demo                                              | Superuser                                                  | {}
     postgres                                          | Superuser, Create role, Create DB, Replication, Bypass RLS | {}
     v-approle-demoapp-MofCjauBa7PYUeofJYRq-1701490659 | Password valid until 2023-12-02 12:17:44+00                | {}
     v-root-demoapp-65NAzXDOpVrubJX3TU70-1701491916    | Password valid until 2023-12-02 12:38:41+00                | {}
     yb_db_admin                                       | No inheritance, Cannot login                               | {}
     yb_extension                                      | Cannot login                                               | {}
     yb_fdw                                            | Cannot login                                               | {}
     yugabyte                                          | Superuser, Create role, Create DB, Replication, Bypass RLS | {}
    ```

    </details>


### Data Masking

### Vault PKI for YugabyteDB Encryption

TBD