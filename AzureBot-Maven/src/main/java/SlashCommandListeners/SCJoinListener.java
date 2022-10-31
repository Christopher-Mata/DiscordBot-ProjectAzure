package SlashCommandListeners;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

/**
 * Makes the bot join a voice channel
 */
public class SCJoinListener {

    // Connects to discord
    DiscordApi api;

    // Constructor
    public SCJoinListener(DiscordApi api){
        this.api = api;
        joinCommand();
    }

    // Listener
    public void joinCommand(){

        // Creates Event
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

            if(slashCommandInteraction.getCommandName().equals("join")){

                // There is a possibility that an error might occure
                try {

                    // Gets user info
                    Server server = slashCommandInteraction.getServer().get();
                    User user = slashCommandInteraction.getUser();
                    ServerVoiceChannel voiceChannel = server.getConnectedVoiceChannel(user).get();

                    // Connects to the VC and sends a useful message
                    voiceChannel.connect();
                    slashCommandInteraction.createImmediateResponder()
                            .setContent("Successfully join the channel!")
                            .respond();

                    // Disconnects if it is the last person in VC
                    if(voiceChannel.getConnectedUsers().size() == 1){
                        voiceChannel.disconnect();
                    }

                    // Sends an error message if it could not be handled
                } catch (Exception e){
                    slashCommandInteraction.createImmediateResponder()
                            .setContent("Could not connect to the voice channel, you can give me the permission to" +
                                    "to do so by updating my role in the server!!!");
                }
            }
        });
    }
}
