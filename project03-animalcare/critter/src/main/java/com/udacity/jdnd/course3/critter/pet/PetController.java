package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Long id = petService.save(convertPetDTOToPet(petDTO));
        Pet pet = petService.getPet(id);
        PetDTO petDTO1 = convertPetToPetDTO(pet);
        return petDTO1;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        PetDTO petDTO = convertPetToPetDTO(pet);
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        ArrayList<PetDTO> petDTOS = new ArrayList<PetDTO>();
        for (Pet pet: petService.getAllPets()
        ) {
            petDTOS.add(convertPetToPetDTO(pet));
        }
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        ArrayList<PetDTO> petDTOS = new ArrayList<PetDTO>();
        for (Pet pet: petService.getPetByOwner(customerService.getCustomer(ownerId))
        ) {
            petDTOS.add(convertPetToPetDTO(pet));
        }
        return petDTOS;
    }

    private PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if(pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        if (petDTO.getOwnerId() >0 ) {
            pet.setCustomer(customerService.getCustomer(petDTO.getOwnerId()));
        }
        return pet;
    }

}
