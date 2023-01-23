import chariot.Client;
import chariot.model.GameEvent;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;


/**

Lichess UI runner, provides Lichess GUI and uses Chariot lib for Lichess connection

special thanks to Zamfofox for implementing it from scratch.

@author Zamfofox https://github.com/zamfofex


 */

public class Lichess {

    public static void main(String[] args) {
        var client = Client.auth(System.getenv("lichess_bot_token"));
        var bot = client.bot();
        var events = bot.connect().stream();
        var username = client.account().profile().get().username().toLowerCase();

        events.forEach(event -> {

            switch (event.type()) {

            case challenge:
                bot.acceptChallenge(event.id());
                break;

            case gameStart:

                var gameEvents = bot.connectToGame(event.id()).stream();
                var board = new Board();
                var engine = new LiseChessEngine(board);
                boolean[] isWhite = {false};

                // todo: handle an ongoing game
                gameEvents.forEach(gameEvent -> {
                    switch (gameEvent.type()) {

                    case gameFull:
                        isWhite[0] = ((GameEvent.Full) gameEvent).white().name().toLowerCase().equals(username);
                        if (isWhite[0]) {
                            var move = engine.findBestMove();
                            bot.move(event.id(), move.toString());
                            board.doMove(move);
                        }
                        break;

                    case gameState:
                        var names = ((GameEvent.State) gameEvent).moves().split(" ");
                        var whiteTurn = names.length % 2 == 0;

                        if (isWhite[0] == whiteTurn) {
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

                            var move = engine.findBestMove();
                            bot.move(event.id(), move.toString());
                            board.doMove(move);
                        }
                        break;
                    }
                });

                break;
            }
        });
    }
}