package com.praveenkesarwani.commands;

import com.praveenkesarwani.OutputPrinter;
import com.praveenkesarwani.model.Command;
import com.praveenkesarwani.model.Slot;
import com.praveenkesarwani.service.ParkingLotService;
import java.util.List;
import java.util.Optional;

/**
 * Executor to handle command of fetching slot number of a car with a given
 * registration number.
 */
public class RegNumberToSlotNumberCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "slot_number_for_car_with_number";

  public RegNumberToSlotNumberCommandExecutor(
      final ParkingLotService parkingLotService,
      final OutputPrinter outputPrinter) {
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
    final List<Slot> occupiedSlots = parkingLotService.getOccupiedSlots();
    final String regNumberToFind = command.getParams().get(0);
    final Optional<Slot> foundSlot = occupiedSlots.stream()
        .filter(slot -> slot.getParkedCar().getRegistrationNumber().equals(regNumberToFind))
        .findFirst();
    if (foundSlot.isPresent()) {
      outputPrinter.printWithNewLine(foundSlot.get().getSlotNumber().toString());
    } else {
      outputPrinter.notFound();
    }
  }
}
