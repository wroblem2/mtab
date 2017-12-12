package com.mwroblewski.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PROFILES")
@JsonIgnoreProperties(value = {"user"})
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // content of profile
    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;
    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String surname;
    @Column(columnDefinition = "VARCHAR(50)")
    private String email;
    @Column(columnDefinition = "VARCHAR(20)")
    private String phone;
    @Column(columnDefinition = "VARCHAR(50)")
    private String address;  // should be embadded
    @Temporal(value = TemporalType.DATE)
    private Date born;
    @Enumerated(value = EnumType.STRING)
    private Contract contract;

    @Column(columnDefinition = "VARCHAR(2000)")
    private String experiences;
    @Column(columnDefinition = "VARCHAR(2000)")
    private String accomplishments;
    @Column(columnDefinition = "VARCHAR(2000)")
    private String interests;

    @Digits(integer = 6, fraction = 2)
    private BigDecimal minSalary;
    @Digits(integer = 6, fraction = 2)
    private BigDecimal maxSalary;

    // relation with other tables
    @OneToOne
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    private Attachments attachments;

    // constructors
    public Profile() {
    }

    // getter/setter methods
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getBorn() {
        return born;
    }
    public void setBorn(Date born) {
        this.born = born;
    }
    public Contract getContract() {
        return contract;
    }
    public void setContract(Contract contract) {
        this.contract = contract;
    }
    public String getExperiences() {
        return experiences;
    }
    public void setExperiences(String experiences) {
        this.experiences = experiences;
    }
    public String getAccomplishments() {
        return accomplishments;
    }
    public void setAccomplishments(String accomplishments) {
        this.accomplishments = accomplishments;
    }
    public String getInterests() {
        return interests;
    }
    public void setInterests(String interests) {
        this.interests = interests;
    }
    public BigDecimal getMinSalary() {
        return minSalary;
    }
    public void setMinSalary(BigDecimal minSalary) {
        this.minSalary = minSalary;
    }
    public BigDecimal getMaxSalary() {
        return maxSalary;
    }
    public void setMaxSalary(BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Attachments getAttachments() {
        return attachments;
    }
    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }
}
