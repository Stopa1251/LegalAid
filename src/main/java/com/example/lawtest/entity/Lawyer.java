package com.example.lawtest.entity;

import jakarta.persistence.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
//import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "lawyers")
@Indexed
public class Lawyer extends User {
    private float experience;
    @FullTextField(analyzer = "ukrainian")
    private String portfolio;
//    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL)
    @ManyToMany
    @JoinTable(
        name = "lawyer_specializations",
        joinColumns = @JoinColumn(name = "lawyer_id"),
        inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
//    @FullTextField(analyzer = "ukrainian")
    private List<Specialization> specializations;
    private double rating;
    private String licenseNo;


    @OneToMany(mappedBy = "assignedLawyer", cascade = CascadeType.ALL)
    private List<Order> ordersAssigned;


    public void setLawyer(Lawyer lawyer) {
        this.setUser(lawyer);
        this.setExperience(lawyer.getExperience());
        this.setPortfolio(lawyer.getPortfolio());
        this.setSpecializations(lawyer.getSpecializations());
        this.setLicenseNo(lawyer.getLicenseNo());
        this.setOrdersAssigned(lawyer.getOrdersAssigned());

    }


    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Specialization> specializations) {
        this.specializations = specializations;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public List<Order> getOrdersAssigned() {
        return ordersAssigned;
    }

    public void setOrdersAssigned(List<Order> ordersAssigned) {
        this.ordersAssigned = ordersAssigned;
    }

    @Transient
    public String getSpecializationsString() {
        if (specializations == null || specializations.isEmpty()) {
            return "Немає спеціалізацій";
        }
        return specializations.stream()
                .map(Specialization::getSpecializationName)
                .collect(Collectors.joining(", "));
    }

}


