package eu.mxas.msemails.readingexample.demomicrosoftonlineemails.ms.service;

import eu.mxas.msemails.readingexample.demomicrosoftonlineemails.ms.MsMailboxProperties;
import jakarta.annotation.PostConstruct;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailClientService {

	private final AuthService authService;
	private final MsMailboxProperties mailboxProperties;
	private Properties props;

	@PostConstruct
	void init() {
		this.props = prepareImapProperties();
	}

	private Properties prepareImapProperties() {
		Properties props = new Properties();

		props.put("mail.store.protocol", "imap");
		props.put("mail.imap.host", "outlook.office365.com");
		props.put("mail.imap.port", "993");
		props.put("mail.imap.ssl.enable", "true");
		props.put("mail.imap.starttls.enable", "true");
		props.put("mail.imap.auth", "true");
		props.put("mail.imap.auth.mechanisms", "XOAUTH2");
		props.put("mail.debug", "true");
		props.put("mail.debug.auth", "true");

		props.put("mail.imap.user", mailboxProperties.getUsername());
		return props;
	}


	public Store prepareEmailStore() {
		try {
			Session session = Session.getInstance(props);
			Store store = session.getStore(mailboxProperties.getProtocol());
			store.connect(mailboxProperties.getHost(), mailboxProperties.getUsername(), authService.getAccessToken());
			return store;
		} catch (Exception e) {
			throw new RuntimeException("Failed to prepare MS Mailbox Store.", e);
		}
	}
}
