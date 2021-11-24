package com.car.controller;

import com.car.model.Car;
import com.car.payload.request.CarRequest;
import com.car.service.CarService;
import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarRestController {

  private CarService carService;

  private ModelMapper modelMapper = new ModelMapper();

  @Autowired
  public CarRestController(CarService carService) {
    this.carService = carService;
  }

  @PostMapping
  public ResponseEntity<Car> addCar(@Valid @RequestBody CarRequest carRequest) {
    Car car = modelMapper.map(carRequest, Car.class);
    Car newCar = carService.addCar(car);
    return new ResponseEntity<>(newCar, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Car>> getAllCars() {
    return new ResponseEntity<>(carService.getCars(), HttpStatus.OK);
  }

  @GetMapping("/by/make/{make}")
  public ResponseEntity<List<Car>> getAllCarsByMake(@Valid @PathVariable String make) {
    return new ResponseEntity<>(carService.getCarsByMake(make), HttpStatus.OK);
  }

  @GetMapping("/by/model/{model}")
  public ResponseEntity<List<Car>> getAllCarsByModel(@Valid @PathVariable String model) {
    return new ResponseEntity<>(carService.getCarsByModel(model), HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity deleteCar(@Valid @RequestBody CarRequest carRequest) {
      Car carToBeDeleted = modelMapper.map(carRequest, Car.class);
      carService.deleteCar(carToBeDeleted);
      return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Car> updateCar(@Valid @PathVariable Long id, @Valid @RequestBody CarRequest carRequest) {
    Car carToBeUpdated = modelMapper.map(carRequest, Car.class);
    return new ResponseEntity<>(carService.updateCar(id, carToBeUpdated), HttpStatus.OK);
  }
}
