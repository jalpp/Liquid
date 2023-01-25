## Lise Chess Engine

Lise is Beginner chess engine, that uses basic piece value and negamax algo. 


## Discord Usage

**/move <String (SAN/LAN) chess move>**
play a chess move with Lise, using /move to input chess moves


**/resetboard**
reset, start, end a chess game with Lise

## Lichess usage 

just challenge LISEBOT and play Lise in real time!

## Remarks
- [JDA](https://github.com/DV8FromTheWorld/JDA)
- [Chariot](https://github.com/tors42/chariot)
- [Java chessLib](https://github.com/bhlangonijr/chesslib) 
- [Lichess Gifs](https://github.com/lichess-org/lila-gif)
- [Zamfofex](https://github.com/zamfofex) **For setting up Lichess connection (Thank you!)** 


## Lise Acheivements:

- Beating Stockfish level 1

https://lichess1.org/game/export/gif/white/3yXuqQdK.gif?theme=blue&piece=cardinal

- drawing 3 times to beginner trained Maia Chess engine
https://lichess1.org/game/export/gif/white/Yfcf1R6t.gif?theme=blue&piece=cardinal


## Engine Algorithm

Lise is a beginner engine, who can play two main openings
Hippo defense and King's Indian Defense(Attack). Even though Lise can
play openings well, it plays middlegames/endgames like a intermediate 
player via using negamax + openingbook + basic eval function.

**Opening Book**

Runs for Discord, opening book not supported for Lichess

- [Hippo Defence](https://en.wikipedia.org/wiki/Hippopotamus_Defence)
- [King's Indian Defence](https://en.wikipedia.org/wiki/King%27s_Indian_Defence)

**Negamax Algorithm**
[Chessprogramming.org](https://www.chessprogramming.org/Negamax)

- runs on depth 5, more the depth better move but slower search (not ideal for Discord)
- functions with eval function 

**Eval function**

- takes account of chess board area and all legal moves
- compares current player's piece value count to oppoenet's


**Piece Value**
The engine takes account a basic piece value 

- Bishop & Knight 300 points
- Pawn 100 point
- Rook 500 points
- Queen 1000 points
- King 1000 points

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
- set env variable lichess_bot_token
- set up testing discord server
- run LiseChessEngine locally 
- run /move and start playing Lise!


## Play Lise on Discord
 To play Lise you can also configure LISEBOT (with built in commands)
[Play me!](https://top.gg/bot/930544707300393021)


## Play Lise on Lichess
(Bot maybe down to due to maintaince)
[Challenge Lise!](https://lichess.org/@/LISEBOT)
