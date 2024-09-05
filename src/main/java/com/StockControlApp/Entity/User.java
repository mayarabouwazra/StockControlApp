package com.StockControlApp.Entity;

import jakarta.persistence.*;
import lombok.Data;
@Table(name = "USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Entity
@Data
public class User {
    @Id
    private String email;
    private String password;
    private String nom;
    private String prenom;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public User() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
