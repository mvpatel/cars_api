package com.car;

import com.car.controller.CarController;
import com.car.service.CarService;
import com.car.service.impl.CarServiceImpl;

public class CarApplication {

  private static CarController CarController;
  private static CarService customerService = new CarServiceImpl();

  public static void main(String[] args) {
  }

}
