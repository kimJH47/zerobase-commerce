package back.ecommerce.auth.token;

import static io.jsonwebtoken.SignatureAlgorithm.*;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import back.ecommerce.exception.TokenHasExpiredException;
import back.ecommerce.exception.TokenHasInvalidException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenProvider {

	private static final String TYPE = "Bearer";
	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = HS256;

	@Value("${jwt.secretKey}")
	private String securityKey;
	private final JwtParser parser = Jwts.parser();

	public Token create(String email, int expireTime) {

		HashMap<String, Object> payload = new HashMap<>();
		payload.put("email", email);

		String token = Jwts.builder()
			.setHeaderParam("typ", "JWT")
			.setHeaderParam("alg", SIGNATURE_ALGORITHM)
			.setClaims(payload)
			.setExpiration(createExpireTime(expireTime))
			.signWith(HS256, securityKey)
			.compact();

		return new Token(token, expireTime, TYPE);
	}

	private Date createExpireTime(int expireTime) {
		Date expireDate = new Date();
		expireDate.setTime(expireDate.getTime() + expireTime);
		return expireDate;
	}

	public String extractClaim(String token, String claimName) {
		try {
			String claim = parser.setSigningKey(securityKey)
				.parseClaimsJws(token)
				.getBody()
				.get(claimName, String.class);

			if (claim == null) {
				return "";
			}
			return claim;
		} catch (IllegalArgumentException e) {
			throw new TokenHasInvalidException("토큰이 비어있습니다.");
		} catch (SignatureException | UnsupportedJwtException | MalformedJwtException e) {
			throw new TokenHasInvalidException("토큰이 유효하지 않습니다.");
		} catch (ExpiredJwtException e) {
			throw new TokenHasExpiredException("토큰이 만료 되었습니다.");
		}
	}
}
