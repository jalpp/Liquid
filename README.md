## Lise Chess Engine

Lise is Discord chess engine, that allows users to play
chess games with Lise engine

## Usage

**/move <String (SAN/LAN) chess move>**
play a chess move with Lise, using /move to input chess moves


**/resetboard**
reset, start, end a chess game with Lise

## Remarks
- [JDA](https://github.com/DV8FromTheWorld/JDA)
- [Chariot](https://github.com/tors42/chariot)
- [Java chessLib](https://github.com/bhlangonijr/chesslib) 
- [Lichess Gifs](https://github.com/lichess-org/lila-gif)

## Engine Algorithm

Lise is a beginner engine, who can play two main openings
Hippo defense and King's Indian Defense(Attack). Even though Lise can
play openings well, it plays middlegames/endgames like a intermediate 
player via using negamax + openingbook + basic eval function.

**Negamax Algorithm**
[Chessprogramming.org](https://www.chessprogramming.org/Negamax)

- runs on depth 5, more the depth better move but slower search (not ideal for Discord)
- functions with eval function 

**Eval function**

- takes account of chess board area and all legal moves
- compares current player's piece value count to oppoenet's
- looks into if its own king is attacked

**Piece Value**
The engine takes account a basic piece value 

- Bishop & Knight 3 points
- Pawn 1 point
- Rook 5 points
- Queen 15 points
- King Max Integer points

## Engine Upgrades

Lise is still being worked on, with intentions to add

- A full tablebase support for better endgame play
- more opening support
- support for playing white side
- support for tactics creation
- transition to NN for dynamic piece values

## Setup

- obtain discord bot token from Discord dev page
- set env variable discord-bot-token
- set up testing discord server
- run LiseChessEngine locally 
- run /move and start playing Lise!

## Play Lise
 To play Lise you can also configure LISEBOT (with built in commands)
[Play me!](https://top.gg/bot/930544707300393021)
