import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;

import java.util.ArrayList;
import java.util.Random;

public class LiseChessEngine {


    private Board board;
    private ArrayList<Board> trainingGames = new ArrayList<>();
    private String[] openingBook;

    private Client client;
    private Random random;
    private String movesan;
    private int movePicker;


    public LiseChessEngine(Board board){
        this.board = board;
    }

    public void doWhiteMove(String move){


        this.board.setSideToMove(Side.WHITE);
        this.board.doMove(move);
        this.board.setSideToMove(Side.BLACK);
    }




    public Boolean gameOver(){
        if(this.board.isMated() || this.board.isDraw() || this.board.isStaleMate()){
            this.trainingGames.add(this.board);
            return true;
        }

        return false;
    }


    public String playFromOpeningBookHippo(int index){
        this.openingBook = new String[]{"move-maker","e6", "Ne7", "g6", "Bg7", "O-O", "d6", "Nd7", "b6", "Bb7", "a6", "h6", "c6", "Qc7"};

        return this.openingBook[index];




    }


    public String playFromOpeningBookKIA(int index){
        this.openingBook = new String[]{"move-maker", "Nf6", "d6", "g6", "Bg7", "O-O", "a6", "Nd7", "b6", "c6", "Re8", "Qc7", "Rb8", "d5"};

        return this.openingBook[index];
    }


    public void playRandomLegalMoves(){

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
                this.board.doMove(this.board.legalMoves().get(movePicker));
                this.board.setSideToMove(Side.WHITE);
            }

        }

         else{

            //this.board.

            this.board.doMove(this.board.legalMoves().get(movePicker));
            this.board.setSideToMove(Side.WHITE);
        }






       // return this.board;
    }




    public void playTablebaseMoves(){
       // this.board = new Board();
        this.board.setSideToMove(Side.BLACK);
        this.client = Client.basic();

        String move = this.client.tablebase().standard(this.board.getFen()).get().moves().get(this.movePicker).san();
        this.board.doMove(move);
        this.board.setSideToMove(Side.WHITE);

        //return this.board;

    }

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
