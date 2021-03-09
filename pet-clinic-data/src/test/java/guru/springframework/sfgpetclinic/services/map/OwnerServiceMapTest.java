package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;

    final Long ownerId = 1L;
    final String lastName = "Smith";

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());
        ownerServiceMap.save(Owner.builder()
                .id(ownerId)
                .lastName(lastName)
                .build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerServiceMap.findAll();

        assertThat(ownerSet, hasSize(1));
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(ownerId);

        assertThat(ownerServiceMap.findAll(), hasSize(0));
    }

    @Test
    void saveExistingId() {
        Long id = 2L;
        Owner owner = Owner.builder()
                .id(id)
                .build();

        Owner savedOwner = ownerServiceMap.save(owner);

        assertThat(savedOwner, equalTo(owner));
        assertThat(savedOwner.getId(), equalTo(id));
    }

    @Test
    void saveNoId() {
        Owner savedOwner = ownerServiceMap.save(Owner.builder().build());

        assertThat(savedOwner.getId(), notNullValue());
        assertThat(savedOwner, notNullValue());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(ownerId));

        assertThat(ownerServiceMap.findAll(), hasSize(0));
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(ownerId);

        assertThat(owner.getId(), equalTo(ownerId));
    }

    @Test
    void findByLastName() {
        Owner smith = ownerServiceMap.findByLastName(lastName);

        assertThat(smith, notNullValue());
        assertThat(smith.getId(), equalTo(ownerId));
    }

    @Test
    void findByLastNameNotFound() {
        Owner smith = ownerServiceMap.findByLastName("foo");

        assertThat(smith, nullValue());
    }
}