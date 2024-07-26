package com.zerosome.datasource.remote.service

//class ProductService @Inject constructor(
//    private val apiClient: HttpClient
//): BaseService {
//    override val apiRoute: String
//        get() = "api/app/v1/product"
//
//    fun getProductByCategory(
//        token: String? = null,
//        d2CategoryCode: String,
//        orderType: String,
//        brandList: List<String>, // Next, will change to brandList,
//        zeroCategoryList: List<String>, // Next, will Change to ZeroCtg
//        cursor: Int? = null,
//        limit: Int? = null
//    ) = apiClient.safeGet<PagedResponse<CategoryProductResponse>>(token, url = "$apiRoute/category/$d2CategoryCode") {
//        cursor?.let {
//            parameter("cursor", it)
//        }
//        limit?.let {
//            parameter("limit", it)
//        }
//        setBody(
//            CategoryProductRequest(
//                d2CategoryCode,
//                orderType,
//                brandList,
//                zeroCategoryList
//            )
//        )
//    }
//
//    fun getProductDetail(
//        token: String? = null,
//        productId: String
//    ) = apiClient.safeGet<ProductDetailResponse>(token, "$apiRoute/$productId")
//
//}