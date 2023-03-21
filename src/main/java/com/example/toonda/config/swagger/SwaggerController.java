package com.example.toonda.config.swagger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@ApiIgnore
public class SwaggerController {

    @GetMapping("/api/doc")
    public void redirectSwagger(HttpServletResponse response) throws IOException {
//        response.sendRedirect("http://localhost:8080/swagger-ui/index.html");
        response.sendRedirect("https://jm.rgngr.shop/swagger-ui/index.html");
    }

}
