package com.mwroblewski.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "OFFERS")
@JsonIgnoreProperties(value = {"user"})
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "VARCHAR(500)", nullable = false)
    private String name;
    @Column(columnDefinition = "VARCHAR(5000)", nullable = false)
    private String description;
    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date published;
    @Enumerated(value = EnumType.STRING)
    private Contract contract;
    @Digits(integer = 6, fraction = 2)
    private BigDecimal upperMoneyLimit;
    @Digits(integer = 6, fraction = 2)
    private BigDecimal lowerMoneyLimit;

    // relations with other tables
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "offer")
    private List<Entry> entryList;
    @ManyToOne
    private City city;
    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private List<Technology> technologies;
//    @OneToMany(mappedBy = "offer")
//    private List<Entry> entries = new ArrayList<>();

    // constructors
    public Offer() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public BigDecimal getUpperMoneyLimit() {
        return upperMoneyLimit;
    }

    public void setUpperMoneyLimit(BigDecimal upperMoneyLimit) {
        this.upperMoneyLimit = upperMoneyLimit;
    }

    public BigDecimal getLowerMoneyLimit() {
        return lowerMoneyLimit;
    }

    public void setLowerMoneyLimit(BigDecimal lowerMoneyLimit) {
        this.lowerMoneyLimit = lowerMoneyLimit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Entry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }
}
