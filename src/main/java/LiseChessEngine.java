import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.move.Move;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class LiseChessEngine {
    
    
    



    private Board board;
    private ArrayList<Board> trainingGames = new ArrayList<>();
    private String[] openingBook;
    private List<Move> captures;
    private List<Move> attackMoves;

    private Client client;
    private Random random;
    private String movesan;
    private int movePicker;


    public LiseChessEngine(Board board){
        this.board = board;
    }







    public boolean doWhiteMove(String move){


        Move m = new Move(move, Side.WHITE);

        if(this.board.isMoveLegal(m, true)){
            return true;
        }else{
            return false;
        }


    }


   // check game state

    public Boolean gameOver(){
        if(this.board.isMated() || this.board.isDraw() || this.board.isStaleMate()){
            this.trainingGames.add(this.board);
            return true;
        }

        return false;
    }
    
    // Two main opening books


    public String playFromOpeningBookHippo(int index){
        this.openingBook = new String[]{"move-maker","e6", "Ne7", "g6", "Bg7", "O-O", "d6", "Nd7", "b6", "Bb7", "a6", "h6", "c6", "Qc7"};

        return this.openingBook[index];




    }


    public String playFromOpeningBookKIA(int index){
        this.openingBook = new String[]{"move-maker", "Nf6", "d6", "g6", "Bg7", "O-O", "a6", "Nd7", "b6", "c6", "Re8", "Qc7", "Rb8", "d5"};

        return this.openingBook[index];
    }
    
    // calculate and store all valid captures when king is not attacked, if the king is attacked captures are placed with non captures


    public List<Move> getCaptures(){

        if(this.board.isKingAttacked()){
            return this.board.legalMoves();
        }

        this.captures = this.board.pseudoLegalCaptures();

        return this.captures;
    }

    
   // searching for attacking moves in the middlegame, not currently used in main Engine logic, but will be added soon 




    public List<Move> searchMiddleGameMoves(){

        for(Move m: this.board.legalMoves()){
            if(this.board.isAttackedBy(m)){
                this.attackMoves.add(m);
            }

        }

        if(this.attackMoves.size() > 0){
            return this.attackMoves;
        }

        return this.board.legalMoves();
    }
    
    
    // critical Engine algo, plays best chess move from negamax algo + opening book, if openings don't go as planned well, you just play the best move!


    public void abstractedRandomizer(){
        this.random = new Random();
        this.movePicker = random.nextInt(this.board.legalMoves().size());
        this.board.setSideToMove(Side.BLACK);



        if(this.board.getMoveCounter() <= 13){

            try {

                Random mover = new Random();
                int coinFlip = mover.nextInt(2);

                if(coinFlip == 0){


                        this.board.doMove(this.playFromOpeningBookHippo(this.board.getMoveCounter()));


                }else{

                        this.board.doMove(this.playFromOpeningBookKIA(this.board.getMoveCounter()));

                }


                //this.board.setSideToMove(Side.WHITE);
            }catch (Exception e){
                //this.board.undoMove();

               this.board.doMove(findBestMove());
            }

        }

        else{

            //this.board.
            this.board.doMove(findBestMove());
        }

    }



    public void playRandomLegalMoves(){



      this.board.doMove(findBestMove());



       // return this.board;
    }
    
    // find the best chess move according to negamax algo, depth set to 5 for less search fast moves to plat

    public Move findBestMove() {
        int depth = 5; // Adjust the search depth as needed
        int bestValue = Integer.MIN_VALUE;
        Move bestMove = null;
        for (Move move : this.board.legalMoves()) {
            this.board.doMove(move);
            int value = -negamax(depth -1 , Integer.MAX_VALUE, Integer.MIN_VALUE);
            this.board.undoMove();
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }
    
    // negamax algo that uses max and min values to determine the best move to find, uses a simple eval function

    private int negamax(int depth, int alpha, int beta) {
        if (depth == 0 || this.board.isMated() || this.board.isDraw()) {
            // Evaluate the current board position and return a score
            return evaluate();
        }
        int bestValue = Integer.MIN_VALUE;
        for (Move move : this.board.legalMoves()) {
            this.board.doMove(move);
            int value = -negamax(depth - 1, -beta, -alpha);
            this.board.undoMove();
            bestValue = Math.max(bestValue, value);
            alpha = Math.max(alpha, value);
            if (alpha >= beta) {
                break;
            }
        }
        return bestValue;
    }
    
    
    
    // piece value function, follows "normal" chess piece values



    private int valueMapping(Piece p){
        int value = 0;
        switch (p){
            case WHITE_PAWN:
                return 1;
            case WHITE_BISHOP:
                return 3;
                //break;
            case WHITE_KNIGHT:
                return 3;
                //break;
            case WHITE_ROOK:
                return 5;
               // break;
            case WHITE_QUEEN:
                return 15;
               // break;
            case WHITE_KING:
                return Integer.MAX_VALUE;
               // break;
            case BLACK_PAWN:
                return 1;
               // break;
            case BLACK_BISHOP:
                return 3;
            //break;
            case BLACK_KNIGHT:
                return 3;
           // break;

            case BLACK_ROOK:
                return 5;
            //break;
            case BLACK_QUEEN:
                return 15;
            //break;
            case BLACK_KING:
                return Integer.MAX_VALUE;
            //break;
            default:
                return 0;
            //break;
        }
        //return 0;
    }


    
    // calculate white piece advantage on number of pieces with the values attached to it 

    private int whitePieceAdvantage(){
        int counter = 0;

        counter += (Piece.WHITE_BISHOP.ordinal() * valueMapping(Piece.WHITE_BISHOP))+ (Piece.WHITE_PAWN.ordinal() * valueMapping(Piece.WHITE_PAWN)) + (Piece.WHITE_QUEEN.ordinal()* valueMapping(Piece.WHITE_QUEEN)) + (Piece.WHITE_ROOK.ordinal() * valueMapping(Piece.WHITE_ROOK)) +
                (Piece.WHITE_KNIGHT.ordinal() * valueMapping(Piece.WHITE_KNIGHT)) + (Piece.WHITE_KING.ordinal() * valueMapping(Piece.WHITE_KING));

        return counter;
    }

    // calculate black piece advantage on number of pieces with the values attached to it 

    private int blackPieceAdvantage(){
        int blackCounter = 0;
        blackCounter += (Piece.BLACK_BISHOP.ordinal()* valueMapping(Piece.BLACK_BISHOP)) + (Piece.BLACK_KNIGHT.ordinal() * valueMapping(Piece.BLACK_KNIGHT))+ (Piece.BLACK_QUEEN.ordinal()* valueMapping(Piece.BLACK_QUEEN)) + (Piece.BLACK_ROOK.ordinal()
        * valueMapping(Piece.BLACK_ROOK))+ (Piece.BLACK_PAWN.ordinal()* valueMapping(Piece.BLACK_PAWN)) + (Piece.BLACK_KING.ordinal() * valueMapping(Piece.BLACK_KING));

        return blackCounter;
    }

    private int evaluate() {
        int score = 0;
        // material evaluation

        score += (this.whitePieceAdvantage() - this.blackPieceAdvantage());
        
        // chessboard area eval, less space less eval and vise versa
     
        score += this.board.legalMoves().size();
        
        // king attack eval, if you are under fire run!!
      
        if (this.board.isKingAttacked()) {
            score -= 50;
        }
        return score;
    }

    
    // very basic tactic creator, not used will come in future

    public List<Move> generateCheckTactics() {
        List<Move> tactics = new ArrayList<>();
        for (Move move : this.board.legalMoves()) {
            this.board.doMove(move);
            if (this.board.isKingAttacked()) {
                tactics.add(move);
            }
            this.board.undoMove();
        }
        return tactics;
    }





   // still in works, better way to implement an endgame tablebase support

    public void playTablebaseMoves(){
       // this.board = new Board();
        this.board.setSideToMove(Side.BLACK);
        this.client = Client.basic();

        String move = this.client.tablebase().standard(this.board.getFen()).get().moves().get(this.movePicker).san();
        this.board.doMove(move);
        this.board.setSideToMove(Side.WHITE);

        //return this.board;

    }
    
    // Discord helper methods

    public void resetBoard(){
        this.board = new Board();
    }

    public String getFenBoard(){
        return this.board.getFen();
    }

    public String getImageOfCurrentBoard(){
        ChessUtil chessUtil = new ChessUtil();
        return chessUtil.getImageFromFEN(this.board.getFen());
    }












}
