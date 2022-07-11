package com.praveenkesarwani.model;

/**
 * Model object to represent a car.
 */
public class Car {
  private String registrationNumber;
  private int driversAge;

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public int getDriversAge() {
    return driversAge;
  }

  public Car(final String registrationNumber, final int driversAge) {
    this.registrationNumber = registrationNumber;
    this.driversAge = driversAge;
  }
}
