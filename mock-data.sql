-- =============================================
-- Tiffany Furniture Database Mock Data
-- =============================================

-- Clear existing data (in reverse order of dependencies)
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM products;
DELETE FROM sub_categories;
DELETE FROM categories;
DELETE FROM banners;
DELETE FROM about_us;

-- =============================================
-- CATEGORIES
-- =============================================
INSERT INTO categories (id, name, image_url, status, is_deleted, created_at, updated_at, created_by, updated_by, version) VALUES
('a1111111-1111-1111-1111-111111111111', 'Living Room', '/api/images/category-living-room.jpg', 'ACTIVE', false, NOW(), NOW(), 'system', 'system', 0),
('a2222222-2222-2222-2222-222222222222', 'Dining Room', '/api/images/category-dining-room.jpg', 'ACTIVE', false, NOW(), NOW(), 'system', 'system', 0),
('a3333333-3333-3333-3333-333333333333', 'Bedroom', '/api/images/category-bedroom.jpg', 'ACTIVE', false, NOW(), NOW(), 'system', 'system', 0),
('a4444444-4444-4444-4444-444444444444', 'Office', '/api/images/category-office.jpg', 'ACTIVE', false, NOW(), NOW(), 'system', 'system', 0),
('a5555555-5555-5555-5555-555555555555', 'Outdoor', '/api/images/category-outdoor.jpg', 'ACTIVE', false, NOW(), NOW(), 'system', 'system', 0);

-- =============================================
-- SUB-CATEGORIES
-- =============================================
INSERT INTO sub_categories (id, name, image_url, status, category_id, is_deleted, created_at, updated_at, created_by, updated_by, version) VALUES
-- Living Room Subcategories
('b1111111-1111-1111-1111-111111111111', 'Sofas', '/api/images/subcat-sofas.jpg', 'ACTIVE', 'a1111111-1111-1111-1111-111111111111', false, NOW(), NOW(), 'system', 'system', 0),
('b1111111-2222-2222-2222-222222222222', 'Coffee Tables', '/api/images/subcat-coffee-tables.jpg', 'ACTIVE', 'a1111111-1111-1111-1111-111111111111', false, NOW(), NOW(), 'system', 'system', 0),
('b1111111-3333-3333-3333-333333333333', 'TV Stands', '/api/images/subcat-tv-stands.jpg', 'ACTIVE', 'a1111111-1111-1111-1111-111111111111', false, NOW(), NOW(), 'system', 'system', 0),

-- Dining Room Subcategories
('b2222222-1111-1111-1111-111111111111', 'Dining Tables', '/api/images/subcat-dining-tables.jpg', 'ACTIVE', 'a2222222-2222-2222-2222-222222222222', false, NOW(), NOW(), 'system', 'system', 0),
('b2222222-2222-2222-2222-222222222222', 'Dining Chairs', '/api/images/subcat-dining-chairs.jpg', 'ACTIVE', 'a2222222-2222-2222-2222-222222222222', false, NOW(), NOW(), 'system', 'system', 0),
('b2222222-3333-3333-3333-333333333333', 'Buffets', '/api/images/subcat-buffets.jpg', 'ACTIVE', 'a2222222-2222-2222-2222-222222222222', false, NOW(), NOW(), 'system', 'system', 0),

-- Bedroom Subcategories
('b3333333-1111-1111-1111-111111111111', 'Beds', '/api/images/subcat-beds.jpg', 'ACTIVE', 'a3333333-3333-3333-3333-333333333333', false, NOW(), NOW(), 'system', 'system', 0),
('b3333333-2222-2222-2222-222222222222', 'Wardrobes', '/api/images/subcat-wardrobes.jpg', 'ACTIVE', 'a3333333-3333-3333-3333-333333333333', false, NOW(), NOW(), 'system', 'system', 0),
('b3333333-3333-3333-3333-333333333333', 'Nightstands', '/api/images/subcat-nightstands.jpg', 'ACTIVE', 'a3333333-3333-3333-3333-333333333333', false, NOW(), NOW(), 'system', 'system', 0),

-- Office Subcategories
('b4444444-1111-1111-1111-111111111111', 'Desks', '/api/images/subcat-desks.jpg', 'ACTIVE', 'a4444444-4444-4444-4444-444444444444', false, NOW(), NOW(), 'system', 'system', 0),
('b4444444-2222-2222-2222-222222222222', 'Office Chairs', '/api/images/subcat-office-chairs.jpg', 'ACTIVE', 'a4444444-4444-4444-4444-444444444444', false, NOW(), NOW(), 'system', 'system', 0),

-- Outdoor Subcategories
('b5555555-1111-1111-1111-111111111111', 'Patio Sets', '/api/images/subcat-patio-sets.jpg', 'ACTIVE', 'a5555555-5555-5555-5555-555555555555', false, NOW(), NOW(), 'system', 'system', 0),
('b5555555-2222-2222-2222-222222222222', 'Garden Benches', '/api/images/subcat-garden-benches.jpg', 'ACTIVE', 'a5555555-5555-5555-5555-555555555555', false, NOW(), NOW(), 'system', 'system', 0);

-- =============================================
-- PRODUCTS
-- =============================================
INSERT INTO products (id, name, description, image_url, base_price, status, category_id, sub_category_id, product_view, is_deleted, created_at, updated_at, created_by, updated_by, version) VALUES
-- Living Room Products (Sofas)
('p1111111-1111-1111-1111-111111111111', 'Modern L-Shaped Sofa', 'Luxurious L-shaped sofa with premium fabric upholstery. Perfect for large living rooms.', '/api/images/product-l-shaped-sofa.jpg', 1299.99, 'ACTIVE', 'a1111111-1111-1111-1111-111111111111', 'b1111111-1111-1111-1111-111111111111', 145, false, NOW(), NOW(), 'system', 'system', 0),
('p1111111-2222-2222-2222-222222222222', 'Classic 3-Seater Sofa', 'Elegant 3-seater sofa with wooden frame and cushioned seats.', '/api/images/product-3-seater-sofa.jpg', 799.99, 'ACTIVE', 'a1111111-1111-1111-1111-111111111111', 'b1111111-1111-1111-1111-111111111111', 89, false, NOW(), NOW(), 'system', 'system', 0),
('p1111111-3333-3333-3333-333333333333', 'Leather Recliner Sofa', 'Premium leather recliner with adjustable headrest and footrest.', '/api/images/product-recliner-sofa.jpg', 1599.99, 'ACTIVE', 'a1111111-1111-1111-1111-111111111111', 'b1111111-1111-1111-1111-111111111111', 234, false, NOW(), NOW(), 'system', 'system', 0),

-- Living Room Products (Coffee Tables)
('p1111111-4444-4444-4444-444444444444', 'Glass Top Coffee Table', 'Modern glass top coffee table with chrome finish legs.', '/api/images/product-glass-coffee-table.jpg', 299.99, 'ACTIVE', 'a1111111-1111-1111-1111-111111111111', 'b1111111-2222-2222-2222-222222222222', 67, false, NOW(), NOW(), 'system', 'system', 0),
('p1111111-5555-5555-5555-555555555555', 'Wooden Coffee Table', 'Solid oak coffee table with storage drawers.', '/api/images/product-wooden-coffee-table.jpg', 449.99, 'ACTIVE', 'a1111111-1111-1111-1111-111111111111', 'b1111111-2222-2222-2222-222222222222', 123, false, NOW(), NOW(), 'system', 'system', 0),

-- Dining Room Products
('p2222222-1111-1111-1111-111111111111', '6-Seater Dining Table', 'Spacious dining table for 6 people with modern design.', '/api/images/product-6-seater-table.jpg', 899.99, 'ACTIVE', 'a2222222-2222-2222-2222-222222222222', 'b2222222-1111-1111-1111-111111111111', 178, false, NOW(), NOW(), 'system', 'system', 0),
('p2222222-2222-2222-2222-222222222222', '4-Seater Compact Dining Table', 'Perfect for small apartments. Made from solid wood.', '/api/images/product-4-seater-table.jpg', 549.99, 'ACTIVE', 'a2222222-2222-2222-2222-222222222222', 'b2222222-1111-1111-1111-111111111111', 92, false, NOW(), NOW(), 'system', 'system', 0),
('p2222222-3333-3333-3333-333333333333', 'Cushioned Dining Chair Set', 'Set of 4 cushioned dining chairs with ergonomic design.', '/api/images/product-dining-chairs.jpg', 399.99, 'ACTIVE', 'a2222222-2222-2222-2222-222222222222', 'b2222222-2222-2222-2222-222222222222', 156, false, NOW(), NOW(), 'system', 'system', 0),

-- Bedroom Products
('p3333333-1111-1111-1111-111111111111', 'King Size Bed Frame', 'Sturdy king size bed frame with headboard.', '/api/images/product-king-bed.jpg', 1199.99, 'ACTIVE', 'a3333333-3333-3333-3333-333333333333', 'b3333333-1111-1111-1111-111111111111', 203, false, NOW(), NOW(), 'system', 'system', 0),
('p3333333-2222-2222-2222-222222222222', 'Queen Size Storage Bed', 'Queen bed with built-in storage drawers.', '/api/images/product-queen-storage-bed.jpg', 949.99, 'ACTIVE', 'a3333333-3333-3333-3333-333333333333', 'b3333333-1111-1111-1111-111111111111', 167, false, NOW(), NOW(), 'system', 'system', 0),
('p3333333-3333-3333-3333-333333333333', '3-Door Wardrobe', 'Spacious wardrobe with mirror and hanging space.', '/api/images/product-wardrobe.jpg', 699.99, 'ACTIVE', 'a3333333-3333-3333-3333-333333333333', 'b3333333-2222-2222-2222-222222222222', 134, false, NOW(), NOW(), 'system', 'system', 0),

-- Office Products
('p4444444-1111-1111-1111-111111111111', 'Executive Office Desk', 'Large executive desk with multiple drawers.', '/api/images/product-executive-desk.jpg', 849.99, 'ACTIVE', 'a4444444-4444-4444-4444-444444444444', 'b4444444-1111-1111-1111-111111111111', 78, false, NOW(), NOW(), 'system', 'system', 0),
('p4444444-2222-2222-2222-222222222222', 'Ergonomic Office Chair', 'Adjustable ergonomic chair with lumbar support.', '/api/images/product-office-chair.jpg', 349.99, 'ACTIVE', 'a4444444-4444-4444-4444-444444444444', 'b4444444-2222-2222-2222-222222222222', 211, false, NOW(), NOW(), 'system', 'system', 0),

-- Outdoor Products
('p5555555-1111-1111-1111-111111111111', '5-Piece Patio Set', 'Weather-resistant patio furniture set.', '/api/images/product-patio-set.jpg', 1099.99, 'ACTIVE', 'a5555555-5555-5555-5555-555555555555', 'b5555555-1111-1111-1111-111111111111', 56, false, NOW(), NOW(), 'system', 'system', 0),
('p5555555-2222-2222-2222-222222222222', 'Teak Garden Bench', 'Classic teak wood garden bench.', '/api/images/product-garden-bench.jpg', 329.99, 'ACTIVE', 'a5555555-5555-5555-5555-555555555555', 'b5555555-2222-2222-2222-222222222222', 43, false, NOW(), NOW(), 'system', 'system', 0);

-- =============================================
-- BANNERS
-- =============================================
INSERT INTO banners (id, name, image_url, link_url, display_order, status, description, is_deleted, created_at, updated_at, created_by, updated_by, version) VALUES
('bn111111-1111-1111-1111-111111111111', 'Holiday Sale Banner', '/api/images/banner-holiday-sale.jpg', '/products?category=living-room', 1, 'ACTIVE', 'Up to 50% off on selected items', false, NOW(), NOW(), 'system', 'system', 0),
('bn222222-2222-2222-2222-222222222222', 'New Arrivals', '/api/images/banner-new-arrivals.jpg', '/products?sort=newest', 2, 'ACTIVE', 'Check out our latest furniture collection', false, NOW(), NOW(), 'system', 'system', 0),
('bn333333-3333-3333-3333-333333333333', 'Free Delivery', '/api/images/banner-free-delivery.jpg', null, 3, 'ACTIVE', 'Free delivery on orders over $500', false, NOW(), NOW(), 'system', 'system', 0);

-- =============================================
-- ABOUT US
-- =============================================
INSERT INTO about_us (id, email, location, phone_number, store_phone, bank_name, bank_number, available_time, showroom_hours, website_url, telegram_url, messenger_url, facebook_url, instagram_url, twitter_url, about_us_profile_image, qr_code_image, description, is_deleted, created_at, updated_at, created_by, updated_by, version) VALUES
('au111111-1111-1111-1111-111111111111', 
'TiffanyfurnitureCompany@gmail.com', 
'Phnom Penh, Cambodia', 
'015 971 189', 
'089 458 533', 
'ABA Bank', 
'001234567890', 
'8am to 5:30pm', 
'8am to 5:30pm', 
'www.tiffanyfurniturecompany.com', 
'https://t.me/tiffanyfurniture', 
'https://m.me/tiffanyfurniture', 
'https://facebook.com/tiffanyfurniture', 
'https://instagram.com/tiffanyfurniture', 
'https://twitter.com/tiffanyfurniture', 
'/api/images/about-us-profile.jpg', 
'/api/images/payment-qr-code.jpg', 
'Tiffany Furniture Company is your premier destination for quality furniture in Cambodia. We offer a wide selection of modern and classic furniture pieces for every room in your home. With over 10 years of experience, we are committed to providing excellent customer service and high-quality products at competitive prices.',
false, NOW(), NOW(), 'system', 'system', 0);

-- =============================================
-- ORDERS
-- =============================================
INSERT INTO orders (id, order_number, phone_number, customer_name, payment_method, payment_status, total_amount, status, is_deleted, created_at, updated_at, created_by, updated_by, version) VALUES
('o1111111-1111-1111-1111-111111111111', 'ORD-20251211143012-A1B2', '012 345 678', 'Sok Dara', 'ABA Bank Transfer', 'PAID', 1699.98, 'SUCCESS', false, NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days', 'system', 'system', 0),
('o2222222-2222-2222-2222-222222222222', 'ORD-20251211150045-C3D4', '015 876 543', 'Chan Sophea', 'Cash on Delivery', 'UNPAID', 949.99, 'PROCESS', false, NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', 'system', 'system', 0),
('o3333333-3333-3333-3333-333333333333', 'ORD-20251211120530-E5F6', '098 765 432', 'Pov Rithy', 'Wing Transfer', 'PAID', 1549.98, 'SUCCESS', false, NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days', 'system', 'system', 0),
('o4444444-4444-4444-4444-444444444444', 'ORD-20251211161245-G7H8', '077 123 456', 'Neak Pheakdey', 'ABA Bank Transfer', 'UNPAID', 799.99, 'PROCESS', false, NOW() - INTERVAL '2 hours', NOW() - INTERVAL '2 hours', 'system', 'system', 0),
('o5555555-5555-5555-5555-555555555555', 'ORD-20251209093020-I9J0', '016 234 567', 'Lim Veasna', 'Cash on Delivery', 'PAID', 449.99, 'FAIL', false, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days', 'system', 'system', 0);

-- =============================================
-- ORDER ITEMS
-- =============================================
INSERT INTO order_items (id, product_name, quantity, price, total, order_id, is_deleted, created_at, updated_at, created_by, updated_by, version) VALUES
-- Order 1 items
('oi111111-1111-1111-1111-111111111111', 'Modern L-Shaped Sofa', 1, 1299.99, 1299.99, 'o1111111-1111-1111-1111-111111111111', false, NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days', 'system', 'system', 0),
('oi111111-2222-2222-2222-222222222222', 'Cushioned Dining Chair Set', 1, 399.99, 399.99, 'o1111111-1111-1111-1111-111111111111', false, NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days', 'system', 'system', 0),

-- Order 2 items
('oi222222-1111-1111-1111-111111111111', 'Queen Size Storage Bed', 1, 949.99, 949.99, 'o2222222-2222-2222-2222-222222222222', false, NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day', 'system', 'system', 0),

-- Order 3 items
('oi333333-1111-1111-1111-111111111111', 'Classic 3-Seater Sofa', 1, 799.99, 799.99, 'o3333333-3333-3333-3333-333333333333', false, NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days', 'system', 'system', 0),
('oi333333-2222-2222-2222-222222222222', 'Wooden Coffee Table', 1, 449.99, 449.99, 'o3333333-3333-3333-3333-333333333333', false, NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days', 'system', 'system', 0),
('oi333333-3333-3333-3333-333333333333', 'Glass Top Coffee Table', 1, 299.99, 299.99, 'o3333333-3333-3333-3333-333333333333', false, NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days', 'system', 'system', 0),

-- Order 4 items
('oi444444-1111-1111-1111-111111111111', 'Classic 3-Seater Sofa', 1, 799.99, 799.99, 'o4444444-4444-4444-4444-444444444444', false, NOW() - INTERVAL '2 hours', NOW() - INTERVAL '2 hours', 'system', 'system', 0),

-- Order 5 items
('oi555555-1111-1111-1111-111111111111', 'Wooden Coffee Table', 1, 449.99, 449.99, 'o5555555-5555-5555-5555-555555555555', false, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days', 'system', 'system', 0);

-- =============================================
-- Summary
-- =============================================
-- Categories: 5
-- Sub-categories: 13
-- Products: 15
-- Banners: 3
-- About Us: 1
-- Orders: 5
-- Order Items: 8
