package back.ecommerce.api.cart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import back.ecommerce.api.auth.resolver.annotation.UserEmail;
import back.ecommerce.api.dto.Response;
import back.ecommerce.cart.application.CartService;
import back.ecommerce.cart.dto.request.AddCartRequest;
import back.ecommerce.cart.dto.request.DeleteCartRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@PostMapping("/add-product")
	public ResponseEntity<Response> addCart(@RequestBody @Valid AddCartRequest request) {
		return Response.createSuccessResponse("장바구니에 상품이 추가 되었습니다.",
			cartService.addProduct(request.getEmail(), request.getProductId(), request.getQuantity()));
	}

	@GetMapping
	public ResponseEntity<Response> findByEmail(@UserEmail String tokenEmail) {
		return Response.createSuccessResponse("장바구니가 성공적으로 조회 되었습니다.", cartService.findCartByUserEmail(tokenEmail));
	}

	@DeleteMapping
	public ResponseEntity<Response> deleteById(DeleteCartRequest request) {
		return Response.createSuccessResponse("상품이 성공적으로 삭제되었습니다.",
			cartService.deleteById(request.getCartId(), request.getEmail()));

	}
}