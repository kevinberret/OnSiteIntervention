package fi.haagahelia.serverprogramming.OnSiteIntervention;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// this class is only used to add a middleware (= interceptor)
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor());
	}
	
	@Bean
	public RequestInterceptor requestInterceptor() {
	    return new RequestInterceptor();
	}
}