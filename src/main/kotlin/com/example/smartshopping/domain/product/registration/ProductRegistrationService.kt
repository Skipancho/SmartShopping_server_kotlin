package com.example.smartshopping.domain.product.registration

import com.example.smartshopping.common.SmartShoppingException
import com.example.smartshopping.domain.auth.UserContextHolder
import com.example.smartshopping.domain.product.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductRegistrationService @Autowired constructor(
    private val productRepository: ProductRepository,
    private val productImageRepository: ProductImageRepository,
    private val userContextHolder: UserContextHolder
){
    fun register(request: ProductRegistrationRequest) =
        userContextHolder.userCode?.let { userCode ->
            val images by lazy { findAndValidateImages(request.imageIds) }
            request.validateRequest()
            request.toProduct(images, userCode)
                .run(::save)
        } ?: throw SmartShoppingException("상품 등록에 필요한 사용자 정보가 존재하지 않습니다.")

    private fun findAndValidateImages(imageIds : List<Long?>) =
        productImageRepository.findByIdIn(imageIds.filterNotNull())
            .also { images ->
                images.forEach { image ->
                    if (image.productId != null)
                        throw SmartShoppingException("이미 등록된 상품입니다.")
                }
            }

    private fun save(product: Product) = productRepository.save(product)
}

private fun ProductRegistrationRequest.validateRequest() = when{
    name.length !in 1..40 ||
            imageIds.size !in 1..4 ||
            imageIds.filterNotNull().isEmpty() ||
            description.length !in 1..500 ||
            price <= 0 -> throw SmartShoppingException("올바르지 않은 상품 정보입니다.")
    else -> {
        //do nothing
     }
}

private fun ProductRegistrationRequest.toProduct(
    images : MutableList<ProductImage>,
    userCode : Long
) = Product(name, description, price, categoryId, ProductStatus.SELLABLE,images, userCode)