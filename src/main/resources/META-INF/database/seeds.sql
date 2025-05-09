-- MySQL sysdate(), Postgresql now()
-- MySQL date_sub(), Postgresql now() - interval 'X day'
-- MySQL date_add(), Postgresql now() + interval 'X day'
-- MySQL binary(16) (UNHEX(REPLACE('<UUID>', '-', ''))), Postgresql uuid ('<UUID>'::UUID)

INSERT INTO products (id, tenant, name, price, description, created_at, active, version) VALUES ('ab5666b6-3106-469b-9e34-2963b801466a'::UUID, 'shop_ecommerce', 'Kindle', 499.00, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.', '2024-12-01 12:00:00.000', 'YES', 0);
INSERT INTO products (id, tenant, name, price, description, created_at, active, version) VALUES ('77c31aa8-14f5-4df1-9a96-fa03d6882f4f'::UUID, 'shop_ecommerce', 'Camera GoPro Hero 7', 1400.00, 'Desempenho 2x melhor.', '2024-12-01 12:00:00.000', 'YES', 0);
INSERT INTO products (id, tenant, name, price, description, created_at, active, version) VALUES ('4e3a1229-050e-4049-9a85-54885aa5e875'::UUID, 'shop_ecommerce', 'Mochila Gamer', 200.00, 'Para notebooks de 15" e 17".', '2024-12-01 12:00:00.000', 'YES', 0);
INSERT INTO products (id, tenant, name, price, description, created_at, active, version) VALUES ('73beb2ec-5a28-43db-93a8-0cdd823fc2c6'::UUID, 'shop_ecommerce', 'Joystick', 250.00, 'PS5 Joystick', '2024-12-01 12:00:00.000', 'YES', 0);
INSERT INTO products (id, tenant, name, price, description, created_at, active, version) VALUES ('225ef043-f0a4-4c6d-b896-0ebf415dad93'::UUID, 'shop_ecommerce', 'PS5', 3550.00, 'PS5', '2024-12-01 12:00:00.000', 'YES', 0);
INSERT INTO products (id, tenant, name, price, description, created_at, active, version) VALUES ('849c840b-63fa-44b8-9883-47d9940adf8b'::UUID, 'shop_ecommerce', 'PS4', 1500.00, 'PS4', '2024-12-01 12:00:00.000', 'NO', 0);

-- INSERT INTO ecm_products (product_id, product_name, product_price, product_description, product_created_at) VALUES ('6cb5d716-f087-47a8-8edd-1d262a9da5f7'::UUID, 'PS3', 500.00, 'PS3', '2024-12-01 12:00:00.000');

INSERT INTO clients (id, tenant, name, cpf, version) VALUES ('737fac65-ec05-4173-a522-00833a22271b'::UUID, 'shop_ecommerce', 'João da Silva', '588.891.730-33', 0);
INSERT INTO clients (id, tenant, name, cpf, version) VALUES ('00492c10-234a-4388-9375-2da767ce0d6a'::UUID, 'shop_ecommerce', 'Manoel', '915.364.463-86', 0);

INSERT INTO clients_details (client_id, sex, birth_date) VALUES ('737fac65-ec05-4173-a522-00833a22271b'::UUID, 'MALE', '2000-01-01');

INSERT INTO orders (id, tenant, client_id, total, created_at, status, version) VALUES ('24be65bf-8e80-477c-81c5-277697b1bd37'::UUID, 'shop_ecommerce', '737fac65-ec05-4173-a522-00833a22271b'::UUID, '220.00', '2024-12-27 19:07:35.624173', 'WAITING', 0);
INSERT INTO orders (id, tenant, client_id, total, created_at, status, version) VALUES ('07e419cc-f461-42c6-8055-fca267c407ef'::UUID, 'shop_ecommerce', '737fac65-ec05-4173-a522-00833a22271b'::UUID, '20.00', '2024-12-27 02:36:35.624173', 'WAITING', 0);
INSERT INTO orders (id, tenant, client_id, total, created_at, status, version) VALUES ('47cbd3b5-aa9a-4644-95b3-0d9ca0551328'::UUID, 'shop_ecommerce', '737fac65-ec05-4173-a522-00833a22271b'::UUID, '10.00', NOW() - INTERVAL '3 DAY', 'WAITING', 0);
INSERT INTO orders (id, tenant, client_id, total, created_at, status, version) VALUES ('5d027af2-a718-46b2-a38d-0ba5daaf4860'::UUID, 'shop_ecommerce', '737fac65-ec05-4173-a522-00833a22271b'::UUID, '10.00', NOW() - INTERVAL '2 DAY', 'WAITING', 0);
INSERT INTO orders (id, tenant, client_id, total, created_at, status, version) VALUES ('9e1b1c20-6b20-4d96-a6d3-1ae0c0c7dd47'::UUID, 'shop_ecommerce', '737fac65-ec05-4173-a522-00833a22271b'::UUID, '10.00', NOW() - INTERVAL '1 DAY', 'WAITING', 0);
INSERT INTO orders (id, tenant, client_id, total, created_at, status, version) VALUES ('b5f64582-a54b-4c31-98cc-058f6ab36a76'::UUID, 'shop_ecommerce', '737fac65-ec05-4173-a522-00833a22271b'::UUID, '30.00', NOW(), 'PAID', 0);

INSERT INTO order_items (order_id, product_id, product_price, quantity, version) VALUES ('07e419cc-f461-42c6-8055-fca267c407ef'::UUID, 'ab5666b6-3106-469b-9e34-2963b801466a'::UUID, '10.00', 2, 0);
INSERT INTO order_items (order_id, product_id, product_price, quantity, version) VALUES ('24be65bf-8e80-477c-81c5-277697b1bd37'::UUID, 'ab5666b6-3106-469b-9e34-2963b801466a'::UUID, '10.00', 1, 0);
INSERT INTO order_items (order_id, product_id, product_price, quantity, version) VALUES ('24be65bf-8e80-477c-81c5-277697b1bd37'::UUID, '77c31aa8-14f5-4df1-9a96-fa03d6882f4f'::UUID, '10.00', 1, 0);
INSERT INTO order_items (order_id, product_id, product_price, quantity, version) VALUES ('24be65bf-8e80-477c-81c5-277697b1bd37'::UUID, '4e3a1229-050e-4049-9a85-54885aa5e875'::UUID, '200.00', 1, 0);
INSERT INTO order_items (order_id, product_id, product_price, quantity, version) VALUES ('9e1b1c20-6b20-4d96-a6d3-1ae0c0c7dd47'::UUID, 'ab5666b6-3106-469b-9e34-2963b801466a'::UUID, '10.00', 1, 0);
INSERT INTO order_items (order_id, product_id, product_price, quantity, version) VALUES ('b5f64582-a54b-4c31-98cc-058f6ab36a76'::UUID, 'ab5666b6-3106-469b-9e34-2963b801466a'::UUID, '10.00', 1, 0);
INSERT INTO order_items (order_id, product_id, product_price, quantity, version) VALUES ('b5f64582-a54b-4c31-98cc-058f6ab36a76'::UUID, '77c31aa8-14f5-4df1-9a96-fa03d6882f4f'::UUID, '20.00', 1, 0);
INSERT INTO order_items (order_id, product_id, product_price, quantity, version) VALUES ('47cbd3b5-aa9a-4644-95b3-0d9ca0551328'::UUID, 'ab5666b6-3106-469b-9e34-2963b801466a'::UUID, '10.00', 1, 0);
INSERT INTO order_items (order_id, product_id, product_price, quantity, version) VALUES ('5d027af2-a718-46b2-a38d-0ba5daaf4860'::UUID, 'ab5666b6-3106-469b-9e34-2963b801466a'::UUID, '10.00', 1, 0);

INSERT INTO categories (id, tenant, name, version) VALUES ('d9e5d6f8-6605-4dcd-a21a-3839407a0a1f'::UUID, 'shop_ecommerce', 'Electronics', 0);
INSERT INTO categories (id, tenant, name, version) VALUES ('65a38317-8d2b-43a9-ba84-0f6610bdc128'::UUID, 'shop_ecommerce', 'Videogames', 0);
INSERT INTO categories (id, tenant, name, version) VALUES ('26ea828c-45b6-44e7-9c89-e76732123052'::UUID, 'shop_ecommerce', 'Books', 0);
INSERT INTO categories (id, tenant, name, version) VALUES ('c76ab57b-092d-414b-87d3-eaee61eea23c'::UUID, 'shop_ecommerce', 'Sports', 0);
INSERT INTO categories (id, tenant, name, version) VALUES ('a7241e73-6969-4799-ae21-48c93fac25f4'::UUID, 'shop_ecommerce', 'E-Sports', 0);
INSERT INTO categories (id, tenant, name, version) VALUES ('87744af7-5198-41e7-9e33-59e15863964c'::UUID, 'shop_ecommerce', 'Soccer', 0);
INSERT INTO categories (id, tenant, name, version) VALUES ('77bcff79-5b4e-45e4-81e6-408850aa818e'::UUID, 'shop_ecommerce', 'Swimming', 0);
INSERT INTO categories (id, tenant, name, version) VALUES ('362df1eb-8707-4cc0-99b3-aea50c6747d7'::UUID, 'shop_ecommerce', 'Notebooks', 0);
INSERT INTO categories (id, tenant, name, version) VALUES ('9e0371a4-0c0c-4ee0-9e64-7497e72190af'::UUID, 'shop_ecommerce', 'Smartphones', 0);

-- INSERT INTO ecm_categories (category_id, category_name) VALUES ('8b7aeedd-da63-4785-88c0-a2f27614b29d'::UUID, 'Smartphones');

/* INHERITANCE WITH SINGLE_TABLE */
INSERT INTO payments (order_id, tenant, status, card_number, dtype, version) VALUES ('47cbd3b5-aa9a-4644-95b3-0d9ca0551328'::UUID, 'shop_ecommerce', 'PROCESSING', '1234 5678 9012 3456', 'CardPayment', 0);
INSERT INTO payments (order_id, tenant, status, card_number, dtype, version) VALUES ('24be65bf-8e80-477c-81c5-277697b1bd37'::UUID, 'shop_ecommerce', 'PROCESSING', '1234 5678 9012 3456', 'CardPayment', 0);
INSERT INTO payments (order_id, tenant, status, barcode, due_date, dtype, version) VALUES ('b5f64582-a54b-4c31-98cc-058f6ab36a76'::UUID, 'shop_ecommerce', 'PROCESSING', '12345.12345', NOW() + INTERVAL '2 DAY', 'BankSlipPayment', 0);

/* INHERITANCE WITH TABLE_PER_CLASS */
-- INSERT INTO card_payments (order_id, status, card_number) VALUES ('47cbd3b5-aa9a-4644-95b3-0d9ca0551328'::UUID, 'PROCESSING', '1234 5678 9012 3456');

/* INHERITANCE WITH JOINED */
-- INSERT INTO payments (order_id, status, dtype) VALUES ('47cbd3b5-aa9a-4644-95b3-0d9ca0551328'::UUID, 'PROCESSING', 'CardPayment');
-- INSERT INTO card_payments (order_id, card_number) VALUES ('47cbd3b5-aa9a-4644-95b3-0d9ca0551328'::UUID, '1234 5678 9012 3456');

INSERT INTO products_categories (product_id, category_id) VALUES ('225ef043-f0a4-4c6d-b896-0ebf415dad93'::UUID, '65a38317-8d2b-43a9-ba84-0f6610bdc128'::UUID);
INSERT INTO products_categories (product_id, category_id) VALUES ('73beb2ec-5a28-43db-93a8-0cdd823fc2c6'::UUID, '65a38317-8d2b-43a9-ba84-0f6610bdc128'::UUID);
INSERT INTO products_categories (product_id, category_id) VALUES ('ab5666b6-3106-469b-9e34-2963b801466a'::UUID, '26ea828c-45b6-44e7-9c89-e76732123052'::UUID);
INSERT INTO products_categories (product_id, category_id) VALUES ('77c31aa8-14f5-4df1-9a96-fa03d6882f4f'::UUID, 'd9e5d6f8-6605-4dcd-a21a-3839407a0a1f'::UUID);

INSERT INTO invoices (order_id, tenant, xml, emission_date, version) VALUES ('24be65bf-8e80-477c-81c5-277697b1bd37'::UUID, 'shop_ecommerce', '<xml />'::bytea, '2024-12-27 19:07:35.624173', 0);
