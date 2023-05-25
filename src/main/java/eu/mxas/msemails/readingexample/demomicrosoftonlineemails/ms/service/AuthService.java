package eu.mxas.msemails.readingexample.demomicrosoftonlineemails.ms.service;

import com.google.common.base.Suppliers;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import eu.mxas.msemails.readingexample.demomicrosoftonlineemails.ms.MsAuthProperties;
import jakarta.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Lazy
public class AuthService {

	private final MsAuthProperties properties;
	private ConfidentialClientApplication app;

	private Supplier<String> accessTokenCache;


	@PostConstruct
	void init() throws MalformedURLException {
		this.app = ConfidentialClientApplication.builder(
						properties.getClientId(),
						ClientCredentialFactory.createFromSecret(properties.getClientSecret()))
				.authority(properties.getAuthorityUrl() + properties.getTanentId() + "/")
				.build();

		this.accessTokenCache = Suppliers.memoizeWithExpiration(
				this::loadAccessToken,
				properties.getTokenCacheTimoutMinutes(), TimeUnit.MINUTES
		);
	}

	private String loadAccessToken() {
		ClientCredentialParameters clientCredentialParam = ClientCredentialParameters.builder(
						Collections.singleton(properties.getScope()))
				.build();
		try {
			return app.acquireToken(clientCredentialParam).get().accessToken();
		} catch (Exception e) {
			log.error("Failed square MS Office Mailbox access_token", e);
			return null;
		}
	}


	public String getAccessToken() {
		return this.accessTokenCache.get();
	}
}
