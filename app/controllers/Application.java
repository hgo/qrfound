package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;

import models.Contact;
import models.CountryCodes;
import models.QRCode;
import net.glxn.qrgen.image.ImageType;
import notifiers.Mails;
import play.Logger;
import play.Play;
import play.data.validation.Email;
import play.data.validation.MinSize;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.libs.Codec;
import play.mvc.Controller;
import play.mvc.Http.Header;
import play.mvc.Util;
import utils.FQRUtils;

public class Application extends Controller {

    public static void registration() {
        CountryCodes countryCodes = null;
        try {
            Header header = request.headers.get("accept-language");
            Logger.info(header + "");
            String code = header.value().split(",")[0].toUpperCase();
            Logger.info(code);
            CountryCodes cCodes = CountryCodes.find("code = ?", code).first();
            if (cCodes != null) {
                countryCodes = cCodes;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        render(countryCodes);
    }

    public static void register(@Required @Email String email, @Required @Phone String phone, @Required @MinSize(6) String password) {
        if (validation.hasErrors()) {
            validation.keep();
            params.flash();
        }
        Contact contact = new Contact();
        contact.email = email;
        contact.phone = phone;
        contact.password = Codec.hexMD5(password);
        contact = contact.save();
        String url = Play.configuration.getProperty("found.url");
        File file = net.glxn.qrgen.QRCode.from(url + contact.uuid).withSize(250, 250).to(ImageType.PNG).file();
        QRCode qrCode = new QRCode();
        qrCode.contact = contact;
        qrCode.createdAt = new Date();
        qrCode.qr = file;
        qrCode = qrCode.save();
        FQRUtils.setConnectedUser(contact.id);
        dashboard();
    }

    public static void downloadQR(boolean inline) throws FileNotFoundException {
        Contact user = FQRUtils.connectedUser();
        if (user == null) {
            registration();
        }
        QRCode code = QRCode.findByUUID(user.uuid);
        notFoundIfNull(code);
        FileInputStream fs = new FileInputStream(code.qr);
        renderBinary(fs, "My_QRCode.png", false);
    }

    public static void dashboard() {
        Contact user = FQRUtils.connectedUser();
        if (user == null) {
            registration();
        }
        render(user);
    }

    public static void found(String uuid) {
        notFoundIfNull(uuid);
        QRCode code = QRCode.findByUUID(uuid);
        notFoundIfNull(code);
        render(code,uuid);
    }

    public static void notifyContact(String phone, String comment, String uuid) {
        Contact contact = Contact.findByUUID(uuid);
        Mails.notifyContact(phone, comment, contact.email);
        renderHtml("<h2>Thanks! You will be called..");
    }

    @Util
    private static HashMap checkInside(String key, String value) {
        boolean ok = false;
        String message = "";
        if ("email".equals(key)) {
            ok = validation.required(value).ok && validation.email(value).ok;
            if (!ok) {
                message = "Please enter a valid email address";
            }
            if (ok) {
                ok = 0L == Contact.count("email = ?", value);
                if (!ok) {
                    message = "This email address already registered";
                }
            }
        } else if ("phone".equals(key)) {
            ok = validation.phone(value).ok;
            if (!ok) {
                message = "Please enter a valid phone number";
            }
        } else if ("password".equals(key)) {
            ok = validation.required(value).ok && value.length() > 6;
            if (!ok) {
                message = "Password length should be greater than 6";
            }
        }
        HashMap map = new HashMap();
        map.put("ok", ok);
        map.put("message", message);
        return map;
    }

    public static void check(String key, String value) {
        notFoundIfNull(key);
        notFoundIfNull(value);
        HashMap map = checkInside(key, value);
        renderJSON(map);

    }

    public static void create(Contact contact) {
        contact.save();
        ok();
    }

}