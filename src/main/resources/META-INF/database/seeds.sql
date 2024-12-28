INSERT INTO products (id, name, price, description) VALUES (UNHEX(REPLACE('ab5666b6-3106-469b-9e34-2963b801466a', '-', '')), 'Kindle', 499.00, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
INSERT INTO products (id, name, price, description) VALUES (UNHEX(REPLACE('77c31aa8-14f5-4df1-9a96-fa03d6882f4f', '-', '')), 'Camera GoPro Hero 7', 1400.00, 'Desempenho 2x melhor.');

INSERT INTO clients (id, name) VALUES (UNHEX(REPLACE('737fac65-ec05-4173-a522-00833a22271b', '-', '')), 'João da Silva');
INSERT INTO clients (id, name) VALUES (UNHEX(REPLACE('00492c10-234a-4388-9375-2da767ce0d6a', '-', '')), 'Manoel');

INSERT INTO orders (id, total, ordered_at, status) VALUES (UNHEX(REPLACE('24be65bf-8e80-477c-81c5-277697b1bd37', '-', '')), '10.00', '2024-12-27 19:07:35.624173', 'WAITING');
INSERT INTO orders (id, total, ordered_at, status) VALUES (UNHEX(REPLACE('07e419cc-f461-42c6-8055-fca267c407ef', '-', '')), '10.00', '2024-12-27 02:36:35.624173', 'WAITING');

INSERT INTO order_items (id, order_id, product_price, quantity) VALUES (UNHEX(REPLACE('6af35921-75c4-471a-914e-9597b4d3fce7', '-', '')), UNHEX(REPLACE('07e419cc-f461-42c6-8055-fca267c407ef', '-', '')), '10.00', 2);

INSERT INTO categories (id, name) VALUES (UNHEX(REPLACE('d9e5d6f8-6605-4dcd-a21a-3839407a0a1f', '-', '')), 'Eletronicos');
