$content = Get-Content 'c:\Users\makkara.nob\Desktop\NEW PROJECT\tiffanny-backend\src\main\java\com\emenu\features\product\service\impl\ProductPromotionServiceImpl.java' -Raw

# Add specification import
$content = $content -replace 'import com.emenu.features.product.service.ProductPromotionService;', 'import com.emenu.features.product.service.ProductPromotionService;
import com.emenu.features.product.specification.ProductPromotionSpecification;'

# Replace findAllActive
$content = $content -replace 'return promotionRepository\.findAllActive\(\)\.stream\(\)', '// Use specification to find all active promotions
        return promotionRepository.findAll(
                ProductPromotionSpecification.filterPromotions(null, null, null, null, null)
        ).stream()'

# Replace findByProductId
$content = $content -replace 'return promotionRepository\.findByProductId\(productId\)\.stream\(\)', '// Use specification to filter by product
        return promotionRepository.findAll(
                ProductPromotionSpecification.filterPromotions(null, null, productId, null, null)
        ).stream()'

# Replace findByStatus
$content = $content -replace 'return promotionRepository\.findByStatus\(status\)\.stream\(\)', '// Use specification to filter by status
        return promotionRepository.findAll(
                ProductPromotionSpecification.filterPromotions(null, status, null, null, null)
        ).stream()'

Set-Content 'c:\Users\makkara.nob\Desktop\NEW PROJECT\tiffanny-backend\src\main\java\com\emenu\features\product\service\impl\ProductPromotionServiceImpl.java' -Value $content
