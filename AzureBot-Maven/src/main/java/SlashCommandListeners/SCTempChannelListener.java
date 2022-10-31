package SlashCommandListeners;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.ServerVoiceChannelBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommandInteraction;
import java.util.concurrent.TimeUnit;

/**
 * Creates a temp channel for the user to hold meetings
 */
public class SCTempChannelListener {

    // Connects to discord
    DiscordApi api;

    // To be used by the listener
    ServerVoiceChannel channel;

    // Constructor
    public SCTempChannelListener(DiscordApi api){
        this.api = api;
        tempChannelCommand();
    }

    // Listener
    public void tempChannelCommand(){

        // Creates the event
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

            // If the event contains the command then execute the program
            if(slashCommandInteraction.getCommandName().equals("meeting")){

                // Gets the options the user specified
                Server server = slashCommandInteraction.getServer().get();
                String name = String.valueOf(slashCommandInteraction.getOptionStringValueByIndex(1));

                // Formats the option the user wanted
                for(int i = 0; i < name.length(); i++){

                    if (name.charAt(i) == '['){
                        name = name.substring(i + 1, name.length() - 1);
                        break;
                    }
                }

                // Gets the number of people to attend
                double numOfPeople = slashCommandInteraction.getOptionDecimalValueByIndex(0).get();

                try {

                    // Creates the new channel
                    channel = new ServerVoiceChannelBuilder(server)
                            .setName(name)
                            .setUserlimit((int) numOfPeople)
                            .create()
                            .join();

                    // Responds with a helpful message
                    slashCommandInteraction.createImmediateResponder()
                            .setContent("Temp VC created!!! if inactive for 30 seconds, it will be deleted. Everyone" +
                                    " leaves the VC it will also get deleted")
                            .respond();

                    // This is here to catch unwanted errors that might occure
                } catch(Exception e){

                    // The default I set for the VC
                    channel = new ServerVoiceChannelBuilder(server)
                            .setName("Meeting")
                            .setUserlimit(30)
                            .create()
                            .join();

                    // Sends a helpful message
                    slashCommandInteraction.createImmediateResponder()
                            .setContent("Could not create VC with intended amount, default set to 30!")
                            .respond();
                }

                // Delete the channel if the last user leaves
                channel.addServerVoiceChannelMemberLeaveListener(event1 -> {
                    if (event1.getChannel().getConnectedUserIds().isEmpty()) {
                        event1.getChannel().delete();
                    }
                });

                // Delete the channel if 30 seconds have passed without someone joining
                api.getThreadPool().getScheduler().schedule(() -> {
                    if (channel.getConnectedUserIds().isEmpty()) {
                        channel.delete();
                    }

                    // This will count to 30 seconds and delete the channel if reached
                }, 30, TimeUnit.SECONDS);
            }
        });
    }
}
