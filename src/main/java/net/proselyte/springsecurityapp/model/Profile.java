package net.proselyte.springsecurityapp.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {
    @Column(name = "name")
    private String name;
    @Column(name = "orgName")
    private String orgName;
    @Column(name = "debt")
    private String debt;
    @Column(name = "telefon")
    private String telefon;
    @Column(name = "uidOrg")
    private String uidOrg;
    @Column(name = "userMail")
    private String userMail;
    @Column(name = "uidUser")
    @Id
    private String uidUser;
    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getUidOrg() {
        return uidOrg;
    }

    public void setUidOrg(String uidOrg) {
        this.uidOrg = uidOrg;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}
