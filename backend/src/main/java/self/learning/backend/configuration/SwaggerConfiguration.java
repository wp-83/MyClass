package self.learning.backend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MyClass API")
                        .version("1.0")
                        .description("""
                                API for My Class application.

                                This API provides endpoints for managing
                                students, courses, lecturers, and enrollments. 
                                
                                **The starting of API url always "/my-class"**
                                """));
    }
}
