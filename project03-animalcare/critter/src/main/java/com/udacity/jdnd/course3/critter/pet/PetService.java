package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Long save(Pet pet) {
        return petRepository.save(pet).getId();
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet getPet(Long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getPetByOwner(Customer customer) {
        List<Pet> pets =  petRepository.findAllByCustomer(customer);
        return  pets;
    }



}
