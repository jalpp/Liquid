package Engine;

import chariot.Client;
import chariot.model.Enums;
import chariot.model.Event;
import chariot.model.GameStateEvent;
import chariot.model.VariantType;
import chariot.model.Enums.Speed;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Objects;
import java.util.Random;


public class LichessBotRunner {


    private static boolean isRapid;
    private static boolean isBlitz;
    private static boolean isCla;

    private static Liquid_Levels level;



    public LichessBotRunner(){

    }


    public static void main(String[] args) {

        var client = Client.auth(System.getenv("BOT-TOKEN"));
        var bot = client.bot();
        var events = bot.connect().stream();
        var username = client.account().profile().get().name().toLowerCase();
        String[] s = {"maia1", "maia5", "maia9", "charibot",  "TurtleBot", "SxRandom", "zeekat", "knucklefish"};
        int picker = s.length;
        int index = new Random().nextInt(picker);
        String botname = s[index];

        try {
            level = LiquidSearchEngine.determineAdaptability(botname);
            bot.challengeKeepAlive(botname, challengeBuilder -> challengeBuilder.clockRapid10m0s().rated(false).color(Enums.ColorPref.white));
        }catch (Exception e){
            System.out.println("Failed Challege " + botname);
        }

        events.forEach(event -> {
            switch (event.type()) {
                case challenge -> {
                    var challenge = (Event.ChallengeEvent) event;
                    System.out.println(challenge);
                    boolean isPlaying = Client.basic().users().byId("LISEBOT").get().accountStats().playing() > 1;
                    boolean std = challenge.challenge().gameType().variant() == VariantType.Variant.standard;
                    isRapid = challenge.challenge().gameType().timeControl().speed().name().equalsIgnoreCase("rapid");
                    isBlitz = challenge.challenge().gameType().timeControl().speed().name().equalsIgnoreCase("blitz");
                    isCla = challenge.challenge().gameType().timeControl().speed().name().equalsIgnoreCase("classical");
                    if(!Objects.equals(challenge.challenge().players().challengerOpt().get().user().name(), "LISEBOT")){
                        level = LiquidSearchEngine.determineAdaptability(challenge.challenge().players().challengerOpt().get().user().name());
                    }

                    boolean isCoores = challenge.challenge().gameType().timeControl().speed() == Speed.correspondence;
                    boolean isblackside = challenge.challenge().colorInfo().request().name().equalsIgnoreCase("white");
                    boolean isRandom = challenge.challenge().colorInfo().request().name().equalsIgnoreCase("random");
                    System.out.println(challenge);
                    if (std && !isCoores && !isblackside && !isPlaying && !isRandom) {
                        bot.acceptChallenge(event.id());
                    } else if (isCoores) {
                        bot.declineChallenge(event.id(), Enums.DeclineReason.Provider::timeControl);

                    } else if (isblackside || isRandom) {
                        bot.declineChallenge(event.id(), Enums.DeclineReason.Provider::generic);
                    } else if (isPlaying){
                        bot.declineChallenge(event.id(), Enums.DeclineReason.Provider::later);
                    }
                    else {
                        bot.declineChallenge(event.id(), Enums.DeclineReason.Provider::variant);
                    }
                }
                case challengeDeclined, challengeCanceled -> bot.abort(event.id());
                case gameFinish -> {
                    bot.chat(event.id(), "Thanks for playing me! ggs");
                    bot.chatSpectators(event.id(), "Thanks for watching!");
                }
                case gameStart -> {

                    bot.chatSpectators(event.id(), "Running Lise Mode " + level.toString());
                    var gameEvents = bot.connectToGame(event.id()).stream();
                    var board = new Board();
                    var engine = new LiquidSearchEngine(board);
                    boolean[] isWhite = {false};
                    bot.chat(event.id(), "omg you are very strong.. I'm scared but hey good luck!!");
                    gameEvents.forEach(gameEvent -> {
                        switch (gameEvent.type()) {
                            case opponentGone -> bot.abort(event.id());
                            case gameFull -> {
                                try {

                                    isWhite[0] = ((GameStateEvent.Full) gameEvent).white().name().toLowerCase().equals(username);

                                    System.out.println(isWhite[0]);
                                    if (isWhite[0]) {
                                        try {

                                            var move = engine.findBestMoveBasedOnLevels(level, board);
                                            bot.move(event.id(), move.toString());
                                            board.doMove(move);
                                            

                                        } catch (Exception e) {
                                            bot.resign(event.id());
                                        }
                                    }

                                } catch (Exception e) {
                                    bot.resign(event.id());
                                    System.out.println(e.getMessage());
                                }
                            }
                            case gameState -> {

                                try {

                                    var names = ((GameStateEvent.State) gameEvent).moves().split(" ");
                                    var whiteTurn = names.length % 2 == 0;

                                    System.out.println(whiteTurn);
                                    if (isWhite[0] == whiteTurn) {
                                        try {
                                            var name = names[names.length - 1];
                                            var from = Square.fromValue(name.substring(0, 2).toUpperCase());
                                            var to = Square.fromValue(name.substring(2, 4).toUpperCase());
                                            if (names.length == 5) {
                                                var type = PieceType.fromSanSymbol(name.substring(4).toUpperCase());
                                                var piece = Piece.make(whiteTurn ? Side.WHITE : Side.BLACK, type);
                                                var move = new Move(from, to, piece);
                                                board.doMove(move);
                                            } else {
                                                var move = new Move(from, to);
                                                board.doMove(move);
                                            }
                                        } catch (Exception e) {
                                            bot.resign(event.id());
                                        }

                                        if(board.getMoveCounter() > 10 && isRapid){
                                            Thread.sleep(new Random().nextInt(0, 10000));
                                        } else if (board.getMoveCounter() > 10 && isBlitz) {
                                            Thread.sleep(new Random().nextInt(0, 5000));
                                        } else if (board.getMoveCounter() > 10 && isCla) {
                                            Thread.sleep(new Random().nextInt(0, 40000));
                                        } else{
                                            Thread.sleep(0);
                                        }


                                        if(board.isKingAttacked()){
                                            bot.chat(event.id(), "yo watch out!");
                                            bot.chatSpectators(event.id(), "seems like someone's king attacked lol");
                                        }

                                        if(board.isRepetition()){
                                            bot.chat(event.id(), "lets repeat I want draw");
                                            bot.chatSpectators(event.id(), "he really trying for draw LOL");
                                        }

                                        bot.chatSpectators(event.id(), "Lise Eval: " + engine.evaluateBoard() + " Stockfish Eval: " + StockFish.getEvalForFEN(13, board.getFen()));
                                        var move = engine.findBestMoveBasedOnLevels(level, board);
                                        bot.move(event.id(), move.toString());
                                        board.doMove(move);

                                    }

                                } catch (Exception e) {
                                    bot.resign(event.id());

                                }
                            }
                        }
                    });
                }
            }
        });


    }



}