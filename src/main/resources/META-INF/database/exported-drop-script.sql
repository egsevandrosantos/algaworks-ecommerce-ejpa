
    alter table categories 
       drop 
       foreign key fk_category_category;

    alter table clients_contacts 
       drop 
       foreign key fk_client_contact;

    alter table clients_details 
       drop 
       foreign key fk_client_detail_client;

    alter table invoices 
       drop 
       foreign key fk_invoice_order;

    alter table order_items 
       drop 
       foreign key fk_order_item_order;

    alter table order_items 
       drop 
       foreign key fk_order_item_product;

    alter table orders 
       drop 
       foreign key fk_order_client;

    alter table payments 
       drop 
       foreign key fk_payment_order;

    alter table products_attributes 
       drop 
       foreign key fk_product_attribute;

    alter table products_categories 
       drop 
       foreign key fk_product_category;

    alter table products_categories 
       drop 
       foreign key fk_category_product;

    alter table products_tags 
       drop 
       foreign key fk_product_tag;

    alter table stocks 
       drop 
       foreign key fk_stock_product;

    drop table if exists categories;

    drop table if exists clients;

    drop table if exists clients_contacts;

    drop table if exists clients_details;

    drop table if exists invoices;

    drop table if exists order_items;

    drop table if exists orders;

    drop table if exists payments;

    drop table if exists primary_key_strategies;

    drop table if exists products;

    drop table if exists products_attributes;

    drop table if exists products_categories;

    drop table if exists products_tags;

    drop table if exists stocks;
