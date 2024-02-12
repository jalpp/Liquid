package Engine;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;

import java.util.Scanner;

public class UCIProtocolRunner {

   static Liquid_Levels levels = null;



    public static void main(String[] args) {
        LiquidSearchEngine engine = new LiquidSearchEngine(new Board());
        Scanner scanner = new Scanner(System.in);
        System.out.println(engine.EngineInfo() + "\n");

        engine.EngineUCICommands();


        ChangeLevel(scanner, engine);

        while (true) {
            String input = scanner.nextLine();
            if ("uci".equals(input)) {
                System.out.println(engine.EngineInfo());
                engine.EngineUCICommands();
            } else if ("isready".equals(input)) {
                try {

                    System.out.println(engine.findBestMoveBasedOnLevels(levels, new Board()));
                    System.out.println("Lise started properly");
                    engine.EngineUCICommands();
                }catch (Exception e){
                    System.out.println("Error! Lise is broken");
                    break;
                }
            } else if (input.startsWith("position")) {
                String fen = input.substring("position".length()).trim();
                if(fen.contains("b")){
                    Board b = new Board();
                    b.setSideToMove(Side.BLACK);
                    b.loadFromFen(fen);
                    LiquidSearchEngine engineFEN = new LiquidSearchEngine(b);
                    System.out.println(engineFEN.findBestMoveBasedOnLevels(levels, b));
                }else{
                    Board b = new Board();
                    b.setSideToMove(Side.WHITE);
                    b.loadFromFen(fen);
                    LiquidSearchEngine engineFEN = new LiquidSearchEngine(b);
                    System.out.println(engineFEN.findBestMoveBasedOnLevels(levels, b));
                }

            }

            else if (input.startsWith("level")){
                ChangeLevel(scanner, engine);
            }

            else if (input.startsWith("eval")) {
                String fen = input.substring("eval".length()).trim();
                if(fen.contains("b")){
                    Board b = new Board();
                    b.loadFromFen(fen);
                    b.setSideToMove(Side.BLACK);
                    LiquidSearchEngine engineFEN = new LiquidSearchEngine(b);
                    System.out.println(engineFEN.evaluateBoard());
                }else{
                    Board b = new Board();
                    b.loadFromFen(fen);
                    b.setSideToMove(Side.WHITE);
                    LiquidSearchEngine engineFEN = new LiquidSearchEngine(b);
                    System.out.println(engineFEN.evaluateBoard());
                }

            } else if ("quit".equals(input)) {
                System.out.println("Lise Engine is shut down");
                break;
            }
        }
        scanner.close();
    }


    public static void ChangeLevel(Scanner scanner, LiquidSearchEngine engine){
        System.out.println("Select Level [1 - 4] 1 = Beginner 4 = BEAST");
        String level = scanner.nextLine();

        int le = Integer.parseInt(level);

        switch (le){
            case 1 -> {
                levels = Liquid_Levels.BEGINNER;
                System.out.println("Configured Lise Beginner Level!");
                engine.EngineUCICommands();

            }

            case 2 -> {
                levels = Liquid_Levels.NOVICE;
                System.out.println("Configured Lise Novice Level!");
                engine.EngineUCICommands();

            }

            case 3 -> {
                levels = Liquid_Levels.STRONG;
                System.out.println("Configured Lise Strong Level!");
                engine.EngineUCICommands();

            }

            case 4 -> {
                levels = Liquid_Levels.BEAST;
                System.out.println("Configured Lise Beast Level!");
                engine.EngineUCICommands();

            }


        }

    }




}
