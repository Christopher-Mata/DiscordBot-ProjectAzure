package SlashCommandListeners;

import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommandInteraction;

/**
 * Brings the user to my repo
 */
public class SCRepoListener {

    // Connects to discord
    DiscordApi api;

    // Constructor
    public SCRepoListener(DiscordApi api){
        this.api = api;
        repoCommand();
    }

    // Listener
    public void repoCommand(){

        // Creates Event
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

            if(slashCommandInteraction.getCommandName().equals("repo")){

                // Sends a message
                slashCommandInteraction.createImmediateResponder()
                        .setContent("https://github.com/Christopher-Mata/DiscordBot-Azure")
                        .respond();
            }
        });
    }
}
