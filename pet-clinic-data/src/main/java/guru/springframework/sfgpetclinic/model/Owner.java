package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person{


    @Builder
    public Owner(Long id,
                 String firstName,
                 String lastName,
                 Set<Pet> pets,
                 String address,
                 String city,
                 String telephone) {
        super(id, firstName, lastName);
        this.pets = pets;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
    }

    public Owner(Set<Pet> pets, String address, String city, String telephone) {
        this.pets = pets;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
    }

    @Column(name = "pets")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "telephone")
    private String telephone;

}
