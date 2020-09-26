package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long id) {

        return  customerRepository.getOne(id);
    }

    public Long save (Customer customer) {

        return customerRepository.save(customer).getId();
    }

    public Customer getCustomerByPet(Pet pet) {
        return customerRepository.findAllByPetsContaining(pet).get(0);
    }
}
