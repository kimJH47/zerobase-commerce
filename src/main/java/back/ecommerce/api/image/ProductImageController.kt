package back.ecommerce.api.image

import back.ecommerce.api.dto.Response
import back.ecommerce.auth.domain.AuthUser
import back.ecommerce.image.application.ProductImageFacadeService
import back.ecommerce.image.dto.request.ImageUploadRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/images")
class ProductImageController(
    private val productImageFacadeService: ProductImageFacadeService
) {

    @PostMapping("/products/upload")
    fun upload(@AuthenticationPrincipal authUser: AuthUser, @ModelAttribute request: ImageUploadRequest): ResponseEntity<Response> {
        return Response.createSuccessResponse(
            "이미지가 성공적으로 업로드 되었습니다.", productImageFacadeService.uploads(
                authUser.username, request
            )
        )
    }
}