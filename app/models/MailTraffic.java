package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class MailTraffic extends Model {

    @Column(nullable = false)
    public String to;
    
    @Temporal(TemporalType.TIMESTAMP)
    public Date sentAt;
    
    @Column(nullable=false,updatable=false)
    public String type;
    

//    public static MailTraffic newNotifyMail(){
////        new MailTraffic("notify")
//    }

}
