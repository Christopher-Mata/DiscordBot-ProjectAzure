import org.apache.log4j.BasicConfigurator;
import org.javacord.api.DiscordApi;

/**
 * Main class for the bot.
 * @author Christopher
 * @version 1.0
 * @since 1.0
 */
public class Azure {
    public static void main(String[] args) {

        BasicConfigurator.configure();

        // Create a new AzureProperties object which contains
        // the properties for the bot.
        AzureProperties azureProperties = new AzureProperties();
        System.out.println("Bot properties obtained");
        azureProperties.buildDiscordApi();
        System.out.println("Discord api built");
        DiscordApi api = azureProperties.getApi();
        System.out.println("Bot is now ready!");

        // adds the listeners/SlashCommands to the api
        new SlashCommands(api);
        System.out.println("SlashCommands Initiated!");
    }
}
