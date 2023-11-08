create table
    users_tokenization (
        id varchar(50) primary key,
        username varchar(50),
        password varchar(200),
        email varchar(200),
        creditcard varchar(200),
        flag varchar(30)
    ) SPLIT INTO 1 TABLETS;

create index idx_user_tokenization on users_tokenization(username) ;

create index idx_user_email on users_tokenization(email) ;
