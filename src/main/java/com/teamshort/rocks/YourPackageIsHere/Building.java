package com.teamshort.rocks.YourPackageIsHere;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Set;

@Entity
public class Building implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String username;
    String name;
    String streetaddress;
    String city;
    String state;
    String zip;
    String email;
    String password;

    @OneToMany(mappedBy = "building")
    Set<Tenant> tenants;


    public Building(){}

    public Building(String username, String name, String streetaddress, String city, String state, String zip, String email, String password){
        this.username = username;
        this.name = name;
        this.streetaddress = streetaddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetaddress() {
        return streetaddress;
    }

    public void setStreetAdress(String streetaddress) {
        this.streetaddress = streetaddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }
}
