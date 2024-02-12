package Engine;


import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;



import java.util.*;


public class LiseChessEngine {

    private Board board;

    public LiseChessEngine(Board board){
        this.board = board;
    }

    public Boolean gameOver(){
        if(this.board.isMated() || this.board.isDraw() || this.board.isStaleMate()){
            return true;
        }

        return false;
    }



    public void playDiscordBotMoves(){
        this.board.doMove(findBestMove());
    }





    public Move findBestMove() {


            int depth = 5;
            int bestValue = Integer.MIN_VALUE;
            Move bestMove = null;
            for (Move move : MoveGenerator.generateLegalMoves(this.board)) {
                this.board.doMove(move);
                int value = negamax(depth - 1, Integer.MAX_VALUE, Integer.MIN_VALUE);
                this.board.undoMove();
                if (value > bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            }


            return bestMove;

    }



    private int negamax(int depth, int alpha, int beta) {
        if (depth == 0 || this.board.isMated() || this.board.isDraw()) {

            return evaluate();
        }
        int bestValue = Integer.MIN_VALUE;
        for (Move move : MoveGenerator.generateLegalMoves(this.board)) {
            this.board.doMove(move);
            int value = negamax(depth - 1, -beta, -alpha);
            this.board.undoMove();
            bestValue = Math.max(bestValue, value);
            alpha = Math.max(alpha, value);
            if (alpha >= beta) {
                break;
            }
        }


        return bestValue;
    }


    private int evaluate() {
        int score = 0;

        score -= 64;

        score += this.board.legalMoves().size();


        if(board.getSideToMove().equals(Side.WHITE)){
            return score * 1;
        }else{
            return score * -1;
        }

    }


    public void resetBoard(){
        this.board = new Board();
    }



    public String getImageOfCurrentBoard(boolean isBlack){
        ChessUtil chessUtil = new ChessUtil();
        return chessUtil.getImageFromFEN(this.board.getFen(), isBlack, "brown", "kosal");
    }



}
