package notifiers;

import play.mvc.Mailer;

public class Mails extends Mailer {

    public static void notify(String phone, String comment, String to) {
        setSubject("[Urgent] Someone find your item. Please call him");
        setFrom("info@foundqr.net");
        addRecipient(to);
        send(phone, comment, to);
    }
}
