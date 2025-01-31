package Spring.Auth.ServiceImpl;

import java.security.Key;
import java.time.zone.ZoneRulesException;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import Spring.Auth.Service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 24))
				.signWith(getSiginKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	
	public String generateRefreshToken(Map<String, Object> extraClaims,  UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 24))
				.signWith(getSiginKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	private Key getSiginKey() {
		byte[] key = Decoders.BASE64.decode("qwertyuiop789asdfghjkl456zxcvbnm123");
		return Keys.hmacShaKeyFor(key);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSiginKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
	public boolean isTokenExpired(String token) {
		
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
		
	
}





