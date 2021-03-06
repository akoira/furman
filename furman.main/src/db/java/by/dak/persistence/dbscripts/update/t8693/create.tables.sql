create table COMMON_DATA (ID bigint generated by default as identity,
DISCRIMINATOR VARCHAR(255) not null,
CTYPE VARCHAR(255) not null,
SERVICE VARCHAR(255) not null,
NAME  VARCHAR(255) not null,
AMOUNT DOUBLE not null,
PRICE DOUBLE not null,
DIALER_PRICE DOUBLE not null,
LAST BOOLEAN not null,
ORDER_ID BIGINT not null,
constraint PK_COMMON_DATA primary key (ID) ,
);
