package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Long save(Schedule schedule) {
        return scheduleRepository.save(schedule).getId();
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> a =  scheduleRepository.findAll();
        return a;
    }

    public Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.getOne(scheduleId);
    }

    public List<Schedule> getAllSchedulesForPet(Pet pet) {
        return scheduleRepository.findAllByPetsContaining(pet);
    }

    public List<Schedule> getAllSchedulesForEmployee(Employee employee) {
        return scheduleRepository.findAllByEmployeesContaining(employee);
    }

    public List<Schedule> getAllSchedulesForCustomer(Customer customer) {
        ArrayList<Schedule> scheduleArrayList = new ArrayList<Schedule>();
        scheduleRepository.findAll().forEach(schedule-> {
            schedule.getPets().forEach(pet-> {
                if (pet.getCustomer().getId().equals(customer.getId()))
                    scheduleArrayList.add(schedule);
            });
        });
        return  scheduleArrayList;
    }




}
