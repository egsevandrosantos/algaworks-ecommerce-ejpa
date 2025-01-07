INSERT INTO products (id, name, price, description, created_at) VALUES (UNHEX(REPLACE('ab5666b6-3106-469b-9e34-2963b801466a', '-', '')), 'Kindle', 499.00, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.', '2024-12-01 12:00:00.000');
INSERT INTO products (id, name, price, description, created_at) VALUES (UNHEX(REPLACE('77c31aa8-14f5-4df1-9a96-fa03d6882f4f', '-', '')), 'Camera GoPro Hero 7', 1400.00, 'Desempenho 2x melhor.', '2024-12-01 12:00:00.000');
INSERT INTO products (id, name, price, description, created_at) VALUES (UNHEX(REPLACE('4e3a1229-050e-4049-9a85-54885aa5e875', '-', '')), 'Mochila Gamer', 200.00, 'Para notebooks de 15" e 17".', '2024-12-01 12:00:00.000');
INSERT INTO products (id, name, price, description, created_at) VALUES (UNHEX(REPLACE('73beb2ec-5a28-43db-93a8-0cdd823fc2c6', '-', '')), 'Joystick', 250.00, 'PS5 Joystick', '2024-12-01 12:00:00.000');
INSERT INTO products (id, name, price, description, created_at) VALUES (UNHEX(REPLACE('225ef043-f0a4-4c6d-b896-0ebf415dad93', '-', '')), 'PS5', 3550.00, 'PS5', '2024-12-01 12:00:00.000');

INSERT INTO clients (id, name, cpf) VALUES (UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), 'João da Silva', '588.891.730-33');
INSERT INTO clients (id, name, cpf) VALUES (UNHEX(REPLACE('00492c10-234a-4388-9375-2da767ce0d6a', '-', '')), 'Manoel', '915.364.463-86');

INSERT INTO orders (id, client_id, total, created_at, status) VALUES (UNHEX(REPLACE('24be65bf-8e80-477c-81c5-277697b1bd37', '-', '')), UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), '20.00', '2024-12-27 19:07:35.624173', 'WAITING');
INSERT INTO orders (id, client_id, total, created_at, status) VALUES (UNHEX(REPLACE('07e419cc-f461-42c6-8055-fca267c407ef', '-', '')), UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), '20.00', '2024-12-27 02:36:35.624173', 'WAITING');
INSERT INTO orders (id, client_id, total, created_at, status) VALUES (UNHEX(REPLACE('47cbd3b5-aa9a-4644-95b3-0d9ca0551328', '-', '')), UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), '10.00', DATE_SUB(SYSDATE(), INTERVAL 3 DAY), 'WAITING');
INSERT INTO orders (id, client_id, total, created_at, status) VALUES (UNHEX(REPLACE('5d027af2-a718-46b2-a38d-0ba5daaf4860', '-', '')), UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), '10.00', DATE_SUB(SYSDATE(), INTERVAL 2 DAY), 'WAITING');
INSERT INTO orders (id, client_id, total, created_at, status) VALUES (UNHEX(REPLACE('9e1b1c20-6b20-4d96-a6d3-1ae0c0c7dd47', '-', '')), UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), '10.00', DATE_SUB(SYSDATE(), INTERVAL 1 DAY), 'WAITING');
INSERT INTO orders (id, client_id, total, created_at, status) VALUES (UNHEX(REPLACE('b5f64582-a54b-4c31-98cc-058f6ab36a76', '-', '')), UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), '10.00', SYSDATE(), 'WAITING');

INSERT INTO order_items (order_id, product_id, product_price, quantity) VALUES (UNHEX(REPLACE('07e419cc-f461-42c6-8055-fca267c407ef', '-', '')), UNHEX(REPLACE('ab5666b6-3106-469b-9e34-2963b801466a', '-', '')), '10.00', 2);
INSERT INTO order_items (order_id, product_id, product_price, quantity) VALUES (UNHEX(REPLACE('24be65bf-8e80-477c-81c5-277697b1bd37', '-', '')), UNHEX(REPLACE('ab5666b6-3106-469b-9e34-2963b801466a', '-', '')), '10.00', 1);
INSERT INTO order_items (order_id, product_id, product_price, quantity) VALUES (UNHEX(REPLACE('24be65bf-8e80-477c-81c5-277697b1bd37', '-', '')), UNHEX(REPLACE('77c31aa8-14f5-4df1-9a96-fa03d6882f4f', '-', '')), '10.00', 1);
INSERT INTO order_items (order_id, product_id, product_price, quantity) VALUES (UNHEX(REPLACE('9e1b1c20-6b20-4d96-a6d3-1ae0c0c7dd47', '-', '')), UNHEX(REPLACE('ab5666b6-3106-469b-9e34-2963b801466a', '-', '')), '10.00', 1);
INSERT INTO order_items (order_id, product_id, product_price, quantity) VALUES (UNHEX(REPLACE('b5f64582-a54b-4c31-98cc-058f6ab36a76', '-', '')), UNHEX(REPLACE('ab5666b6-3106-469b-9e34-2963b801466a', '-', '')), '10.00', 1);
INSERT INTO order_items (order_id, product_id, product_price, quantity) VALUES (UNHEX(REPLACE('47cbd3b5-aa9a-4644-95b3-0d9ca0551328', '-', '')), UNHEX(REPLACE('ab5666b6-3106-469b-9e34-2963b801466a', '-', '')), '10.00', 1);
INSERT INTO order_items (order_id, product_id, product_price, quantity) VALUES (UNHEX(REPLACE('5d027af2-a718-46b2-a38d-0ba5daaf4860', '-', '')), UNHEX(REPLACE('ab5666b6-3106-469b-9e34-2963b801466a', '-', '')), '10.00', 1);

INSERT INTO categories (id, name) VALUES (UNHEX(REPLACE('d9e5d6f8-6605-4dcd-a21a-3839407a0a1f', '-', '')), 'Electronics');
INSERT INTO categories (id, name) VALUES (UNHEX(REPLACE('65a38317-8d2b-43a9-ba84-0f6610bdc128', '-', '')), 'Videogames');
INSERT INTO categories (id, name) VALUES (UNHEX(REPLACE('26ea828c-45b6-44e7-9c89-e76732123052', '-', '')), 'Books');
INSERT INTO categories (id, name) VALUES (UNHEX(REPLACE('c76ab57b-092d-414b-87d3-eaee61eea23c', '-', '')), 'Sports');
INSERT INTO categories (id, name) VALUES (UNHEX(REPLACE('a7241e73-6969-4799-ae21-48c93fac25f4', '-', '')), 'E-Sports');
INSERT INTO categories (id, name) VALUES (UNHEX(REPLACE('87744af7-5198-41e7-9e33-59e15863964c', '-', '')), 'Soccer');
INSERT INTO categories (id, name) VALUES (UNHEX(REPLACE('77bcff79-5b4e-45e4-81e6-408850aa818e', '-', '')), 'Swimming');
INSERT INTO categories (id, name) VALUES (UNHEX(REPLACE('362df1eb-8707-4cc0-99b3-aea50c6747d7', '-', '')), 'Notebooks');
INSERT INTO categories (id, name) VALUES (UNHEX(REPLACE('9e0371a4-0c0c-4ee0-9e64-7497e72190af', '-', '')), 'Smartphones');

/* INHERITANCE WITH SINGLE_TABLE */
INSERT INTO payments (order_id, status, card_number, dtype) VALUES (UNHEX(REPLACE('47cbd3b5-aa9a-4644-95b3-0d9ca0551328', '-', '')), 'PROCESSING', '1234 5678 9012 3456', 'CardPayment');
INSERT INTO payments (order_id, status, card_number, dtype) VALUES (UNHEX(REPLACE('24be65bf-8e80-477c-81c5-277697b1bd37', '-', '')), 'PROCESSING', '1234 5678 9012 3456', 'CardPayment');

/* INHERITANCE WITH TABLE_PER_CLASS */
-- INSERT INTO card_payments (order_id, status, card_number) VALUES (UNHEX(REPLACE('47cbd3b5-aa9a-4644-95b3-0d9ca0551328', '-', '')), 'PROCESSING', '1234 5678 9012 3456');

/* INHERITANCE WITH JOINED */
-- INSERT INTO payments (order_id, status, dtype) VALUES (UNHEX(REPLACE('47cbd3b5-aa9a-4644-95b3-0d9ca0551328', '-', '')), 'PROCESSING', 'CardPayment');
-- INSERT INTO card_payments (order_id, card_number) VALUES (UNHEX(REPLACE('47cbd3b5-aa9a-4644-95b3-0d9ca0551328', '-', '')), '1234 5678 9012 3456');

INSERT INTO products_categories (product_id, category_id) VALUES (UNHEX(REPLACE('225ef043-f0a4-4c6d-b896-0ebf415dad93', '-', '')), UNHEX(REPLACE('65a38317-8d2b-43a9-ba84-0f6610bdc128', '-', '')));
INSERT INTO products_categories (product_id, category_id) VALUES (UNHEX(REPLACE('73beb2ec-5a28-43db-93a8-0cdd823fc2c6', '-', '')), UNHEX(REPLACE('65a38317-8d2b-43a9-ba84-0f6610bdc128', '-', '')));
INSERT INTO products_categories (product_id, category_id) VALUES (UNHEX(REPLACE('ab5666b6-3106-469b-9e34-2963b801466a', '-', '')), UNHEX(REPLACE('26ea828c-45b6-44e7-9c89-e76732123052', '-', '')));
INSERT INTO products_categories (product_id, category_id) VALUES (UNHEX(REPLACE('77c31aa8-14f5-4df1-9a96-fa03d6882f4f', '-', '')), UNHEX(REPLACE('d9e5d6f8-6605-4dcd-a21a-3839407a0a1f', '-', '')));

INSERT INTO invoices (order_id, xml, emission_date) VALUES (UNHEX(REPLACE('24be65bf-8e80-477c-81c5-277697b1bd37', '-', '')), '<xml />', '2024-12-27 19:07:35.624173');
