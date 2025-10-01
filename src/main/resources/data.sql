-- Insert roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_MODERATOR');

-- Insert users (password is 'password' encoded with BCrypt)
INSERT INTO users (username, password, email, first_name, last_name, enabled) VALUES 
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'admin@example.com', 'Admin', 'User', true),
('user', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'user@example.com', 'Regular', 'User', true),
('moderator', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'moderator@example.com', 'Moderator', 'User', true);

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES 
(1, 2), -- admin has ROLE_ADMIN
(2, 1), -- user has ROLE_USER
(3, 3); -- moderator has ROLE_MODERATOR
