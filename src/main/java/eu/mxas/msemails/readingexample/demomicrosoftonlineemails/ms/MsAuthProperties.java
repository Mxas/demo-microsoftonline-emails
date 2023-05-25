package eu.mxas.msemails.readingexample.demomicrosoftonlineemails.ms;

import lombok.Data;
import lombok.ToString.Exclude;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "ms-demo.auth")
public class MsAuthProperties {

	private String tanentId;
	private String clientId;
	@Exclude
	private String clientSecret;
	private String scope;
	private String authorityUrl;
	private long tokenCacheTimoutMinutes = 45;
}
