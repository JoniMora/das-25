package ubp.das.ristorinobackend.controller.secure;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/secure")
public class SecurePingController {
    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}
