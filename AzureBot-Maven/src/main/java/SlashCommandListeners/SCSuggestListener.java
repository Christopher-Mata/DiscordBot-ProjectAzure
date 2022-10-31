package SlashCommandListeners;

import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommandInteraction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Grabs any suggestions that the user wants
 */
public class SCSuggestListener {

    // Connects to discord
    DiscordApi api;

    // Constructor
    public SCSuggestListener(DiscordApi api){
        this.api = api;
        suggestCommand();
    }

    // Listener
    public void suggestCommand(){

        // Creates Event
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

            if(slashCommandInteraction.getCommandName().equals("suggest")) {

                // Creates a string array of the message content
                String suggestions = slashCommandInteraction.getOptionStringValueByIndex(0).get();

                // Creates a file object
                File file = new File("suggestions.txt");

                // Checks if the file exists
                try {
                    // Creates a print writer object and prints the suggestion to a file
                    PrintWriter writer = new PrintWriter(file);
                    writer.println("**: " + suggestions);
                    writer.close();

                    // Sends a message to the channel that the suggestion was successfully added
                    slashCommandInteraction.createImmediateResponder()
                            .setContent("Suggestion added! \n ``" + suggestions + "``")
                            .respond();

                } catch (FileNotFoundException e) {
                    slashCommandInteraction.createImmediateResponder()
                            .setContent("There was an internal error adding the suggestion "
                                    + "we will notify you when it is fixed <3")
                            .respond();
                    e.printStackTrace();
                }
            }
        });
    }
}
