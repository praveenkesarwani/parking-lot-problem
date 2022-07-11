package com.praveenkesarwani.commands;

import com.praveenkesarwani.OutputPrinter;
import com.praveenkesarwani.exception.InvalidSlotException;
import com.praveenkesarwani.model.Car;
import com.praveenkesarwani.model.Command;
import com.praveenkesarwani.service.ParkingLotService;
import com.praveenkesarwani.validator.IntegerValidator;
import java.util.List;

/**
 * Executor to handle command of freeing of slot from a car.
 */
public class LeaveCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "leave";

  public LeaveCommandExecutor(final ParkingLotService parkingLotService,
      final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    final List<String> params = command.getParams();
    if (params.size() != 1) {
      return false;
    }
    return IntegerValidator.isInteger(params.get(0));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final Command command) {
    final int slotNum = Integer.parseInt(command.getParams().get(0));
    SlotNumberToCarDetailsCommandExecutor slotNumberToCarDetailsCommandExecutor = new SlotNumberToCarDetailsCommandExecutor(
        parkingLotService, outputPrinter);
    try {
      Car carDetails = slotNumberToCarDetailsCommandExecutor.getCarDetails(slotNum);
      outputPrinter.printWithNewLine("Slot number " + slotNum + " vacated, the car with vehicle registration number "
          + carDetails.getRegistrationNumber() + " left the space, the driver of the car was of age "
          + carDetails.getDriversAge());
      parkingLotService.makeSlotFree(slotNum);
    } catch (Exception e) {
      throw new InvalidSlotException();
    }
  }
}
