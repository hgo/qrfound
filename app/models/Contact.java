package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import play.db.jpa.Model;
import play.libs.Codec;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "uuid" }))
public class Contact extends Model {

    @Column(nullable=false)
    public String uuid;

    @Column(nullable=false)
    public String phone;

    @Column(nullable=false)
    public String email;
    
    @Column(nullable=false)
    public String password;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date createdAt;

    public Contact() {
        this.uuid = Codec.UUID();
    }

    public static Contact findByUUID(String uuid) {
        return Contact.find("uuid =? ", uuid).first();
    }
    
    @PrePersist
    void prePersist(){
        this.createdAt = new Date();
    }
}
