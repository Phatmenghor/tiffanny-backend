# Product Management API Documentation for Frontend

## Base URL
```
http://localhost:8080/api/v1
```

## Common Response Wrapper
All endpoints return responses wrapped in this format:

```json
{
  "success": true,
  "message": "Operation successful message",
  "data": { /* actual response data */ },
  "timestamp": "2025-12-08T16:15:10"
}
```

## Common Error Response
```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2025-12-08T16:15:10"
}
```

---

# 1. Category Management API

## 1.1 Create Category
**POST** `/categories`

### Request Body
```json
{
  "name": "Electronics",
  "imageUrl": "https://example.com/images/electronics.jpg",
  "status": "ACTIVE"
}
```

| Field | Type | Required | Description | Enum Values |
|-------|------|----------|-------------|-------------|
| name | string | ✅ | Category name | - |
| imageUrl | string | ❌ | Category image URL | - |
| status | string | ✅ | Category status | ACTIVE, INACTIVE |

### Response (201 Created)
```json
{
  "success": true,
  "message": "Category created successfully",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Electronics",
    "imageUrl": "https://example.com/images/electronics.jpg",
    "status": "ACTIVE",
    "subCategoryCount": 0,
    "productCount": 0,
    "createdAt": "2025-12-08T16:15:10",
    "updatedAt": "2025-12-08T16:15:10",
    "createdBy": "admin@example.com",
    "updatedBy": "admin@example.com"
  }
}
```

---

## 1.2 Update Category
**PUT** `/categories/{id}`

### Request Body
```json
{
  "name": "Electronics & Gadgets",
  "imageUrl": "https://example.com/images/electronics-updated.jpg",
  "status": "ACTIVE"
}
```

### Response (200 OK)
Same structure as Create Category response.

---

## 1.3 Delete Category (Soft Delete)
**DELETE** `/categories/{id}`

### Response (200 OK)
```json
{
  "success": true,
  "message": "Category deleted successfully",
  "data": null
}
```

---

## 1.4 Get Category by ID
**GET** `/categories/{id}`

### Response (200 OK)
```json
{
  "success": true,
  "message": "Category retrieved successfully",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Electronics",
    "imageUrl": "https://example.com/images/electronics.jpg",
    "status": "ACTIVE",
    "subCategoryCount": 5,
    "productCount": 23,
    "createdAt": "2025-12-08T16:15:10",
    "updatedAt": "2025-12-08T16:15:10",
    "createdBy": "admin@example.com",
    "updatedBy": "admin@example.com"
  }
}
```

---

## 1.5 Get All Categories
**GET** `/categories`

### Response (200 OK)
```json
{
  "success": true,
  "message": "Categories retrieved successfully",
  "data": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "name": "Electronics",
      "imageUrl": "https://example.com/images/electronics.jpg",
      "status": "ACTIVE",
      "subCategoryCount": 5,
      "productCount": 23,
      "createdAt": "2025-12-08T16:15:10",
      "updatedAt": "2025-12-08T16:15:10",
      "createdBy": "admin@example.com",
      "updatedBy": "admin@example.com"
    }
  ]
}
```

---

## 1.6 Get Categories by Status
**GET** `/categories/status/{status}`

**Path Parameters:**
- `status`: ACTIVE | INACTIVE

### Response (200 OK)
Same structure as Get All Categories.

---

# 2. SubCategory Management API

## 2.1 Create SubCategory
**POST** `/subcategories`

### Request Body
```json
{
  "name": "Smartphones",
  "imageUrl": "https://example.com/images/smartphones.jpg",
  "status": "ACTIVE",
  "categoryId": "550e8400-e29b-41d4-a716-446655440000"
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| name | string | ✅ | SubCategory name |
| imageUrl | string | ❌ | SubCategory image URL |
| status | string | ✅ | Status: ACTIVE or INACTIVE |
| categoryId | UUID | ✅ | Parent category ID |

### Response (201 Created)
```json
{
  "success": true,
  "message": "SubCategory created successfully",
  "data": {
    "id": "660e8400-e29b-41d4-a716-446655440001",
    "name": "Smartphones",
    "imageUrl": "https://example.com/images/smartphones.jpg",
    "status": "ACTIVE",
    "categoryName": "Electronics",
    "categoryId": "550e8400-e29b-41d4-a716-446655440000",
    "productCount": 0,
    "createdAt": "2025-12-08T16:15:10",
    "updatedAt": "2025-12-08T16:15:10",
    "createdBy": "admin@example.com",
    "updatedBy": "admin@example.com"
  }
}
```

---

## 2.2 Update SubCategory
**PUT** `/subcategories/{id}`

### Request Body
Same as Create SubCategory.

### Response (200 OK)
Same structure as Create SubCategory response.

---

## 2.3 Delete SubCategory
**DELETE** `/subcategories/{id}`

### Response (200 OK)
```json
{
  "success": true,
  "message": "SubCategory deleted successfully",
  "data": null
}
```

---

## 2.4 Get SubCategory by ID
**GET** `/subcategories/{id}`

### Response (200 OK)
```json
{
  "success": true,
  "message": "SubCategory retrieved successfully",
  "data": {
    "id": "660e8400-e29b-41d4-a716-446655440001",
    "name": "Smartphones",
    "imageUrl": "https://example.com/images/smartphones.jpg",
    "status": "ACTIVE",
    "categoryName": "Electronics",
    "categoryId": "550e8400-e29b-41d4-a716-446655440000",
    "productCount": 15,
    "createdAt": "2025-12-08T16:15:10",
    "updatedAt": "2025-12-08T16:15:10",
    "createdBy": "admin@example.com",
    "updatedBy": "admin@example.com"
  }
}
```

---

## 2.5 Get All SubCategories
**GET** `/subcategories`

### Response (200 OK)
Returns array of subcategories (same structure as Get SubCategory by ID).

---

## 2.6 Get SubCategories by Category
**GET** `/subcategories/category/{categoryId}`

### Response (200 OK)
Returns array of subcategories for the specified category.

---

## 2.7 Get SubCategories by Status
**GET** `/subcategories/status/{status}`

**Path Parameters:**
- `status`: ACTIVE | INACTIVE

### Response (200 OK)
Returns array of subcategories with the specified status.

---

# 3. Product Management API

## 3.1 Create Product
**POST** `/products`

### Request Body
```json
{
  "name": "iPhone 15 Pro Max",
  "description": "Latest flagship smartphone with advanced features",
  "imageUrl": "https://example.com/images/iphone15.jpg",
  "status": "ACTIVE",
  "categoryId": "550e8400-e29b-41d4-a716-446655440000",
  "subCategoryId": "660e8400-e29b-41d4-a716-446655440001",
  "attributes": [
    {
      "attributeName": "Brand",
      "attributeValue": "Apple"
    },
    {
      "attributeName": "Color",
      "attributeValue": "Natural Titanium"
    },
    {
      "attributeName": "Storage",
      "attributeValue": "256GB"
    }
  ],
  "variants": [
    {
      "name": "iPhone 15 Pro Max - 256GB",
      "price": 1199.99,
      "stock": 50,
      "discount": 100.00,
      "discountType": "FIXED_AMOUNT",
      "discountStartDate": "2025-12-01T00:00:00",
      "discountEndDate": "2025-12-31T23:59:59",
      "imageCover": "https://example.com/images/iphone15-256gb.jpg"
    },
    {
      "name": "iPhone 15 Pro Max - 512GB",
      "price": 1399.99,
      "stock": 30,
      "discount": 10,
      "discountType": "PERCENTAGE",
      "discountStartDate": "2025-12-01T00:00:00",
      "discountEndDate": "2025-12-31T23:59:59",
      "imageCover": "https://example.com/images/iphone15-512gb.jpg"
    }
  ]
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| name | string | ✅ | Product name |
| description | string | ❌ | Product description |
| imageUrl | string | ❌ | Main product image |
| status | string | ✅ | ACTIVE or INACTIVE |
| categoryId | UUID | ✅ | Parent category ID |
| subCategoryId | UUID | ❌ | Parent subcategory ID |
| attributes | array | ❌ | Product attributes |
| attributes[].attributeName | string | - | Attribute name |
| attributes[].attributeValue | string | - | Attribute value |
| variants | array | ❌ | Product variants |
| variants[].name | string | - | Variant name |
| variants[].price | decimal | - | Variant price |
| variants[].stock | integer | - | Available stock |
| variants[].discount | decimal | ❌ | Discount amount |
| variants[].discountType | string | ❌ | PERCENTAGE or FIXED_AMOUNT |
| variants[].discountStartDate | datetime | ❌ | Discount start date |
| variants[].discountEndDate | datetime | ❌ | Discount end date |
| variants[].imageCover | string | ❌ | Variant image |

### Response (201 Created)
```json
{
  "success": true,
  "message": "Product created successfully",
  "data": {
    "id": "770e8400-e29b-41d4-a716-446655440002",
    "name": "iPhone 15 Pro Max",
    "description": "Latest flagship smartphone with advanced features",
    "imageUrl": "https://example.com/images/iphone15.jpg",
    "productView": 0,
    "status": "ACTIVE",
    "categoryName": "Electronics",
    "categoryId": "550e8400-e29b-41d4-a716-446655440000",
    "subCategoryName": "Smartphones",
    "subCategoryId": "660e8400-e29b-41d4-a716-446655440001",
    "attributes": [
      {
        "id": "880e8400-e29b-41d4-a716-446655440003",
        "attributeName": "Brand",
        "attributeValue": "Apple",
        "createdAt": "2025-12-08T16:15:10",
        "updatedAt": "2025-12-08T16:15:10"
      },
      {
        "id": "880e8400-e29b-41d4-a716-446655440004",
        "attributeName": "Color",
        "attributeValue": "Natural Titanium",
        "createdAt": "2025-12-08T16:15:10",
        "updatedAt": "2025-12-08T16:15:10"
      },
      {
        "id": "880e8400-e29b-41d4-a716-446655440005",
        "attributeName": "Storage",
        "attributeValue": "256GB",
        "createdAt": "2025-12-08T16:15:10",
        "updatedAt": "2025-12-08T16:15:10"
      }
    ],
    "variants": [
      {
        "id": "990e8400-e29b-41d4-a716-446655440006",
        "name": "iPhone 15 Pro Max - 256GB",
        "price": 1199.99,
        "stock": 50,
        "discount": 100.00,
        "discountType": "FIXED_AMOUNT",
        "discountStartDate": "2025-12-01T00:00:00",
        "discountEndDate": "2025-12-31T23:59:59",
        "imageCover": "https://example.com/images/iphone15-256gb.jpg",
        "createdAt": "2025-12-08T16:15:10",
        "updatedAt": "2025-12-08T16:15:10"
      },
      {
        "id": "990e8400-e29b-41d4-a716-446655440007",
        "name": "iPhone 15 Pro Max - 512GB",
        "price": 1399.99,
        "stock": 30,
        "discount": 10,
        "discountType": "PERCENTAGE",
        "discountStartDate": "2025-12-01T00:00:00",
        "discountEndDate": "2025-12-31T23:59:59",
        "imageCover": "https://example.com/images/iphone15-512gb.jpg",
        "createdAt": "2025-12-08T16:15:10",
        "updatedAt": "2025-12-08T16:15:10"
      }
    ],
    "createdAt": "2025-12-08T16:15:10",
    "updatedAt": "2025-12-08T16:15:10",
    "createdBy": "admin@example.com",
    "updatedBy": "admin@example.com"
  }
}
```

---

## 3.2 Update Product
**PUT** `/products/{id}`

### Request Body
Same as Create Product.

**Note:** Updating a product will replace all attributes and variants with the new data provided.

### Response (200 OK)
Same structure as Create Product response.

---

## 3.3 Delete Product
**DELETE** `/products/{id}`

### Response (200 OK)
```json
{
  "success": true,
  "message": "Product deleted successfully",
  "data": null
}
```

---

## 3.4 Get Product by ID
**GET** `/products/{id}`

### Response (200 OK)
Same structure as Create Product response.

---

## 3.5 Get All Products
**GET** `/products`

### Response (200 OK)
Returns array of products with full details (attributes and variants included).

---

## 3.6 Get Products by Category
**GET** `/products/category/{categoryId}`

### Response (200 OK)
Returns array of products in the specified category.

---

## 3.7 Get Products by SubCategory
**GET** `/products/subcategory/{subCategoryId}`

### Response (200 OK)
Returns array of products in the specified subcategory.

---

## 3.8 Get Products by Status
**GET** `/products/status/{status}`

**Path Parameters:**
- `status`: ACTIVE | INACTIVE

### Response (200 OK)
Returns array of products with the specified status.

---

## 3.9 Increment Product View
**POST** `/products/{id}/view`

### Response (200 OK)
```json
{
  "success": true,
  "message": "Product view incremented successfully",
  "data": null
}
```

**Note:** This endpoint increments the `productView` counter for analytics. Call this when a user views a product detail page.

---

# 4. Product Promotion API

## 4.1 Create Promotion
**POST** `/promotions`

### Request Body
```json
{
  "name": "Holiday Sale - iPhone 15 Pro Max",
  "description": "Special holiday promotion for iPhone 15 Pro Max",
  "imageUrl": "https://example.com/images/promo-iphone15.jpg",
  "status": "ACTIVE",
  "productId": "770e8400-e29b-41d4-a716-446655440002"
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| name | string | ✅ | Promotion name |
| description | string | ❌ | Promotion description |
| imageUrl | string | ❌ | Promotion image/banner |
| status | string | ✅ | ACTIVE or INACTIVE |
| productId | UUID | ✅ | Product to promote |

### Response (201 Created)
```json
{
  "success": true,
  "message": "Promotion created successfully",
  "data": {
    "id": "aa0e8400-e29b-41d4-a716-446655440008",
    "name": "Holiday Sale - iPhone 15 Pro Max",
    "description": "Special holiday promotion for iPhone 15 Pro Max",
    "imageUrl": "https://example.com/images/promo-iphone15.jpg",
    "productView": 0,
    "status": "ACTIVE",
    "productId": "770e8400-e29b-41d4-a716-446655440002",
    "productName": "iPhone 15 Pro Max",
    "createdAt": "2025-12-08T16:15:10",
    "updatedAt": "2025-12-08T16:15:10",
    "createdBy": "admin@example.com",
    "updatedBy": "admin@example.com"
  }
}
```

---

## 4.2 Update Promotion
**PUT** `/promotions/{id}`

### Request Body
Same as Create Promotion.

### Response (200 OK)
Same structure as Create Promotion response.

---

## 4.3 Delete Promotion
**DELETE** `/promotions/{id}`

### Response (200 OK)
```json
{
  "success": true,
  "message": "Promotion deleted successfully",
  "data": null
}
```

---

## 4.4 Get Promotion by ID
**GET** `/promotions/{id}`

### Response (200 OK)
Same structure as Create Promotion response.

---

## 4.5 Get All Promotions
**GET** `/promotions`

### Response (200 OK)
Returns array of all active promotions.

---

## 4.6 Get Promotion by Product
**GET** `/promotions/product/{productId}`

### Response (200 OK)
```json
{
  "success": true,
  "message": "Promotion retrieved successfully",
  "data": {
    "id": "aa0e8400-e29b-41d4-a716-446655440008",
    "name": "Holiday Sale - iPhone 15 Pro Max",
    "description": "Special holiday promotion for iPhone 15 Pro Max",
    "imageUrl": "https://example.com/images/promo-iphone15.jpg",
    "productView": 125,
    "status": "ACTIVE",
    "productId": "770e8400-e29b-41d4-a716-446655440002",
    "productName": "iPhone 15 Pro Max",
    "createdAt": "2025-12-08T16:15:10",
    "updatedAt": "2025-12-08T16:15:10",
    "createdBy": "admin@example.com",
    "updatedBy": "admin@example.com"
  }
}
```

**Note:** Returns `null` in data if no promotion exists for the product.

---

## 4.7 Get Promotions by Status
**GET** `/promotions/status/{status}`

**Path Parameters:**
- `status`: ACTIVE | INACTIVE

### Response (200 OK)
Returns array of promotions with the specified status.

---

# Error Handling

## Common HTTP Status Codes

| Status Code | Description | When it occurs |
|-------------|-------------|----------------|
| 200 | OK | Successful GET, PUT, DELETE operations |
| 201 | Created | Successful POST operations |
| 400 | Bad Request | Invalid request body or validation errors |
| 404 | Not Found | Resource with given ID not found |
| 500 | Internal Server Error | Unexpected server error |

## Error Response Example

```json
{
  "success": false,
  "message": "Category not found with ID: 550e8400-e29b-41d4-a716-446655440000",
  "data": null,
  "timestamp": "2025-12-08T16:15:10"
}
```

## Validation Error Example

```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "name": "Category name is required",
    "status": "Status is required"
  },
  "timestamp": "2025-12-08T16:15:10"
}
```

---

# TypeScript Types (for Frontend)

```typescript
// Enums
export enum Status {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE'
}

export enum DiscountType {
  PERCENTAGE = 'PERCENTAGE',
  FIXED_AMOUNT = 'FIXED_AMOUNT'
}

// Category
export interface CategoryRequest {
  name: string;
  imageUrl?: string;
  status: Status;
}

export interface CategoryResponse {
  id: string;
  name: string;
  imageUrl?: string;
  status: Status;
  subCategoryCount: number;
  productCount: number;
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  updatedBy: string;
}

// SubCategory
export interface SubCategoryRequest {
  name: string;
  imageUrl?: string;
  status: Status;
  categoryId: string;
}

export interface SubCategoryResponse {
  id: string;
  name: string;
  imageUrl?: string;
  status: Status;
  categoryName: string;
  categoryId: string;
  productCount: number;
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  updatedBy: string;
}

// Product Attribute
export interface ProductAttributeRequest {
  attributeName: string;
  attributeValue: string;
}

export interface ProductAttributeResponse {
  id: string;
  attributeName: string;
  attributeValue: string;
  createdAt: string;
  updatedAt: string;
}

// Product Variant
export interface ProductVariantRequest {
  name: string;
  price: number;
  stock: number;
  discount?: number;
  discountType?: DiscountType;
  discountStartDate?: string;
  discountEndDate?: string;
  imageCover?: string;
}

export interface ProductVariantResponse {
  id: string;
  name: string;
  price: number;
  stock: number;
  discount?: number;
  discountType?: DiscountType;
  discountStartDate?: string;
  discountEndDate?: string;
  imageCover?: string;
  createdAt: string;
  updatedAt: string;
}

// Product
export interface ProductRequest {
  name: string;
  description?: string;
  imageUrl?: string;
  status: Status;
  categoryId: string;
  subCategoryId?: string;
  attributes?: ProductAttributeRequest[];
  variants?: ProductVariantRequest[];
}

export interface ProductResponse {
  id: string;
  name: string;
  description?: string;
  imageUrl?: string;
  productView: number;
  status: Status;
  categoryName: string;
  categoryId: string;
  subCategoryName?: string;
  subCategoryId?: string;
  attributes: ProductAttributeResponse[];
  variants: ProductVariantResponse[];
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  updatedBy: string;
}

// Product Promotion
export interface ProductPromotionRequest {
  name: string;
  description?: string;
  imageUrl?: string;
  status: Status;
  productId: string;
}

export interface ProductPromotionResponse {
  id: string;
  name: string;
  description?: string;
  imageUrl?: string;
  productView: number;
  status: Status;
  productId: string;
  productName: string;
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  updatedBy: string;
}

// API Response Wrapper
export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T | null;
  timestamp: string;
}
```

---

# Notes for Frontend Developers

1. **All IDs are UUIDs** - Use string type for all ID fields
2. **Dates are ISO 8601 format** - Use date parsing libraries or native Date constructor
3. **Soft Delete** - Deleted items won't appear in list/get endpoints (handled by backend)
4. **Cascade on Update** - Updating a product replaces all attributes and variants
5. **Product View Tracking** - Call the increment endpoint when users view product details
6. **Decimal Values** - Prices and discounts use decimal/float types (2 decimal places)
7. **Image URLs** - Must be valid URLs; consider implementing file upload separately
8. **Discount Calculation**:
   - `PERCENTAGE`: Final price = price - (price * discount / 100)
   - `FIXED_AMOUNT`: Final price = price - discount

---

# Testing with Swagger UI

Access Swagger UI at: `http://localhost:8080/swagger-ui.html`

All endpoints are documented and can be tested interactively through the Swagger interface.
