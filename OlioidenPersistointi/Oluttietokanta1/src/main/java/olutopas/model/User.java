package olutopas.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/* @author mhaanran */
@Entity
public class User {
    @Id
    private Integer id;
    
    private String kayttajatunnus;
   
    @OneToMany
    private Rating rating;

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public User() {     
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKayttajatunnus() {
        return kayttajatunnus;
    }

    public void setKayttajatunnus(String kayttajatunnus) {
        this.kayttajatunnus = kayttajatunnus;
    }
    public String toString() {
        return getKayttajatunnus();
    }
}
