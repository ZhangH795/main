package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
//@@author Pengyuz
/**
 * export the person details in txt
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "new file created";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "export the person details in txt file";
    private String filepath;
    public ExportCommand (String f) {
        this.filepath = f.trim();
    }
    @Override
    public CommandResult execute() throws CommandException {
        try {
            File file = new File(filepath);
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write("New backup addressbook storage is created at " + filepath + " "
                    + LocalDateTime.now());
            output.newLine();
            output.write("==================================================================================");
            output.newLine();
            output.newLine();

            for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
                output.write("Person No." + (i + 1));
                output.newLine();
                output.write("name:" + model.getAddressBook().getPersonList().get(i).getName().fullName);
                output.newLine();
                if (!"01/01/1900".equals(model.getAddressBook().getPersonList().get(i).getBirthday().toString())) {
                    output.write("birthday:"
                            + model.getAddressBook().getPersonList().get(i).getBirthday().toString());
                    output.newLine();
                }
                output.write("phone:" + model.getAddressBook().getPersonList().get(i).getPhone().toString());
                output.newLine();
                output.write("email:" + model.getAddressBook().getPersonList().get(i).getEmail().toString());
                output.newLine();
                output.write("address:" + model.getAddressBook().getPersonList().get(i).getAddress().toString());
                output.newLine();
                output.write("tags:" + model.getAddressBook().getPersonList().get(i).getTags().toString());
                output.newLine();
                output.write("dateAdded:" + model.getAddressBook().getPersonList().get(i).getDateAdded().toString());
                output.newLine();
                output.write("eventAdded:");
                for (Event o: model.getAddressBook().getPersonList().get(i).getEvents()) {
                    output.write(o.getEventName().fullName + " || ");
                }
                output.newLine();
                output.write("==================================================================================");
                output.newLine();
                output.newLine();
            }
            for (int i = 0; i < model.getEventList().size(); i++) {
                output.write("Event No." + (i + 1));
                output.newLine();
                output.write("eventName:" + model.getEventList().get(i).getEventName().fullName);
                output.newLine();
                output.write("eventTime:" + model.getEventList().get(i).getEventTime().toString());
                output.newLine();
                output.write("eventDuration:" + model.getEventList().get(i).getEventTime().toString());
                output.newLine();
                output.write("==================================================================================");
                output.newLine();
                output.newLine();

            }
            output.write("End of file");
            output.close();
            return new CommandResult(MESSAGE_SUCCESS);

        } catch (Exception e) {
            throw new CommandException("can't create a file in the path" + filepath);
        }
    }
}
