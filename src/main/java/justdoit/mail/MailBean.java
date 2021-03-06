package justdoit.mail;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Stateless
public class MailBean {

    public MailBean() {

    }

    private MailConfig mailConfig = readConfigFromFile("/mailConfig.json");

    /**
     * Sends an E-Mail
     *
     * @param mailContent
     * @throws MessagingException
     */
    public void sendMail(MailContent mailContent) throws MessagingException {
        MailAuthenticator auth = new MailAuthenticator(mailConfig.username, mailConfig.password);

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", mailConfig.host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", mailConfig.port);
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties, auth);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailConfig.from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailContent.recipientAdress, false));
        msg.setSubject(mailContent.subject);
        msg.setContent(mailContent.content, "text/html; charset=utf-8");
        msg.setSentDate(new Date());
        Transport.send(msg);
    }

    /**
     * Read properties which are important to send a mail from a JSON File.
     *
     * @return MailConfig
     */
    private MailConfig readConfigFromFile(String path) {

        String from = "";
        String host = "";
        String port = "";
        String username = "";
        String password = "";

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new InputStreamReader(getClass().getResourceAsStream(path)));
            JSONObject jsonObject = (JSONObject) obj;

            from = (String) jsonObject.get("from");
            host = (String) jsonObject.get("host");
            port = (String) jsonObject.get("port");
            username = (String) jsonObject.get("username");
            password = (String) jsonObject.get("password");

        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }

        return new MailConfig(from, host, port, username, password);
    }

    class MailAuthenticator extends Authenticator {

        private final String user;
        private final String password;

        public MailAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.user, this.password);
        }
    }

    class MailConfig {

        private final String from;
        private final String host;
        private final String port;
        private final String username;
        private final String password;

        public MailConfig(String from, String host, String port, String username, String password) {
            this.from = from;
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
        }
    }
}
