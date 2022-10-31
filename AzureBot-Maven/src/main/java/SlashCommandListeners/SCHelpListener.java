package SlashCommandListeners;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import java.awt.*;

/**
 * Sends a message containing a list of working commands
 */
public class SCHelpListener {

    // Connects to discord
    DiscordApi api;

    // Constructor
    public SCHelpListener(DiscordApi api){
        this.api = api;
        helpCommand();
    }

    // Listener
    public void helpCommand() {

        // Create Event
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

            if(slashCommandInteraction.getCommandName().equals("help")){

                // Create Embed with information
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("List Of Commands (Current Commands Converted to Slash-Commands)")
                        .setColor(Color.PINK)
                        .setDescription("""
                                **/help**\s
                                 I wounder what this does :)
                                **/ping**\s
                                 Pong :)
                                **/poll**\s
                                 Make a poll to debate w/ your buddies!!!\s
                                **/repo**\s
                                 Brings you to my Home!!!
                                **/invite**\s
                                 Makes you a temporary invite to this server(max of 42 people can use it)
                                 **/join\s**
                                  Joins the voice channel you are in to not make you feel lowly
                                 **/meeting**\s
                                  Will create a temp VC that will last for 30seconds of inactivity or until everyone leaves
                                 **/suggest**\s
                                  "Add to me ( •̀ω•́ )σ""");

                // Send the embed
                slashCommandInteraction.createImmediateResponder()
                        .addEmbed(embed)
                        .respond();
            }
        });
    }
}
