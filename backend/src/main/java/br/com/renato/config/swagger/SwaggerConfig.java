package br.com.renato.config.swagger;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	private final TypeResolver typeResolver;
	private final ApplicationEventPublisher publisher;

	@Value("${spring.application.name}")
	private String nomeProjeto;

	@Value("${app.email-contato:}")
	private String emailContato;

	@Value("${app.seguranca.enable-https}")
	private boolean isEnableHttps;

	@Value("${app.swagger.project-groupId}")
	private String projectGroupId;

	@Value("${app.swagger.project-version}")
	private String projectVersion;

	public SwaggerConfig(TypeResolver typeResolver, ApplicationEventPublisher publisher) {
		this.typeResolver = typeResolver;
		this.publisher = publisher;
	}

	@Bean
	public Docket apiV1() {
		return criarDocket("v1");
	}

	@Bean
	public Docket apiV2() {
		return criarDocket("v2");
	}

	private Docket criarDocket(String apiVersion) {
		try {
			Docket docket = new Docket(DocumentationType.SWAGGER_2)
					.protocols(newHashSet(isEnableHttps ? "https" : "http"))
					.forCodeGeneration(true)
					.groupName(apiVersion)
					.produces(newHashSet(APPLICATION_JSON_UTF8_VALUE))
					.apiInfo(apiInfo())
					.select()
					.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
					.paths(PathSelectors.regex("/" + apiVersion + "/.*"))
					.build()
					.pathMapping("/")
					.directModelSubstitute(LocalDate.class, Date.class)
					.directModelSubstitute(LocalDateTime.class, java.util.Date.class)
					.genericModelSubstitutes(ResponseEntity.class)
					.alternateTypeRules(
							newRule(typeResolver.resolve(DeferredResult.class,
									typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
									typeResolver.resolve(WildcardType.class)))
					.useDefaultResponseMessages(true)
					.globalResponseMessage(RequestMethod.GET, globalResponses())
					.globalResponseMessage(RequestMethod.POST, globalResponses())
					.globalResponseMessage(RequestMethod.DELETE, globalResponses())
					.globalResponseMessage(RequestMethod.PUT, globalResponses())
					.globalResponseMessage(RequestMethod.PATCH, globalResponses());

			publisher.publishEvent(new DocketCriadoEvent(this, docket));
			return docket;
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
				.deepLinking(true)
				.displayOperationId(false)
				.defaultModelsExpandDepth(1)
				.defaultModelExpandDepth(1)
				.defaultModelRendering(ModelRendering.EXAMPLE)
				.displayRequestDuration(false)
				.docExpansion(DocExpansion.NONE)
				.filter(false)
				.maxDisplayedTags(null)
				.operationsSorter(OperationsSorter.ALPHA)
				.showExtensions(false)
				.tagsSorter(TagsSorter.ALPHA)
				.validatorUrl(null)
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.version(projectVersion)
				.title(nomeProjeto)
				.description(projectGroupId)
				.contact(new Contact("Renato", "http://renato.com.br", emailContato))
				.build();
	}

	private List<ResponseMessage> globalResponses() {
		return newArrayList(
				new ResponseMessageBuilder().code(500).message("Erro inesperado no servidor, não foi possível processar a solicitação").build()
				, new ResponseMessageBuilder().code(400).message("Dados inválidos fornecidos pelo cliente").build()
				, new ResponseMessageBuilder().code(401).message("Usuário não autenticado").build()
				, new ResponseMessageBuilder().code(403).message("Usuário sem permissão para esta operação").build()
		);
	}
}
