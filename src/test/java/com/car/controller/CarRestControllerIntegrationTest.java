package com.car.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.car.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CarRestControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  Car car1;
  Car car2;
  Car car3;

  String carRequestJson1;
  String carRequestJson2;
  String carRequestJson3;
  ObjectWriter objectMapperWriter;

  final String BASE_URL = "/cars";

  @BeforeAll
  void setUp() throws Exception {
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
        .colour("Orange")
        .make("Lamborghini Huracan")
        .model("Huracan Evo")
        .year((short) 2021)
        .build();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    objectMapperWriter = objectMapper.writer().withDefaultPrettyPrinter();
    carRequestJson1 = objectMapperWriter.writeValueAsString(car1);
    carRequestJson2 = objectMapperWriter.writeValueAsString(car2);
    carRequestJson3 = objectMapperWriter.writeValueAsString(car3);

      mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
          .content(carRequestJson1))
          .andExpect(status().isCreated());

      mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
          .content(carRequestJson2))
          .andExpect(status().isCreated());

      mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
          .content(carRequestJson3))
          .andExpect(status().isCreated());
  }

  @Test
  void shouldCreateCar_With_Status_isCreated() throws Exception {
    Car car4 = Car.builder()
        .colour("White")
        .make("Range Rover")
        .model("Velar")
        .year((short) 2021)
        .build();

    String carRequestJson4 = objectMapperWriter.writeValueAsString(car4);

    mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(carRequestJson4))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.colour").value("White"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.make").value("Range Rover"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Velar"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.year").value("2021"));
  }

  @Test
  void shouldGetAllAddedCars_With_Status_isOK() throws Exception {
    mockMvc.perform(get(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].colour").value("White"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].make").value("Audi"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("A6"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value("2000"))

        .andExpect(MockMvcResultMatchers.jsonPath("$[1].colour").value("Black"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].make").value("Audi"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("A4"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].year").value("2000"))

        .andExpect(MockMvcResultMatchers.jsonPath("$[2].colour").value("Orange"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].make").value("Lamborghini Huracan"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].model").value("Huracan Evo"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].year").value("2021"));
  }

  @Test
  void shouldGetAllByMake_With_Status_isOK() throws Exception {
    mockMvc.perform(get(BASE_URL + "/by/make/Audi")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].colour").value("White"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].make").value("Audi"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("A6"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value("2000"))

        .andExpect(MockMvcResultMatchers.jsonPath("$[1].colour").value("Black"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].make").value("Audi"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("A4"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].year").value("2000"));
  }

  @Test
  void shouldGetAllByMake_WhereMakeContainSpace_With_Status_isOK() throws Exception {
    mockMvc.perform(get(BASE_URL + "/by/make/Lamborghini Huracan")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))

        .andExpect(MockMvcResultMatchers.jsonPath("$[0].colour").value("Orange"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].make").value("Lamborghini Huracan"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Huracan Evo"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value("2021"));
  }

  @Test
  void shouldGetAllByModel_With_Status_isOK() throws Exception {
    mockMvc.perform(get(BASE_URL + "/by/model/A6")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].colour").value("White"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].make").value("Audi"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("A6"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value("2000"));
  }

  @Test
  void shouldGetAllByModel_WhereModelContainSpace_With_Status_isOK() throws Exception {
    mockMvc.perform(get(BASE_URL + "/by/model/Huracan Evo")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))

        .andExpect(MockMvcResultMatchers.jsonPath("$[0].colour").value("Orange"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].make").value("Lamborghini Huracan"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Huracan Evo"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value("2021"));
  }

  @Test
  void shouldDeleteTheCar_With_Status_isOK() throws Exception {

    Car carForDelete = Car.builder()
        .colour("Blue")
        .make("Lamborghini")
        .model("Miura")
        .year((short) 2021)
        .build();

    String newCarBeforeDelete = objectMapperWriter.writeValueAsString(carForDelete);

    MvcResult carBeforeUpdate = mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(newCarBeforeDelete))
        .andExpect(status().isCreated())
        .andReturn();

    String carRequestForDelete = carBeforeUpdate.getResponse().getContentAsString();

    mockMvc.perform(delete(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(carRequestForDelete))
        .andExpect(status().isOk());

    // Making Sure that not getting already Deleted Car Which was Lamborghini Miura blue

    mockMvc.perform(get(BASE_URL + "/by/model/Miura")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
  }

  @Test
  void shouldUpdateTheCar_With_Status_isOK() throws Exception {
    Car carForUpdate = Car.builder()
        .colour("Red")
        .make("Ferrari")
        .model("Roma")
        .year((short) 2021)
        .build();

    String newCarBeforeUpdateRequestJson = objectMapperWriter.writeValueAsString(carForUpdate);

    MvcResult carBeforeUpdate = mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(newCarBeforeUpdateRequestJson))
        .andExpect(status().isCreated())
        .andReturn();

    var idForUpdateCar = JsonPath.read(carBeforeUpdate.getResponse().getContentAsString(), "$.id");

    carForUpdate.setModel("Portofino");
    carForUpdate.setColour("Yellow");

    String carForUpdateRequestJson = objectMapperWriter.writeValueAsString(carForUpdate);


    mockMvc.perform(put(BASE_URL + "/" + idForUpdateCar)
        .contentType(MediaType.APPLICATION_JSON)
        .content(carForUpdateRequestJson))
        .andExpect(status().isOk());

    // Making Sure that updated car can be get by Model which could be Portofino

    mockMvc.perform(get(BASE_URL + "/by/model/Portofino")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].colour").value("Yellow"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].make").value("Ferrari"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Portofino"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value("2021"));
  }

  @Test
  void shouldNotUpdateTheCar_With_Status_isNotFoundWhen_InvalidCarId() throws Exception {
    Car carForUpdateForInvalidId = Car.builder()
        .colour("Red")
        .make("Hundai")
        .model("Accent")
        .year((short) 2021)
        .build();

    String carForUpdateRequestJson = objectMapperWriter.writeValueAsString(carForUpdateForInvalidId);

    mockMvc.perform(put(BASE_URL + "/" + 95)
        .contentType(MediaType.APPLICATION_JSON)
        .content(carForUpdateRequestJson))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldGetErrorForUpdateTheCar_WhereCarNotFound_With_Status_isNotFound() throws Exception {
    car1.setId(1L);
    car1.setModel("Q7");
    String carToBeUpdatedRequest = objectMapperWriter.writeValueAsString(car1);

    mockMvc.perform(put(BASE_URL + "/" + 99L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(carToBeUpdatedRequest))
        .andExpect(status().isNotFound());
  }
}