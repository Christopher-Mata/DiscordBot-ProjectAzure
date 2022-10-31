package SlashCommandListeners;

import Assets.LavaplayerAudioSource;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;

public class SCPlayerListener {

    DiscordApi api;

    public SCPlayerListener(DiscordApi api){
        this.api = api;
        audioPlayer();
    }

    public void audioPlayer() {
        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction sci = event.getSlashCommandInteraction();

            if (sci.getCommandName().equals("play")) {
                Server server = sci.getServer().get();
                User user = sci.getUser();
                ServerVoiceChannel serverVoiceChannel = user.getConnectedVoiceChannel(server).get();
                DiscordApi api = sci.getApi();
                String url = sci.getOptionStringRepresentationValueByIndex(0).toString();

                //Format string
                for (int i = 0; i < url.length(); i++) {
                    if (url.charAt(i) == '[') {
                        url = url.substring(i + 1, url.length() - 1);
                        break;
                    }
                }

                String finalUrl = url;
                serverVoiceChannel.connect().thenAccept(audioConnection -> {

                    // Create a player manager
                    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                    playerManager.registerSourceManager(new YoutubeAudioSourceManager(true));
                    AudioPlayer player = playerManager.createPlayer();

                    // Create an audio source and add it to the audio connection's queue
                    AudioSource source = new LavaplayerAudioSource(api, player);
                    audioConnection.setAudioSource(source);


                    // THIS URL GIVES Track NOT LOADED: https://soundcloud.com/discover/sets/personalized-tracks::3rr03:1326012928
                    // YouTube Audio Source manager is gone
                    // You can now use the AudioPlayer like you would normally do with Lavaplayer, e.g.,
                    playerManager.loadItem(finalUrl, new AudioLoadResultHandler() {

                        @Override
                        public void trackLoaded(AudioTrack track) {
                            player.playTrack(track);
                        }

                        @Override
                        public void playlistLoaded(AudioPlaylist playlist) {
                            for (AudioTrack track : playlist.getTracks()) {
                                player.playTrack(track);
                            }
                        }

                        @Override
                        public void noMatches() {
                            // Notify the user that we've got nothing
                            sci.createImmediateResponder()
                                    .setContent("No Song Matches, Please try again!")
                                    .respond();
                        }

                        @Override
                        public void loadFailed(FriendlyException throwable) {
                            // Notify the user that everything exploded
                            sci.createImmediateResponder()
                                    .setContent("I got overwhelmed, PLZ try a valid link! \n Failed to play: " + finalUrl)
                                    .respond();
                        }
                    });
                }).exceptionally(e -> {

                    // Failed to connect to voice channel (no permissions?)
                    sci.createImmediateResponder()
                            .setContent("Something went wrong while loading " + finalUrl + " Plz try again!")
                            .respond();

                    e.printStackTrace();
                    return null;
                });

                sci.createImmediateResponder()
                        .setContent("Now Playing: " + url)
                        .respond();
            }
        });
    }
}
