package com.example.lawtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.lawtest.entity.Role;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;


    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = true)
    private boolean verified;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = true)
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

//    @Column(name = "order_id")
    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Order> orders;
//    @Column(name = "review_id")
    @OneToMany
    @JoinColumn(name = "review_id")
    private List<Review> reviews;

    public User setUser(User user) {
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.phoneNumber = user.phoneNumber;
        this.verified = user.verified;
        this.role = user.role;
        this.address = user.address;
        this.createdAt = user.createdAt;
        this.orders = user.orders;
        this.reviews = user.reviews;
        this.experience = user.experience;
        this.portfolio = user.portfolio;
        this.specializations = user.specializations;
        this.rating = user.rating;
        this.licenseNo = user.licenseNo;
        this.profileImagePath = user.profileImagePath;
        this.active = user.active;

        return this;
    }

    // STOPA TEST
    private float experience;
    @Lob
    private String portfolio;
    @OneToMany
    @JoinColumn(name = "specialization_id")
    private List<Specialization> specializations;
    private double rating;
    private String licenseNo;

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
    // STOPA TEST


    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public boolean isActive() {
        return active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @Column(name = "profile_image")
    private String profileImagePath;

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    //    @Lob
//    @Column(columnDefinition = "LONGBLOB")
//    private byte[] avatar;
//
//    public void setAvatarFilename(String avatarFilename) {
//        this.avatarFilename = avatarFilename;
//    }
//
//    public byte[] getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(byte[] avatar) {
//        this.avatar = avatar;
//    }

    private boolean active = true;


    @Transient
    public String getSpecializationsString() {
        if (specializations == null || specializations.isEmpty()) {
            return "Немає спеціалізацій";
        }
        return specializations.stream()
                .map(Specialization::getSpecializationName)
                .collect(Collectors.joining(", "));
    }

    public enum Role implements GrantedAuthority {
        ROLE_CLIENT, ROLE_LAWYER, ROLE_ADMIN, ROLE_SUPPORT;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}