-- Sample data for Order Management API

-- Insert sample orders
INSERT INTO orders (id, customer_name, total_amount, status, order_date, last_modified_date) VALUES
    (1, 'John Doe', 299.98, 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Jane Smith', 149.99, 'PROCESSING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'Bob Johnson', 599.97, 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'Alice Williams', 79.99, 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample order items for order 1
INSERT INTO order_items (id, product_name, quantity, unit_price, order_id) VALUES
    (1, 'Laptop', 1, 199.99, 1),
    (2, 'Mouse', 2, 49.99, 1);

-- Insert sample order items for order 2
INSERT INTO order_items (id, product_name, quantity, unit_price, order_id) VALUES
    (3, 'Keyboard', 1, 149.99, 2);

-- Insert sample order items for order 3
INSERT INTO order_items (id, product_name, quantity, unit_price, order_id) VALUES
    (4, 'Monitor', 2, 299.99, 3);

-- Insert sample order items for order 4
INSERT INTO order_items (id, product_name, quantity, unit_price, order_id) VALUES
    (5, 'Headphones', 1, 79.99, 4);
