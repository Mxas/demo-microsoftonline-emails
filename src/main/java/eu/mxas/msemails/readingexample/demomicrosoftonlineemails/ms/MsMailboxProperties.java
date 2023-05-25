package eu.mxas.msemails.readingexample.demomicrosoftonlineemails.ms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "ms-demo.mailbox")
public class MsMailboxProperties {

	private String username;
	private String protocol = "imap";
	private String host = "outlook.office365.com";
}
