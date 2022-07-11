package com.praveenkesarwani.commands;

import com.praveenkesarwani.OutputPrinter;
import com.praveenkesarwani.exception.InvalidSlotException;
import com.praveenkesarwani.model.Car;
import com.praveenkesarwani.model.Command;
import com.praveenkesarwani.model.Slot;
import com.praveenkesarwani.service.ParkingLotService;
import java.util.List;
import java.util.Optional;

public class SlotNumberToCarDetailsCommandExecutor extends CommandExecutor {
    public static String COMMAND_NAME = "car_details_by_slot_number";

    public SlotNumberToCarDetailsCommandExecutor(
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
        final int slotNum = Integer.parseInt(command.getParams().get(0));
        final List<Slot> occupiedSlots = parkingLotService.getOccupiedSlots();
        final Optional<Slot> foundSlot = occupiedSlots.stream()
                .filter(slot -> slot.getSlotNumber().equals(slotNum))
                .findFirst();
        if (foundSlot.isPresent()) {
            String registrationNumber = foundSlot.get().getParkedCar().getRegistrationNumber();
            int driversAge = foundSlot.get().getParkedCar().getDriversAge();
            outputPrinter
                    .printWithNewLine("At slot number " + slotNum + ": Vehicle registration number is "
                            + registrationNumber + " and the driver of the car is of age " + driversAge);
        } else {
            outputPrinter.notFound();
        }
    }

    public Car getCarDetails(int slotNumber) {
        final List<Slot> occupiedSlots = parkingLotService.getOccupiedSlots();
        final Optional<Slot> foundSlot = occupiedSlots.stream()
                .filter(slot -> slot.getSlotNumber().equals(slotNumber))
                .findFirst();
        if (foundSlot.isPresent()) {
            String registrationNumber = foundSlot.get().getParkedCar().getRegistrationNumber();
            int driversAge = foundSlot.get().getParkedCar().getDriversAge();
            Car carDetails = new Car(registrationNumber, driversAge);
            return carDetails;
        } else {
            throw new InvalidSlotException();
        }
    }
}
