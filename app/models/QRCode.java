package models;

import java.io.File;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class QRCode extends Model {

    @OneToOne
    public Contact contact;

    @Lob
    public File qr;

    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;
    
    public static QRCode findByUUID(String uuid){
        return QRCode.find("select qrCode from QRCode qrCode join qrCode.contact as c where c.uuid=?", uuid).first();
    }

}
