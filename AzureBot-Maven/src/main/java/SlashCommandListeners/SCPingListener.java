package SlashCommandListeners;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

/**
 * Pong!
 */
public class SCPingListener{

    // Connects to discord
    DiscordApi api;

    // Constructor
    public SCPingListener(DiscordApi api){
        this.api = api;
        SCPing();
    }

    // Listener
    public void SCPing(){

        // Creates event
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

            if (slashCommandInteraction.getCommandName().equals("ping")) {
                slashCommandInteraction.createImmediateResponder()
                        .setContent("Pong!")
                        .setFlags(MessageFlag.EPHEMERAL) // Only visible for the user which invoked the command
                        .respond();
            }
        });
    }
}
