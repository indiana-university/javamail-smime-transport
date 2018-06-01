# Java Mail S/MIME Transport

When this library is added to a project you may configure a Java Mail session to use the `smtp+smime` or `smtps+smime` protocol to have the transport sign outgoing mail with a mail certificate stored in a Java keystore (JKS) file.

## Configuration
| Property                                | Description |
|-----------------------------------------|-------------|
| mail.transport.protocol                 | Special protocol to use: `smtp+smime` or `smtps+smime` |
| mail.keystore.file                      | Keystore file containing certificate |
| mail.keystore.password                  | Password for keystore file |
| mail.keystore.`email address`.password  | Password for `email address` alias in keystore

If your keystore only contains one email alias and its password is the same as the keystore's, you can omit the mail.keystore.`email`.address configuration.

## Examples

### Java Mail
```java
// example class
class Mailer {
    
    Session mailSession;
    
    public Mailer(String smtpHost, String smtpPort, String username, String password) {
        properties.put("mail.transport.protocol", "smtp+smime");
        properties.put("mail.smtp+smime.host",  smtpHost);
        properties.put("mail.smtp+smime.port", smtpPort);
        properties.put("mail.smtp+smime.auth", "true");
        properties.put("mail.smtp+smime.starttls.enable", "true");
        
        Authenticator auth = new SMTPAuthenticator(username, password);
        mailSession = Session.getInstance(properties, auth);        
    }
    
    public void mail(String from, String to, String subject, String text) {
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress("workflow.noreply@iu.edu"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("jawbenne@iu.edu"));
        message.setSubject("testing");
        message.setText("This is a test");
        Transport.send(message);        
    }
}
```

### 

