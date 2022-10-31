import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

/**
 * This class is used to store the properties of the bot.
 */
public class AzureProperties {

    /**
     * The token of the bot.
     */
    String token;

    /**
     * The api of the bot.
     */
    DiscordApi api;

    /**
     * Constructor for the properties.
     */
    public AzureProperties(){
        this.token = "OTU5NDk0MzgyMjEyNDgxMDU0.Gipyg0.0d8KgO28DPIz2YaP0AgtWvvFKKsl-mOadcgD7s";
    }

    /**
     * Logs in the bot.
     */
    public void buildDiscordApi(){
        api = new DiscordApiBuilder().setToken(token).login().join();
    }

    /**
     * Gets the api of the bot.
     * @return The api of the bot.
     */
    public DiscordApi getApi() {
        return api;
    }
}
