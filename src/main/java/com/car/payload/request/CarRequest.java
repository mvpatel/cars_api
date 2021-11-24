package com.car.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarRequest {
  long id;
  String make;
  String model;
  String colour;
  short year;
}
