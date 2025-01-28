-- Actually all tables are generated by metadata

CREATE FUNCTION greater_than_average_billing(value double) RETURNS BOOLEAN READS SQL DATA RETURN value >= (SELECT AVG(total) FROM orders);

CREATE TABLE ecm_products (product_id BINARY(16) NOT NULL, product_name VARCHAR(100) NOT NULL, product_description LONGTEXT, product_price DECIMAL(10, 2), product_photo LONGBLOB, product_created_at DATETIME(6) NOT NULL, product_updated_at DATETIME(6), PRIMARY KEY (product_id));