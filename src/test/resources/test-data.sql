DELETE FROM transactions;
DELETE FROM offers;
DELETE FROM users;
DELETE FROM categories;

INSERT INTO categories (id, name) VALUES (1, 'Food');
INSERT INTO categories (id, name) VALUES (2, 'Loan');
INSERT INTO categories (id, name) VALUES (3, 'Pet Care');
INSERT INTO categories (id, name) VALUES (4, 'Classes');
INSERT INTO categories (id, name) VALUES (5, 'Exchange');
INSERT INTO categories (id, name) VALUES (6, 'Handmade Products');
INSERT INTO categories (id, name) VALUES (7, 'Electronics');

ALTER TABLE categories ALTER COLUMN id RESTART WITH 100;
ALTER TABLE users ALTER COLUMN id RESTART WITH 100;
ALTER TABLE offers ALTER COLUMN id RESTART WITH 100;
ALTER TABLE transactions ALTER COLUMN id RESTART WITH 100;

INSERT INTO users (id, username, name, email, password, role, location, balance) VALUES
(1, 'admin', 'Admin User', 'admin@email.com', '$2a$12$rRwUjOC9ZpCfubz9fU5GROn4Bw0yUsvyDCxEVijf.529S6l6DZwWO', 'ADMIN', 'Valencia', 100),
(2, 'rubens_garcia', 'Rubens Garcia', 'elrubens@email.com', '$2a$12$rRwUjOC9ZpCfubz9fU5GROn4Bw0yUsvyDCxEVijf.529S6l6DZwWO', 'USER', 'Xirivella', 77),
(3, 'selma_gonzalez', 'Selma Gonzalez', 'selma@email.com', '$2a$12$rRwUjOC9ZpCfubz9fU5GROn4Bw0yUsvyDCxEVijf.529S6l6DZwWO', 'USER', 'Valencia', 80),
(4, 'anna_magaro', 'Anna Carolina Magaro', 'annacarolina@email.com', '$2a$12$rRwUjOC9ZpCfubz9fU5GROn4Bw0yUsvyDCxEVijf.529S6l6DZwWO', 'USER', 'Valencia', 30),
(5, 'lorena_pumarino', 'Lorena Pumariño', 'lorena@email.com', '$2a$12$rRwUjOC9ZpCfubz9fU5GROn4Bw0yUsvyDCxEVijf.529S6l6DZwWO', 'USER', 'Paterna', 75),
(6, 'carlos_valls', 'Carlos Valls', 'carlos@email.com', '$2a$12$rRwUjOC9ZpCfubz9fU5GROn4Bw0yUsvyDCxEVijf.529S6l6DZwWO', 'USER', 'Patacona', 67),
(7, 'belen_valiente', 'Belén Valiente', 'belen@email.com', '$2a$12$rRwUjOC9ZpCfubz9fU5GROn4Bw0yUsvyDCxEVijf.529S6l6DZwWO', 'USER', 'Alboraya', 48),
(8, 'stefano_ventrudo', 'Stefano Ventrudo', 'stefano@email.com', '$2a$12$rRwUjOC9ZpCfubz9fU5GROn4Bw0yUsvyDCxEVijf.529S6l6DZwWO', 'USER', 'Torrent', 23),
(9, 'delia_molina', 'Delia Molina Cordero', 'delia@email.com', '$2a$12$rRwUjOC9ZpCfubz9fU5GROn4Bw0yUsvyDCxEVijf.529S6l6DZwWO', 'USER', 'Aldaya', 51);

INSERT INTO offers (id, title, description, category_id, price, owner_id, location, is_unique) VALUES
(1, 'Homemade vegan cheese', 'Fermented almond cheese with herbs', 1, 10, 2, 'Valencia', false),
(2, 'Yoga class in the park', '1-hour Hatha Yoga session', 4, 15, 4, 'Paterna', false),
(3, 'Sourdough bread', 'Fresh homemade rye bread', 1, 7, 1, 'Xirivella', false),
(4, 'Mountain bike', 'Full-suspension mountain bike for trail rides', 5, 80, 2, 'Valencia', true),
(5, 'Pet sitter', 'I''ll take care of your dogs or cats while you travel, for one hour', 3, 5, 6, 'Alboraya', false),
(6, 'Lend a drill', 'Electric drill for short-term use', 2, 12, 3, 'Valencia', false),
(7, 'French conversation exchange', '30-minute call to practice speaking', 4, 8, 5, 'La Eliana', false),
(8, 'Vegan cupcakes', 'Box of 6 cupcakes - gluten-free', 1, 9, 7, 'Torrent', false),
(9, 'Knitted scarf', 'Handmade wool scarf', 6, 20, 8, 'Aldaya', true);

INSERT INTO transactions (id, offer_id, receiver_user_id, customer_user_id, transaction_date) VALUES
(1, 5, 6, 4, TIMESTAMP '2025-08-23 10:00:00'),
(2, 6, 3, 6, TIMESTAMP '2025-08-24 14:30:00'),
(3, 7, 5, 8, TIMESTAMP '2025-08-25 09:15:00'),
(4, 9, 8, 3, TIMESTAMP '2025-08-26 16:45:00'),
(5, 1, 2, 5, TIMESTAMP '2025-08-27 11:20:00'),
(6, 3, 1, 7, TIMESTAMP '2025-08-28 13:50:00');