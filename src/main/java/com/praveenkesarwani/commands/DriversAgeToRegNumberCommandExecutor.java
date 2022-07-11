package com.praveenkesarwani.commands;

import com.praveenkesarwani.OutputPrinter;
import com.praveenkesarwani.model.Command;
import com.praveenkesarwani.model.Slot;
import com.praveenkesarwani.service.ParkingLotService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Executor to handle command of fetching all registration number of cars of a
 * particular driver's age.
 */
public class DriversAgeToRegNumberCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "vehicle_registration_number_for_driver_of_age";

  public DriversAgeToRegNumberCommandExecutor(
      final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    return command.getParams().size() == 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final Command command) {
    int driversAge = Integer.parseInt(command.getParams().get(0));
    final List<Slot> slotsForDriversAge = parkingLotService.getSlotsForDriversAge(driversAge);
    if (slotsForDriversAge.isEmpty()) {
      outputPrinter.notFound();
    } else {
      final String result = slotsForDriversAge.stream()
          .map(slot -> slot.getParkedCar().getRegistrationNumber())
          .collect(Collectors.joining(", "));
      outputPrinter.printWithNewLine(result);
    }
  }
}
