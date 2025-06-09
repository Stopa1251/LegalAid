package com.example.lawtest.entity;

import jakarta.persistence.*;
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
@Inheritance(strategy = InheritanceType.JOINED)
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
//    @OneToMany
//    @JoinColumn(name = "id")
//    private List<Order> orders;


//    @Column(name = "review_id")
//    @OneToMany
//    @JoinColumn(name = "id")
//    private List<Review> reviews;


    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Review> reviewsSent;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Review> reviewsReceived;

    private boolean active = true;


    public User setUser(User user) {
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.phoneNumber = user.phoneNumber;
        this.verified = user.verified;
        this.address = user.address;
        this.createdAt = user.createdAt;
        this.profileImagePath = user.profileImagePath;
        this.active = user.active;

        return this;
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



    public enum Role implements GrantedAuthority {
        ROLE_CLIENT, ROLE_LAWYER, ROLE_ADMIN, ROLE_SUPPORT;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}