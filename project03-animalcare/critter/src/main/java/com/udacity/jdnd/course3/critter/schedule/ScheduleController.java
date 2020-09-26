package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Long id = scheduleService.save(convertScheduleDTOToSchedule(scheduleDTO));
        return convertScheduleToScheduleDTO(scheduleService.getSchedule(id));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule>  schedules = scheduleService.getAllSchedules();
        return getScheduleDTOS(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        List<Schedule>  schedules = scheduleService.getAllSchedulesForPet(pet);
        return getScheduleDTOS(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        List<Schedule>  schedules = scheduleService.getAllSchedulesForEmployee(employee);
        return getScheduleDTOS(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        List<Schedule>  schedules = scheduleService.getAllSchedulesForCustomer(customer);
        return getScheduleDTOS(schedules);
    }

    private ArrayList<ScheduleDTO> getScheduleDTOS(List<Schedule> schedules) {
        ArrayList<ScheduleDTO> scheduleDTOS = new ArrayList<ScheduleDTO>();
        for (Schedule schedule: schedules
        ) {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (schedule.getPets() != null)
        {
            List<Long> petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
            scheduleDTO.setPetIds(petIds);
        }

        if (schedule.getEmployees() != null)
        {
            List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
            scheduleDTO.setEmployeeIds(employeeIds);
        }


        return scheduleDTO;


    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);


//        Customer customer = new Customer();
        List<Pet> pets = new ArrayList<Pet>();
        List<Employee> employees = new ArrayList<Employee>();
//        BeanUtils.copyProperties(customerDTO, customer);
        if (scheduleDTO.getPetIds() != null) {
            for (Long id: scheduleDTO.getPetIds()
            ) {
                pets.add(petService.getPet(id));
            }
            schedule.setPets(pets);
        }
        if (scheduleDTO.getEmployeeIds() != null) {
            for (Long id: scheduleDTO.getEmployeeIds()
            ) {
                employees.add(employeeService.getEmployee(id));
            }
            schedule.setEmployees(employees);
        }
        return schedule;
    }
}
