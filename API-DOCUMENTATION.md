# Tiffany Furniture API Documentation

Base URL: `http://your-server:9090/api/v1`

## Table of Contents
1. [Product API](#product-api)
2. [Category API](#category-api)
3. [About Us API](#about-us-api)
4. [Banner API](#banner-api)
5. [Order API](#order-api)
6. [Common Response Structure](#common-response-structure)
7. [Enums](#enums)

---

## Product API

### 1. Get All Products (Paginated)
**Endpoint:** `POST /products/all`

**Request Body:**
```json
{
  "pageNo": 1,
  "pageSize": 10,
  "search": "sofa",
  "status": "ACTIVE",
  "categoryId": "a1111111-1111-1111-1111-111111111111",
  "subCategoryId": "b1111111-1111-1111-1111-111111111111"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Products retrieved successfully",
  "data": {
    "content": [
      {
        "id": "p1111111-1111-1111-1111-111111111111",
        "name": "Modern L-Shaped Sofa",
        "description": "Luxurious L-shaped sofa...",
        "imageUrl": "/api/images/product-l-shaped-sofa.jpg",
        "basePrice": 1299.99,
        "status": "ACTIVE",
        "categoryId": "a1111111-1111-1111-1111-111111111111",
        "categoryName": "Living Room",
        "subCategoryId": "b1111111-1111-1111-1111-111111111111",
        "subCategoryName": "Sofas",
        "productView": 145,
        "attributes": [],
        "variants": []
      }
    ],
    "pageNo": 1,
    "pageSize": 10,
    "totalElements": 15,
    "totalPages": 2,
    "last": false
  }
}
```

### 2. Get Product by ID
**Endpoint:** `GET /products/{id}`

**Response:**
```json
{
  "success": true,
  "message": "Product retrieved successfully",
  "data": {
    "id": "p1111111-1111-1111-1111-111111111111",
    "name": "Modern L-Shaped Sofa",
    "description": "Luxurious L-shaped sofa...",
    "imageUrl": "/api/images/product-l-shaped-sofa.jpg",
    "basePrice": 1299.99,
    "status": "ACTIVE",
    "categoryId": "a1111111-1111-1111-1111-111111111111",
    "categoryName": "Living Room",
    "subCategoryId": "b1111111-1111-1111-1111-111111111111",
    "subCategoryName": "Sofas",
    "productView": 145,
    "attributes": [],
    "variants": []
  }
}
```

### 3. Create Product
**Endpoint:** `POST /products`

**Request Body:**
```json
{
  "name": "Modern L-Shaped Sofa",
  "description": "Luxurious L-shaped sofa with premium fabric upholstery",
  "imageUrl": "/api/images/product-l-shaped-sofa.jpg",
  "basePrice": 1299.99,
  "status": "ACTIVE",
  "categoryId": "a1111111-1111-1111-1111-111111111111",
  "subCategoryId": "b1111111-1111-1111-1111-111111111111",
  "attributes": [
    {
      "attributeName": "Material",
      "attributeValue": "Premium Fabric"
    },
    {
      "attributeName": "Color",
      "attributeValue": "Gray"
    }
  ],
  "variants": [
    {
      "name": "Standard Size",
      "price": 1299.99,
      "stock": 10,
      "discount": 10,
      "discountType": "PERCENTAGE",
      "discountStartDate": "2025-12-01T00:00:00",
      "discountEndDate": "2025-12-31T23:59:59",
      "imageCover": "/api/images/variant-standard.jpg"
    }
  ]
}
```

### 4. Update Product
**Endpoint:** `PUT /products/{id}`

**Request Body:**
```json
{
  "name": "Updated Modern L-Shaped Sofa",
  "description": "Updated description",
  "imageUrl": "/api/images/updated-sofa.jpg",
  "basePrice": 1399.99,
  "status": "ACTIVE",
  "categoryId": "a1111111-1111-1111-1111-111111111111",
  "subCategoryId": "b1111111-1111-1111-1111-111111111111"
}
```

### 5. Delete Product
**Endpoint:** `DELETE /products/{id}`

**Response:**
```json
{
  "success": true,
  "message": "Product deleted successfully",
  "data": null
}
```

### 6. Increment Product View
**Endpoint:** `POST /products/{id}/view`

**Response:**
```json
{
  "success": true,
  "message": "Product view incremented successfully",
  "data": null
}
```

---

## Category API

### 1. Get All Categories (Paginated)
**Endpoint:** `POST /categories/all`

**Request Body:**
```json
{
  "pageNo": 1,
  "pageSize": 10,
  "search": "living",
  "status": "ACTIVE"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Categories retrieved successfully",
  "data": {
    "content": [
      {
        "id": "a1111111-1111-1111-1111-111111111111",
        "name": "Living Room",
        "imageUrl": "/api/images/category-living-room.jpg",
        "status": "ACTIVE"
      }
    ],
    "pageNo": 1,
    "pageSize": 10,
    "totalElements": 5,
    "totalPages": 1,
    "last": true
  }
}
```

### 2. Get Category by ID
**Endpoint:** `GET /categories/{id}`

**Response:**
```json
{
  "success": true,
  "message": "Category retrieved successfully",
  "data": {
    "id": "a1111111-1111-1111-1111-111111111111",
    "name": "Living Room",
    "imageUrl": "/api/images/category-living-room.jpg",
    "status": "ACTIVE"
  }
}
```

### 3. Create Category
**Endpoint:** `POST /categories`

**Request Body:**
```json
{
  "name": "Living Room",
  "imageUrl": "/api/images/category-living-room.jpg",
  "status": "ACTIVE"
}
```

### 4. Update Category
**Endpoint:** `PUT /categories/{id}`

**Request Body:**
```json
{
  "name": "Updated Living Room",
  "imageUrl": "/api/images/updated-category.jpg",
  "status": "ACTIVE"
}
```

### 5. Delete Category
**Endpoint:** `DELETE /categories/{id}`

---

## About Us API

### 1. Get About Us Information
**Endpoint:** `GET /about-us`

**Response:**
```json
{
  "success": true,
  "message": "About Us retrieved successfully",
  "data": {
    "id": "au111111-1111-1111-1111-111111111111",
    "email": "TiffanyfurnitureCompany@gmail.com",
    "location": "Phnom Penh, Cambodia",
    "phoneNumber": "015 971 189",
    "storePhone": "089 458 533",
    "bankName": "ABA Bank",
    "bankNumber": "001234567890",
    "availableTime": "8am to 5:30pm",
    "showroomHours": "8am to 5:30pm",
    "websiteUrl": "www.tiffanyfurniturecompany.com",
    "telegramUrl": "https://t.me/tiffanyfurniture",
    "messengerUrl": "https://m.me/tiffanyfurniture",
    "facebookUrl": "https://facebook.com/tiffanyfurniture",
    "instagramUrl": "https://instagram.com/tiffanyfurniture",
    "twitterUrl": "https://twitter.com/tiffanyfurniture",
    "aboutUsProfileImage": "/api/images/about-us-profile.jpg",
    "qrCodeImage": "/api/images/payment-qr-code.jpg",
    "description": "Tiffany Furniture Company is your premier destination..."
  }
}
```

### 2. Update About Us Information
**Endpoint:** `PUT /about-us`

**Request Body:**
```json
{
  "email": "TiffanyfurnitureCompany@gmail.com",
  "location": "Phnom Penh, Cambodia",
  "phoneNumber": "015 971 189",
  "storePhone": "089 458 533",
  "bankName": "ABA Bank",
  "bankNumber": "001234567890",
  "availableTime": "8am to 5:30pm",
  "showroomHours": "8am to 5:30pm",
  "websiteUrl": "www.tiffanyfurniturecompany.com",
  "telegramUrl": "https://t.me/tiffanyfurniture",
  "messengerUrl": "https://m.me/tiffanyfurniture",
  "facebookUrl": "https://facebook.com/tiffanyfurniture",
  "instagramUrl": "https://instagram.com/tiffanyfurniture",
  "twitterUrl": "https://twitter.com/tiffanyfurniture",
  "aboutUsProfileImage": "/api/images/about-us-profile.jpg",
  "qrCodeImage": "/api/images/payment-qr-code.jpg",
  "description": "Updated description..."
}
```

---

## Banner API

### 1. Get All Banners
**Endpoint:** `GET /banners`

**Response:**
```json
{
  "success": true,
  "message": "Banners retrieved successfully",
  "data": [
    {
      "id": "bn111111-1111-1111-1111-111111111111",
      "name": "Holiday Sale Banner",
      "imageUrl": "/api/images/banner-holiday-sale.jpg",
      "linkUrl": "/products?category=living-room",
      "displayOrder": 1,
      "status": "ACTIVE",
      "description": "Up to 50% off on selected items"
    },
    {
      "id": "bn222222-2222-2222-2222-222222222222",
      "name": "New Arrivals",
      "imageUrl": "/api/images/banner-new-arrivals.jpg",
      "linkUrl": "/products?sort=newest",
      "displayOrder": 2,
      "status": "ACTIVE",
      "description": "Check out our latest furniture collection"
    }
  ]
}
```

### 2. Get Banner by ID
**Endpoint:** `GET /banners/{id}`

**Response:**
```json
{
  "success": true,
  "message": "Banner retrieved successfully",
  "data": {
    "id": "bn111111-1111-1111-1111-111111111111",
    "name": "Holiday Sale Banner",
    "imageUrl": "/api/images/banner-holiday-sale.jpg",
    "linkUrl": "/products?category=living-room",
    "displayOrder": 1,
    "status": "ACTIVE",
    "description": "Up to 50% off on selected items"
  }
}
```

### 3. Create Banner
**Endpoint:** `POST /banners`

**Request Body:**
```json
{
  "name": "Holiday Sale Banner",
  "imageUrl": "/api/images/banner-holiday-sale.jpg",
  "linkUrl": "/products?category=living-room",
  "displayOrder": 1,
  "status": "ACTIVE",
  "description": "Up to 50% off on selected items"
}
```

### 4. Update Banner
**Endpoint:** `PUT /banners/{id}`

**Request Body:**
```json
{
  "name": "Updated Banner",
  "imageUrl": "/api/images/updated-banner.jpg",
  "linkUrl": "/products",
  "displayOrder": 1,
  "status": "ACTIVE",
  "description": "Updated description"
}
```

### 5. Delete Banner
**Endpoint:** `DELETE /banners/{id}`

---

## Order API

### 1. Get All Orders (Paginated)
**Endpoint:** `POST /orders/all`

**Request Body:**
```json
{
  "pageNo": 1,
  "pageSize": 10,
  "search": "Sok Dara",
  "status": "SUCCESS",
  "paymentStatus": "PAID"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Orders retrieved successfully",
  "data": {
    "content": [
      {
        "id": "o1111111-1111-1111-1111-111111111111",
        "orderNumber": "ORD-20251211143012-A1B2",
        "phoneNumber": "012 345 678",
        "customerName": "Sok Dara",
        "paymentMethod": "ABA Bank Transfer",
        "paymentStatus": "PAID",
        "totalAmount": 1699.98,
        "status": "SUCCESS",
        "createdAt": "2025-12-08T14:30:12",
        "items": [
          {
            "id": "oi111111-1111-1111-1111-111111111111",
            "productName": "Modern L-Shaped Sofa",
            "quantity": 1,
            "price": 1299.99,
            "total": 1299.99
          },
          {
            "id": "oi111111-2222-2222-2222-222222222222",
            "productName": "Cushioned Dining Chair Set",
            "quantity": 1,
            "price": 399.99,
            "total": 399.99
          }
        ]
      }
    ],
    "pageNo": 1,
    "pageSize": 10,
    "totalElements": 5,
    "totalPages": 1,
    "last": true
  }
}
```

### 2. Get Order by ID
**Endpoint:** `GET /orders/{id}`

**Response:**
```json
{
  "success": true,
  "message": "Order retrieved successfully",
  "data": {
    "id": "o1111111-1111-1111-1111-111111111111",
    "orderNumber": "ORD-20251211143012-A1B2",
    "phoneNumber": "012 345 678",
    "customerName": "Sok Dara",
    "paymentMethod": "ABA Bank Transfer",
    "paymentStatus": "PAID",
    "totalAmount": 1699.98,
    "status": "SUCCESS",
    "createdAt": "2025-12-08T14:30:12",
    "items": [...]
  }
}
```

### 3. Get Order by Order Number
**Endpoint:** `GET /orders/number/{orderNumber}`

Example: `GET /orders/number/ORD-20251211143012-A1B2`

### 4. Create Order
**Endpoint:** `POST /orders`

**Request Body:**
```json
{
  "phoneNumber": "012 345 678",
  "customerName": "Sok Dara",
  "paymentMethod": "ABA Bank Transfer",
  "paymentStatus": "UNPAID",
  "totalAmount": 1699.98,
  "status": "PROCESS",
  "items": [
    {
      "productName": "Modern L-Shaped Sofa",
      "quantity": 1,
      "price": 1299.99,
      "total": 1299.99
    },
    {
      "productName": "Cushioned Dining Chair Set",
      "quantity": 1,
      "price": 399.99,
      "total": 399.99
    }
  ]
}
```

**Response:**
```json
{
  "success": true,
  "message": "Order created successfully",
  "data": {
    "id": "o1111111-1111-1111-1111-111111111111",
    "orderNumber": "ORD-20251211163012-X9Y8",
    "phoneNumber": "012 345 678",
    "customerName": "Sok Dara",
    "paymentMethod": "ABA Bank Transfer",
    "paymentStatus": "UNPAID",
    "totalAmount": 1699.98,
    "status": "PROCESS",
    "createdAt": "2025-12-11T16:30:12",
    "items": [...]
  }
}
```

### 5. Update Order Status
**Endpoint:** `PUT /orders/{id}/status`

**Request Body:**
```json
{
  "status": "SUCCESS",
  "paymentStatus": "PAID"
}
```

### 6. Delete Order
**Endpoint:** `DELETE /orders/{id}`

---

## Common Response Structure

All API responses follow this structure:

```json
{
  "success": boolean,
  "message": string,
  "data": object | array | null
}
```

---

## Enums

### Status
- `ACTIVE`
- `INACTIVE`

### OrderStatus
- `SUCCESS`
- `PROCESS`
- `FAIL`

### PaymentStatus
- `PAID`
- `UNPAID`

---

## Error Responses

### 400 Bad Request
```json
{
  "success": false,
  "message": "Validation error message",
  "data": null
}
```

### 404 Not Found
```json
{
  "success": false,
  "message": "Resource not found with ID: {id}",
  "data": null
}
```

### 500 Internal Server Error
```json
{
  "success": false,
  "message": "Internal server error",
  "data": null
}
```

---

## Notes for Frontend Developers

1. **Pagination**: All list endpoints use POST method with request body for pagination and filtering
2. **Date Format**: All dates are in ISO 8601 format (`YYYY-MM-DDTHH:mm:ss`)
3. **UUIDs**: All IDs are UUID v4 format
4. **Soft Delete**: Delete operations are soft deletes (records are marked as deleted but not removed from database)
5. **Order Numbers**: Auto-generated in format `ORD-{timestamp}-{random}` (e.g., `ORD-20251211143012-A1B2`)
6. **Image URLs**: All image URLs are relative paths that should be prefixed with your server base URL
