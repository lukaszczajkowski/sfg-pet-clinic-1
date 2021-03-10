package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "Smith";
    public static final long ID = 1L;
    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerSDJpaService service;

    Owner returnOwner;
    Set<Owner> ownerSet = new HashSet<>();

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder()
                .id(ID)
                .lastName(LAST_NAME)
                .build();
        ownerSet.add(returnOwner);
    }

    @Test
    void findAll() {
        when(ownerRepository.findAll())
                .thenReturn(ownerSet);

        Set<Owner> returnedOwners =
                service.findAll();

        assertThat(returnedOwners, hasSize(1));
        assertThat(returnedOwners, equalTo(ownerSet));
    }

    @Test
    void findById() {
        when(ownerRepository.findById(ID))
                .thenReturn(Optional.of(returnOwner));

        Owner ownerFromService = service.findById(ID);

        assertThat(ownerFromService, equalTo(returnOwner));
    }

    @Test
    void save() {
        when(ownerRepository.save(returnOwner))
                .thenReturn(returnOwner);

        Owner savedOwner = service.save(returnOwner);

        assertThat(savedOwner, equalTo(returnOwner));
    }

    @Test
    void delete() {
        service.delete(returnOwner);

        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(ID);

        verify(ownerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void findByLastName() {
        when(ownerRepository.findByLastName(any()))
                .thenReturn(returnOwner);

        Owner smith = service.findByLastName(LAST_NAME);

        assertThat(smith.getLastName(), equalTo(LAST_NAME));
    }
}