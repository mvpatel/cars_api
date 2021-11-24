package com.car.service.impl;

import com.car.model.Car;
import com.car.repository.CarRepository;
import com.car.service.CarService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;

  @Autowired
  public CarServiceImpl(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  @Override
  public Car addCar(Car car) {
    return carRepository.save(car);
  }

  @Override
  public List<Car> getCars() {
    return (List<Car>) carRepository.findAll();
  }

  @Override
  public List<Car> getCarsByMake(String make) {
    return carRepository.findAllByMake(make);
  }

  @Override
  public List<Car> getCarsByModel(String model) {
    return carRepository.findAllByModel(model);
  }

  @Override
  public void deleteCar(Car car) {
    carRepository.delete(car);
  }

  @Override
  public Car updateCar(Long id, Car newCar) {

    // finding the car by given id, if car won't be found then ResourceNotFoundException will be thrown
    Car currentCar = carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid id is given "
                                                                                                + "to update the Car"));
    currentCar.setMake(newCar.getMake());
    currentCar.setModel(newCar.getModel());
    currentCar.setColour(newCar.getColour());
    currentCar.setYear(newCar.getYear());

    return carRepository.save(currentCar);
  }
}
