package com.praveenkesarwani.commands;

import com.praveenkesarwani.OutputPrinter;
import com.praveenkesarwani.exception.NoFreeSlotAvailableException;
import com.praveenkesarwani.model.Car;
import com.praveenkesarwani.model.Command;
import com.praveenkesarwani.service.ParkingLotService;

/**
 * Executor to handle command of parking a car into the parking lot. This
 * outputs the alloted slot
 * in which the car is parked.
 */
public class ParkCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "park";

  public ParkCommandExecutor(
      final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    return command.getParams().size() == 3;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final Command command) {
    final Car car = new Car(command.getParams().get(0), Integer.parseInt(command.getParams().get(2)));
    try {
      final Integer slot = parkingLotService.park(car);
      outputPrinter.printWithNewLine("Car with vehicle registration number " + command.getParams().get(0)
          + " has been parked at slot number " + slot);
    } catch (NoFreeSlotAvailableException exception) {
      outputPrinter.parkingLotFull();
    }
  }
}
