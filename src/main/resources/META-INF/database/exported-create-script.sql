
    create table categories (
        id binary(16) not null,
        parent_id binary(16),
        name varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;

    create table clients (
        cpf varchar(14) not null,
        id binary(16) not null,
        name varchar(100) not null,
        primary key (id)
    ) engine=InnoDB;

    create table clients_contacts (
        client_id binary(16) not null,
        description varchar(255),
        type varchar(255) not null,
        primary key (client_id, type)
    ) engine=InnoDB;

    create table clients_details (
        birth_date date,
        client_id binary(16) not null,
        sex enum ('FEMALE','MALE') not null,
        primary key (client_id)
    ) engine=InnoDB;

    create table invoices (
        emission_date datetime(6) not null,
        order_id binary(16) not null,
        xml LONGBLOB NOT NULL,
        primary key (order_id)
    ) engine=InnoDB;

    create table order_items (
        product_price decimal(19,2) not null,
        quantity integer not null,
        order_id binary(16) not null,
        product_id binary(16) not null,
        primary key (order_id, product_id)
    ) engine=InnoDB;

    create table orders (
        state varchar(2),
        total decimal(19,2) not null,
        created_at datetime(6) not null,
        finished_at datetime(6),
        updated_at datetime(6),
        zip_code varchar(9),
        number varchar(10),
        client_id binary(16) not null,
        id binary(16) not null,
        city varchar(50),
        complement varchar(50),
        neighborhood varchar(50),
        address varchar(100),
        status enum ('CANCELLED','PAID','WAITING') not null,
        primary key (id)
    ) engine=InnoDB;

    create table payments (
        order_id binary(16) not null,
        dtype varchar(31) not null,
        card_number varchar(50),
        barcode varchar(100),
        status enum ('CANCELLED','PROCESSING','RECEIVED') not null,
        primary key (order_id)
    ) engine=InnoDB;

    create table primary_key_strategies (
        id binary(16) not null,
        primary key (id)
    ) engine=InnoDB;

    create table products (
        price decimal(10,2),
        created_at datetime(6) not null,
        updated_at datetime(6),
        id binary(16) not null,
        name varchar(100) not null,
        description longtext,
        photo longblob,
        primary key (id)
    ) engine=InnoDB;

    create table products_attributes (
        product_id binary(16) not null,
        name varchar(100) not null,
        value varchar(255)
    ) engine=InnoDB;

    create table products_categories (
        category_id binary(16) not null,
        product_id binary(16) not null
    ) engine=InnoDB;

    create table products_tags (
        product_id binary(16) not null,
        tag varchar(50) not null
    ) engine=InnoDB;

    create table stocks (
        quantity integer,
        id binary(16) not null,
        product_id binary(16) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table categories 
       add constraint idx_name unique (name);

    create index idx_name 
       on clients (name);

    alter table clients 
       add constraint unq_cpf unique (cpf);

    create index idx_name 
       on products (name);

    alter table products 
       add constraint unq_name unique (name);

    alter table stocks 
       add constraint unq_product unique (product_id);

    alter table categories 
       add constraint fk_category_category 
       foreign key (parent_id) 
       references categories (id);

    alter table clients_contacts 
       add constraint fk_client_contact 
       foreign key (client_id) 
       references clients (id);

    alter table clients_details 
       add constraint fk_client_detail_client 
       foreign key (client_id) 
       references clients (id);

    alter table invoices 
       add constraint fk_invoice_order 
       foreign key (order_id) 
       references orders (id);

    alter table order_items 
       add constraint fk_order_item_order 
       foreign key (order_id) 
       references orders (id);

    alter table order_items 
       add constraint fk_order_item_product 
       foreign key (product_id) 
       references products (id);

    alter table orders 
       add constraint fk_order_client 
       foreign key (client_id) 
       references clients (id);

    alter table payments 
       add constraint fk_payment_order 
       foreign key (order_id) 
       references orders (id);

    alter table products_attributes 
       add constraint fk_product_attribute 
       foreign key (product_id) 
       references products (id);

    alter table products_categories 
       add constraint fk_product_category 
       foreign key (category_id) 
       references categories (id);

    alter table products_categories 
       add constraint fk_category_product 
       foreign key (product_id) 
       references products (id);

    alter table products_tags 
       add constraint fk_product_tag 
       foreign key (product_id) 
       references products (id);

    alter table stocks 
       add constraint fk_stock_product 
       foreign key (product_id) 
       references products (id);
INSERT INTO products (id, name, price, description, created_at) VALUES (UNHEX(REPLACE('ab5666b6-3106-469b-9e34-2963b801466a', '-', '')), 'Kindle', 499.00, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.', '2024-12-01 12:00:00.000');
INSERT INTO products (id, name, price, description, created_at) VALUES (UNHEX(REPLACE('77c31aa8-14f5-4df1-9a96-fa03d6882f4f', '-', '')), 'Camera GoPro Hero 7', 1400.00, 'Desempenho 2x melhor.', '2024-12-01 12:00:00.000');
INSERT INTO products (id, name, price, description, created_at) VALUES (UNHEX(REPLACE('4e3a1229-050e-4049-9a85-54885aa5e875', '-', '')), 'Mochila Gamer', 200.00, 'Para notebooks de 15" e 17".', '2024-12-01 12:00:00.000');
INSERT INTO products (id, name, price, description, created_at) VALUES (UNHEX(REPLACE('73beb2ec-5a28-43db-93a8-0cdd823fc2c6', '-', '')), 'Joystick', 250.00, 'PS5 Joystick', '2024-12-01 12:00:00.000');
INSERT INTO clients (id, name, cpf) VALUES (UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), 'João da Silva', '588.891.730-33');
INSERT INTO clients (id, name, cpf) VALUES (UNHEX(REPLACE('00492c10-234a-4388-9375-2da767ce0d6a', '-', '')), 'Manoel', '915.364.463-86');
INSERT INTO orders (id, client_id, total, created_at, status) VALUES (UNHEX(REPLACE('24be65bf-8e80-477c-81c5-277697b1bd37', '-', '')), UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), '10.00', '2024-12-27 19:07:35.624173', 'WAITING');
INSERT INTO orders (id, client_id, total, created_at, status) VALUES (UNHEX(REPLACE('07e419cc-f461-42c6-8055-fca267c407ef', '-', '')), UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), '10.00', '2024-12-27 02:36:35.624173', 'WAITING');
INSERT INTO orders (id, client_id, total, created_at, status) VALUES (UNHEX(REPLACE('47cbd3b5-aa9a-4644-95b3-0d9ca0551328', '-', '')), UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), '10.00', '2024-12-31 21:37:58.624173', 'WAITING');
INSERT INTO order_items (order_id, product_id, product_price, quantity) VALUES (UNHEX(REPLACE('07e419cc-f461-42c6-8055-fca267c407ef', '-', '')), UNHEX(REPLACE('ab5666b6-3106-469b-9e34-2963b801466a', '-', '')), '10.00', 2);
INSERT INTO categories (id, name) VALUES (UNHEX(REPLACE('d9e5d6f8-6605-4dcd-a21a-3839407a0a1f', '-', '')), 'Eletronicos');
INSERT INTO payments (order_id, status, card_number, dtype) VALUES (UNHEX(REPLACE('47cbd3b5-aa9a-4644-95b3-0d9ca0551328', '-', '')), 'PROCESSING', '1234 5678 9012 3456', 'CardPayment');
