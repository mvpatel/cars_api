package com.car.repository;

import com.car.model.Car;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends PagingAndSortingRepository<Car, Long> {
    List<Car> findAllByMake(String make);
    List<Car> findAllByModel(String model);
}
