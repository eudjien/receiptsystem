drop table if exists _receipt__receipt_discount;
drop table if exists _receipt_item__receipt_item_discount;
drop table if exists event_emails;
drop table if exists emails;
drop table if exists constant_receipt_discounts;
drop table if exists percentage_receipt_discounts;
drop table if exists receipt_discounts;
drop table if exists constant_receipt_item_discounts;
drop table if exists percentage_receipt_item_discounts;
drop table if exists threshold_percentage_receipt_item_discounts;
drop table if exists receipt_item_discounts;
drop table if exists receipt_items;
drop table if exists receipts;
drop table if exists products;

create table emails
(
    id      bigint auto_increment
        primary key,
    address varchar(255) not null,
    constraint UK2qe2cynxce212q2up2q7i01se
        unique (address)
);

create table event_emails
(
    id         bigint auto_increment
        primary key,
    email_id   bigint       null,
    event_type varchar(255) not null,
    constraint UKn481au69osankoe6x0pi1ddfl
        unique (email_id, event_type),
    constraint FKe0l86d21bmav0lbxpls5jnrti
        foreign key (email_id) references emails (id)
);

create table products
(
    id    bigint auto_increment
        primary key,
    name  varchar(255)   not null,
    price decimal(19, 2) not null,
    constraint UKo61fmio5yukmmiqgnxf8pnavn
        unique (name)
);

create table receipt_discounts
(
    type                  varchar(124) not null,
    id                    bigint auto_increment
        primary key,
    dependent_discount_id bigint       null,
    description           varchar(255) not null,
    constraint FKpplo2q3jqfunvlr0nddje9cln
        foreign key (dependent_discount_id) references receipt_discounts (id)
);

create table constant_receipt_discounts
(
    constant decimal(19, 2) not null,
    id       bigint         not null
        primary key,
    constraint FK3c72u0nnivd967ycht17dl0ia
        foreign key (id) references receipt_discounts (id)
);

create table percentage_receipt_discounts
(
    percent double not null,
    id      bigint not null
        primary key,
    constraint FKndrpvsj0qak44cbn7mw11t49x
        foreign key (id) references receipt_discounts (id)
);

create table receipt_item_discounts
(
    type                  varchar(86)  not null,
    id                    bigint auto_increment
        primary key,
    dependent_discount_id bigint       null,
    description           varchar(255) not null,
    constraint FKgjn0brxm8mpgw8q04snabhx3v
        foreign key (dependent_discount_id) references receipt_item_discounts (id)
);

create table constant_receipt_item_discounts
(
    constant decimal(19, 2) not null,
    id       bigint         not null
        primary key,
    constraint FKtpcpgexfwy2hq30ys68kxu0uf
        foreign key (id) references receipt_item_discounts (id)
);

create table percentage_receipt_item_discounts
(
    percent double not null,
    id      bigint not null
        primary key,
    constraint FKjb1wd5g6o3h3snan97hop162k
        foreign key (id) references receipt_item_discounts (id)
);

create table receipts
(
    id           bigint auto_increment
        primary key,
    address      varchar(255) not null,
    cashier      varchar(255) not null,
    date         datetime(6)  not null,
    description  varchar(255) not null,
    name         varchar(255) not null,
    phone_number varchar(255) null,
    constraint UKe6jbf81md2j9a1t1ombo151io
        unique (name)
);

create table _receipt__receipt_discount
(
    receipt_id          bigint not null,
    receipt_discount_id bigint not null,
    primary key (receipt_id, receipt_discount_id),
    constraint FK2tun05jbp5b9h1qtyinjc2jfs
        foreign key (receipt_discount_id) references receipt_discounts (id),
    constraint FKfnt0fyjikxwsa3lidu41k412b
        foreign key (receipt_id) references receipts (id)
);

create table receipt_items
(
    id         bigint auto_increment
        primary key,
    product_id bigint not null,
    quantity   bigint not null,
    receipt_id bigint not null,
    constraint UK99g19542vt8s3r5eps10rusxx
        unique (product_id, receipt_id),
    constraint FKmyfhuc1y0sjqe2geey8jx9nc2
        foreign key (receipt_id) references receipts (id),
    constraint FKoflo2om70ovsg65ot9uqsk221
        foreign key (product_id) references products (id)
);

create table _receipt_item__receipt_item_discount
(
    receipt_item_id          bigint not null,
    receipt_item_discount_id bigint not null,
    primary key (receipt_item_id, receipt_item_discount_id),
    constraint FKenfarm6ytdx191v67pn6b5b1m
        foreign key (receipt_item_id) references receipt_items (id),
    constraint FKrkri3fdilypnd5tsqpt9ljwce
        foreign key (receipt_item_discount_id) references receipt_item_discounts (id)
);

create table threshold_percentage_receipt_item_discounts
(
    percent   double not null,
    threshold bigint not null,
    id        bigint not null
        primary key,
    constraint FKarxmw5has101r0k4qx56a80av
        foreign key (id) references receipt_item_discounts (id)
);
