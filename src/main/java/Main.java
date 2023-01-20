import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Main extends ListenerAdapter {

    private static JDABuilder jdaBuilder;

    private static JDA jda;

    private Board board = new Board();


    public static void main(String[] args) {


        jdaBuilder = JDABuilder.createDefault(System.getenv("discord-bot-token"));

        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        jdaBuilder.setActivity(Activity.playing("Play chess with Lise!"));

        jdaBuilder.addEventListeners(new Main());


        try {
            jda = jdaBuilder.build();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(Commands.slash("move", "make a move").addOption(OptionType.STRING, "play-move", "input chess move", true));
        commands.addCommands(Commands.slash("resetboard", "reset the board"));


        commands.queue();


    }


    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String name = event.getName();


        switch (name) {


            case "resetboard":

                this.board.loadFromFen(new Board().getFen());
                event.reply("board is reset!").queue();

                break;

            case "move":


                try {

                    LiseChessEngine engine = new LiseChessEngine(this.board);
                    String makemove = event.getOption("play-move").getAsString();


                    if (engine.gameOver()) {
                        event.reply("game over!").queue();
                        engine.resetBoard();
                        this.board = new Board();
                    }

                    this.board.doMove(makemove);


                    engine.abstractedRandomizer();

                    event.reply(engine.getImageOfCurrentBoard()).queue();
                    event.getChannel().sendMessage("** Your Turn!** ").queue();


                } catch (Exception e) {

                    event.reply("Not valid move! \n\n **If you are trying to castle use Captial letters (O-O & O-O-O)** \n\n (If you are running this command first time) The game is already on in other server, please reset the board with **/resetboard**! If you would like a to save your game, please join supprot server with **/invite**").setEphemeral(true).queue();

                }


                break;

        }

    }


}
