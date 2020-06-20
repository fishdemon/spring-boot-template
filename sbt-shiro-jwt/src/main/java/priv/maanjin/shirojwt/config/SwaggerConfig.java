/*
 * Delta CONFIDENTIAL
 *
 * (C) Copyright Delta Electronics, Inc. 2015 All Rights Reserved
 *
 * NOTICE:  All information contained herein is, and remains the
 * property of Delta Electronics. The intellectual and technical
 * concepts contained herein are proprietary to Delta Electronics
 * and are protected by trade secret, patent law or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Delta Electronics.
 */
package priv.maanjin.shirojwt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @Title: SwaggerConfig.java
 * 
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Value("${swagger.show}")
	private boolean swaggerShow;

	private static final Contact DEFAULT_CONTACT = new Contact(
		      "Ma Anjin", "", "Anjin.Ma@example.com"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	
	private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
		      "UserManage APIs", "modules: ", //$NON-NLS-1$ //$NON-NLS-2$
		      "1.0", "1.0", DEFAULT_CONTACT, " ", " "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	
	/**
	 * @return Docket
	 */
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(DEFAULT_API_INFO)				
			.select()
			.apis(RequestHandlerSelectors.any())
//				.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
			.paths(Predicates.not(PathSelectors.regex("/error"))) //$NON-NLS-1$ Exclude Spring error controllers
//				.paths(PathSelectors.any())
			.build()
			.enable(swaggerShow);
	}

}
