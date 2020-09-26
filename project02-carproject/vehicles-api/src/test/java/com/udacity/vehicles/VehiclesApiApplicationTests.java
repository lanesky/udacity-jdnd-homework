package com.udacity.vehicles;

import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VehiclesApiApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void contextLoads() {
    }

    @Test
    public void testCreateCar() {
        Car car = createAnUsedCar();
        assertThat(car.getId(), not(0));
    }

    @Test
    public void testGetCar() {

        Car car = createAnUsedCar();

        ResponseEntity<Car> response;
        String url = buildUrl("/cars/" + car.getId());

        response = restTemplate.getForEntity(url, Car.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody().getId(), equalTo(car.getId()));
    }

    @Test
    public void testGetAllCars() {

        Car foo = createAnUsedCar();
        Car bar = createAnUsedCar();

        ResponseEntity<Car> response = send("/cars");
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

    }

    @Test
    public void testUpdateCar() {
        Car car = createAnUsedCar();

        // update an existing car
        car.setCondition(Condition.NEW);
        car.getDetails().setBody("modified");
        HttpEntity<Car> entity = new HttpEntity<Car>(car, headers);
        ResponseEntity<Car> response;
        String url = buildUrl("/cars/" + car.getId());
        response = restTemplate.exchange(url,
                HttpMethod.PUT,
                entity,
                Car.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        // retrieve the car and verify the body has been modified
        url = buildUrl("/cars/" + car.getId());
        response = restTemplate.getForEntity(url, Car.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody().getId(), equalTo(car.getId()));
        assertThat(response.getBody().getCondition(), equalTo(car.getCondition()));

    }

    @Test
    public void testDeleteCar() {
        Car car = createAnUsedCar();

        // delete an existing car
        HttpEntity<Car> entity = new HttpEntity<Car>(car, headers);
        ResponseEntity<Car> response;
        String url = buildUrl("/cars/" + car.getId());
        response = restTemplate.exchange(url,
                HttpMethod.DELETE,
                entity,
                Car.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));

        // if to retrieve a deleted car, 404 returns
        url = buildUrl("/cars/" + car.getId());
        response = restTemplate.getForEntity(url, Car.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    private void createTwoCarsIfNecessary() {

    }

    private Car createAnUsedCar() {
        Car car = prototypeCar();

        HttpEntity<Car> entity = new HttpEntity<Car>(car, headers);

        ResponseEntity<Car> response;
        String url = buildUrl("/cars");
        response = restTemplate.exchange(url,
                HttpMethod.POST,
                entity,
                Car.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));

        return response.getBody();
    }

    private Car prototypeCar() {
        Car car = new Car();
        car.setCondition(Condition.USED);
        car.setCreatedAt(LocalDateTime.now());
        car.setDetails(new Details());
        car.getDetails().setBody("body 99");
        car.getDetails().setEngine("engine 99");
        car.getDetails().setExternalColor("color 99");
        car.getDetails().setFuelType("fuel type 99");
        car.getDetails().setMileage(1000);
        car.getDetails().setModel("99");
        car.getDetails().setModelYear(1999);
        car.getDetails().setNumberOfDoors(9);
        car.getDetails().setProductionYear(1999);
        car.getDetails().setManufacturer(new Manufacturer(100, "test"));
        car.setPrice("99");
        car.setModifiedAt(LocalDateTime.now());
        car.setLocation(new Location(51.509865, -0.118092));
        return car;
    }

    private String buildUrl(String path) {
       return "http://localhost:" + port + path;
    }

    private ResponseEntity<Car> send(String url) {
        return this.restTemplate.getForEntity("http://localhost:" + port + url, Car.class);
    }
}
