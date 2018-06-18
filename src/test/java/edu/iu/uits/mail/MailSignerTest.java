package edu.iu.uits.mail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.security.KeyStore;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MailSignerTest {

    private static final String TEST_ADDRESS_LOCAL_PART = "foo@bar.baz";
    private static final String TEST_ADDRESS = TEST_ADDRESS_LOCAL_PART +  "@bar.baz";
    private static final String KEYSTORE_PASSWORD = "k3yst0r3p@ssw0rd";
    private static final String ALIAS_PASSWORD = "@l1@sp@ssw0rd";

    @Mock
    private KeyStore keyStore;

    private Properties properties;
    private MailSigner mailSigner;

    @BeforeEach
    public void setup() {
        properties = new Properties();
        properties.setProperty("mail.keystore.password", KEYSTORE_PASSWORD);
    }

    @Test
    public void testGetEmailPasswordFullEmail() {
        properties.setProperty(String.format(MailSigner.CERT_PASSWORD_PROPERTY_TEMPLATE, TEST_ADDRESS), ALIAS_PASSWORD);
        mailSigner = new MailSigner(properties, keyStore);
        String emailPassword = mailSigner.getEmailPassword(TEST_ADDRESS);
        assertEquals(ALIAS_PASSWORD, emailPassword);
    }


    @Test
    public void testGetEmailPasswordLocalAddressPart() {
        properties.setProperty(String.format(MailSigner.CERT_PASSWORD_PROPERTY_TEMPLATE, TEST_ADDRESS_LOCAL_PART), ALIAS_PASSWORD);
        mailSigner = new MailSigner(properties, keyStore);
        String emailPassword = mailSigner.getEmailPassword(TEST_ADDRESS);
        assertEquals(ALIAS_PASSWORD, emailPassword);
    }

    @Test
    public void testGetEmailPasswordKeystore() {
        mailSigner = new MailSigner(properties, keyStore);
        String emailPassword = mailSigner.getEmailPassword(TEST_ADDRESS);
        assertEquals(KEYSTORE_PASSWORD, emailPassword);
    }
}
