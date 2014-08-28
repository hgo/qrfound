package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.GenericModel;

@Entity
public class CountryCodes extends GenericModel {


    @Id
    @Column(nullable=false)
    public String code;
    @Column(nullable=false)
    public String dial_code;
    @Column(nullable=false)
    public String name;
}
