package eu.mxas.msemails.readingexample.demomicrosoftonlineemails;

import eu.mxas.msemails.readingexample.demomicrosoftonlineemails.ms.MsAuthProperties;
import eu.mxas.msemails.readingexample.demomicrosoftonlineemails.ms.service.AuthService;
import eu.mxas.msemails.readingexample.demomicrosoftonlineemails.ms.service.EmailClientService;
import javax.mail.Folder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoMicrosoftonlineEmailsApplicationTests {

	@Autowired
	MsAuthProperties properties;
	@Autowired
	AuthService authService;
	@Autowired
	EmailClientService emailClientService;

	@Test
	void props() {
		Assertions.assertNotNull(properties.getScope());
	}

	@Test
	void accessToken() {
		Assertions.assertNotNull(authService.getAccessToken());
		System.out.println();
		System.out.println(authService.getAccessToken());
		System.out.println();
	}

	@Test
	void countMessages() throws Exception {
		var f = emailClientService.prepareEmailStore().getFolder("INBOX");
		f.open(Folder.READ_WRITE);

		Assertions.assertTrue(f.getMessageCount() > 0);
	}

}
