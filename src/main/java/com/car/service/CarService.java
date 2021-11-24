package com.car.service;

import com.car.model.Car;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * This Service class will be used to do CRUD operation on Car
 */
@Service
public interface CarService {

  /**
   * Add the Car
   *
   * @param car: Instance of {@link Car}
   * @return: Instance of {@link Car}
   */
  Car addCar(Car car);

  /**
   * Get All cars which are already added
   *
   * @return: List of {@link Car}
   */
  List<Car> getCars();

  /**
   * get Cars by given Make of the car
   *
   * @param make: Make of the car in the form of {@link String}, e.g. "Audi"
   * @return: List of {@link Car}
   */
  List<Car> getCarsByMake(String make);

  /**
   * get Cars by given model of the car
   *
   * @param model: Model of the car in the form of {@link String}, e.g. "A6"
   * @return: List of {@link Car}
   */
  List<Car> getCarsByModel(String model);

  /**
   * Given Car will be deleted.
   *
   * @param car: Instance of {@link Car}
   */
  void deleteCar(Car car);

  /**
   * Given id will be used to find out the Car and will be updated by given newCar
   *
   * @param id:     id of the Car
   * @param newCar: new Car which could be updated {@link Car}
   * @return: Updated car {@link Car}
   */
  Car updateCar(Long id, Car newCar);

}
