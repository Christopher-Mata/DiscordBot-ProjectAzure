import SlashCommandListeners.*;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import java.util.Arrays;

/**
 * This is to allow the bot to take advantage of the new discord SlashCommand Features
 */

public class SlashCommands {

    // Allows access to the Discord API
    public DiscordApi api;

    // Constructor to be used by the main Azure Class
    public SlashCommands(DiscordApi api){

        // This adds the DiscordAPI to ues in this class
        this.api = api;

        // This will add the slash commands to the bot inorder to be used by the users
        addSlashCommands();
        addSlashFunctions();
    }

    // This adds the SlashCommands to the DiscordAPI
    public void addSlashCommands(){

        SlashCommand.with("ping", "A simple ping pong command!")
                .createGlobal(api)
                .join();
        SlashCommand.with("invite", "Creates a invite to this server")
                .createGlobal(api)
                .join();
        SlashCommand.with("meeting", "Makes a Temporary VC to hold meetings(They will disappear" +
                                "30 seconds after everyone has left)",
                        Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.DECIMAL, "NumberOfPeople", "people attending",true),
                                SlashCommandOption.create(SlashCommandOptionType.STRING, "Name", "name of meeting", true)
                        ))
                .createGlobal(api)
                .join();
        SlashCommand.with("poll", "Create a Poll for voting!!!",
                        Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.STRING, "OptOne", "one option you want",true),
                                SlashCommandOption.create(SlashCommandOptionType.STRING, "OptTwo", "second option you want", true)
                        ))
                .createGlobal(api)
                .join();
        SlashCommand.with("help", "List of Commands and GitHub Repo!!!")
                .createGlobal(api)
                .join();
        SlashCommand.with("join", "Joins the VC so you dont feel lonely")
                .createGlobal(api)
                .join();
        SlashCommand.with("repo", "Brings you to my home!!!")
                .createGlobal(api)
                .join();
        SlashCommand.with("suggest", "Add to me ( •̀ω•́ )σ",
                        Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.STRING, "suggestion",
                                        "type your suggestion here!!!",true)))
                .createGlobal(api)
                .join();
        SlashCommand.with("play", "share songs with your friends!",
                        Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.STRING, "vimeoLink",
                                        "type your song here!!!", true)
                        ))
                .createGlobal(api)
                .join();
    }

    // This connects the listeners to the API
    public void addSlashFunctions(){
        new SCPingListener(api);
        new SCPollListener(api);
        new SCInviteListener(api);
        new SCHelpListener(api);
        new SCRepoListener(api);
        new SCJoinListener(api);
        new SCTempChannelListener(api);
        new SCSuggestListener(api);
        new SCPlayerListener(api);
    }
}
