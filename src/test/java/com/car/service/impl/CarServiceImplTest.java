package com.car.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.car.model.Car;
import com.car.repository.CarRepository;
import com.car.service.CarService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

@RunWith(MockitoJUnitRunner.class)
class CarServiceImplTest {

  @Mock
  CarRepository mockCarRepository;
  @InjectMocks
  CarService underTest = new CarServiceImpl(mockCarRepository);

  List<Car> carList = new ArrayList<>();

  Car car1;
  Car car2;
  Car car3;

  @BeforeEach
  void setUp() {

    MockitoAnnotations.initMocks(this);

    car1 = Car.builder()
        .colour("White")
        .make("Audi")
        .model("A6")
        .year((short) 2000)
        .build();

    car2 = Car.builder()
        .colour("Black")
        .make("Audi")
        .model("A4")
        .year((short) 2000)
        .build();

    car3 = Car.builder()
        .colour("Black")
        .make("Jaguar")
        .model("XF")
        .year((short) 2000)
        .build();
  }

  @AfterEach
  void tearDown() {
    carList.clear();
  }
  @Test
  void save_car_success() {
    when(mockCarRepository.save(any(Car.class))).thenReturn(car1);
    assertEquals(car1, underTest.addCar(car1));
    verify(mockCarRepository, times(1)).save(car1);
  }

  @Test
  void fetch_all_car_success() {

    carList.add(car1);
    carList.add(car2);
    carList.add(car3);

    when(mockCarRepository.findAll()).thenReturn(carList);
    assertEquals(carList, underTest.getCars());
    verify(mockCarRepository, times(1)).findAll();
  }

  @Test
  void find_all_cars_by_Make_success() {

    carList.add(car1);
    carList.add(car2);

    when(mockCarRepository.findAllByMake("Audi")).thenReturn(carList);
    assertEquals(carList, underTest.getCarsByMake("Audi"));
    verify(mockCarRepository, times(1)).findAllByMake("Audi");
  }

  @Test
  void find_all_cars_by_Model_success() {
    carList.add(car3);
    when(mockCarRepository.findAllByModel("XF")).thenReturn(carList);
    assertEquals(carList, underTest.getCarsByModel("XF"));
    verify(mockCarRepository, times(1)).findAllByModel("XF");
  }

  @Test
  void update_car_success() {

    car1.setModel("Q7");

    when(mockCarRepository.findById(any(Long.class))).thenReturn(Optional.of(car1));
    when(mockCarRepository.save(any(Car.class))).thenReturn(car1);
    Car updatedCar = underTest.updateCar(1L, car1);

    verify(mockCarRepository, times(1)).findById(1L);
    verify(mockCarRepository, times(1)).save(car1);
  }

  @Test
  void update_car_should_be_failed_when_given_car_is_not_available() {

    doThrow(new ResourceNotFoundException("Car is not available for given id")).when(mockCarRepository).findById(1L);
    boolean thrown = false;
    String exceptionMessage = null;

    try {
      underTest.updateCar(1L, car1);
    } catch (ResourceNotFoundException rnf) {
      thrown = true;
      exceptionMessage = rnf.getMessage();
    }

    assertTrue(thrown);
    assertEquals("Car is not available for given id", exceptionMessage);

    verify(mockCarRepository, times(1)).findById(1L);
    verify(mockCarRepository, times(0)).save(car1);
  }

  @Test
  void deleteCar_success() throws Exception {
    underTest.deleteCar(car1);
    verify(mockCarRepository, times(1)).delete(car1);
  }
}
