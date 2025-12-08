$content = Get-Content 'c:\Users\makkara.nob\Desktop\NEW PROJECT\tiffanny-backend\src\main\java\com\emenu\features\product\service\impl\ProductServiceImpl.java' -Raw

# Add specification import after other imports
$content = $content -replace 'import com.emenu.features.product.service.ProductService;', 'import com.emenu.features.product.service.ProductService;
import com.emenu.features.product.specification.ProductSpecification;'

# Replace findAllActive
$content = $content -replace 'return productRepository\.findAllActive\(\)\.stream\(\)', '// Use specification to find all active products
        return productRepository.findAll(
                ProductSpecification.filterProducts(null, null, null, null, null, null, null, null, null, null, null)
        ).stream()'

# Replace findByCategoryId
$content = $content -replace 'return productRepository\.findByCategoryId\(categoryId\)\.stream\(\)', '// Use specification to filter by category
        return productRepository.findAll(
                ProductSpecification.filterProducts(null, null, categoryId, null, null, null, null, null, null, null, null)
        ).stream()'

# Replace findBySubCategoryId
$content = $content -replace 'return productRepository\.findBySubCategoryId\(subCategoryId\)\.stream\(\)', '// Use specification to filter by subcategory
        return productRepository.findAll(
                ProductSpecification.filterProducts(null, null, null, subCategoryId, null, null, null, null, null, null, null)
        ).stream()'

# Replace findByStatus
$content = $content -replace 'return productRepository\.findByStatus\(status\)\.stream\(\)', '// Use specification to filter by status
        return productRepository.findAll(
                ProductSpecification.filterProducts(null, status, null, null, null, null, null, null, null, null, null)
        ).stream()'

Set-Content 'c:\Users\makkara.nob\Desktop\NEW PROJECT\tiffanny-backend\src\main\java\com\emenu\features\product\service\impl\ProductServiceImpl.java' -Value $content
