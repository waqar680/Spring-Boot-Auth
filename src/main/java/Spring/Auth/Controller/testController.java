package Spring.Auth.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testController {
	
	@GetMapping("/api")
	public String hello() {
		return "api is healthy";
	}

}
