package notifiers;

import com.sendgrid.*;
import org.apache.commons.lang.text.StrSubstitutor;

import java.io.IOException;
import java.util.HashMap;


public class Mails {

    public static void notifyContact(String phone, String comment, String toEmail) throws IOException {
        Email from = new Email("info@qrfound.com");
        String subject = "[Urgent] Someone find your item. Please call him/her !";
        Email to = new Email(toEmail);

        Content content = new Content("text/plain", generate(toEmail, phone, comment));
        Mail mail = new Mail(from, subject, to, content);


        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sg.api(request);
            System.out.println(response.statusCode);
            System.out.println(response.body);
            System.out.println(response.headers);
        } catch (IOException ex) {
            throw ex;
        }
    }

    private static String generate(String to, String phone, String comment) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("to", to.split("@")[0]);
        map.put("phone", phone);
        map.put("comment", comment);

        String str = "Hello ${to},\n" +
                "\n" +
                "Someone found your item, Call him from phone number; ${phone}.\n" +
                "He/She says; ${comment}";
        StrSubstitutor strSubstitutor = new StrSubstitutor(map);
        return strSubstitutor.replace(str);
    }
}











