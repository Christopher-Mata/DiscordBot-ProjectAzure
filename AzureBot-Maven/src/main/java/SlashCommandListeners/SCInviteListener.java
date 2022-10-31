package SlashCommandListeners;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.invite.Invite;
import org.javacord.api.entity.server.invite.InviteBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

/**
 * Creates server invites
 */
public class SCInviteListener {

    // Connects to Discord
    DiscordApi api;

    // Constructor
    public SCInviteListener(DiscordApi api){
        this.api = api;
        inviteListener();
    }

    // Listener
    public void inviteListener(){

        // Creates Event
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

            if(slashCommandInteraction.getCommandName().equals("invite")){

                // Grabs the text channel the command was sent
                ServerTextChannel channel1 = slashCommandInteraction.getChannel().get().asServerTextChannel().get();

                // Creates the invite
                Invite invite = new InviteBuilder(channel1)
                        .setMaxAgeInSeconds(60*60*24)
                        .setMaxUses(42)
                        .create()
                        .join();

                // Send the invite
                slashCommandInteraction.createImmediateResponder()
                        .setContent("Here is the Invite you requested!!! \n")
                        .append(invite.getUrl())
                        .respond();
            }
        });
    }
}
