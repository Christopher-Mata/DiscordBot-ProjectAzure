package SlashCommandListeners;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import java.awt.*;

/**
 * Creates polls for the users
 */
public class SCPollListener{

    // Connects to discord
    public DiscordApi api;

    // Constructor
    public SCPollListener(DiscordApi api) {
        this.api = api;
        SCPoll();
    }

    // Listener
    public void SCPoll(){

        // Creates Event
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

            // Makes sure that It contains at least one argument
            if (slashCommandInteraction.getCommandName().equals("poll") &&
                    slashCommandInteraction.getArguments().size() > 0) {

                // Grabs the options that the user specified
                String opt1 = String.valueOf(slashCommandInteraction.getOptionStringRepresentationValueByIndex(0));
                String opt2 = String.valueOf(slashCommandInteraction.getOptionStringValueByIndex(1));

                // Format the first option
                for(int i = 0; i < opt1.length(); i++){

                    if (opt1.charAt(i) == '['){
                        opt1 = opt1.substring(i + 1, opt1.length() - 1);
                        break;
                    }
                }

                // Format the last option
                for(int i = 0; i < opt2.length(); i++){

                    if (opt2.charAt(i) == '['){
                        opt2 = opt2.substring(i + 1, opt2.length() - 1);
                        break;
                    }
                }

                // Creates the embed to host the poll
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Poll Created!!! PLZ respond.")
                        .setAuthor(slashCommandInteraction.getUser())
                        .setColor(Color.PINK)
                        .setDescription("**Option 1:**\n " + opt1 + "\n react with thumbs up! \n" +
                                "**Option 2:**\n " + opt2 + "\n react with thumbs down! \n");

                // Sends the embed
                slashCommandInteraction.createImmediateResponder().addEmbed(embed).respond();
            }
        });
    }
}
