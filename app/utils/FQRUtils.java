package utils;

import models.Contact;

import org.apache.commons.lang.math.NumberUtils;

import play.mvc.Scope.Session;

public class FQRUtils {

    public static void setConnectedUser(Long id) {
        Session.current().put("connected", id + "");
    }

    public static Contact connectedUser() {
        return Contact.findById(NumberUtils.toLong(Session.current().get("connected")));
    }
}
