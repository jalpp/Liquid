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

- Drawing to Maia9 (avg rating 1900)

- Drawing to Maia5 (avg rating 1700)

- Drawing 3 times to beginner trained Maia Chess engine Maia1

- Beating random movers within 5 to 30 moves

Some of the acheivements are notable beacause Lise is using plan search alongside using its Eval function to determine the best moves.



## Discord Engine Algorithm

For Discord Lise supports two operations **/resetboard** and **/move** here Lise only plays blackside due to Discord's message nature, and algoritm runs on plan negamax move search.

- Simple Negamax move search

- Simple eval function consider space only


## Lichess Engine Algorithm

- Lichess Lise engine is intermediate type of engine (rating about 1200) that uses its own eval function and negamax and human generative blackside play.

# White Side Eval Function

- Give each legal move an identity by assigning it value for given piece it has. This value is inverse of piece value.

- Give each capture an identity by assigning it value for given piece it has plus an constant of 1 to find re-captures

- Material count for both sides and use piece Value function

- King Safety and Mating net finder


## Black Side Function

Lise uses shuffling method and uses index hashtable to randomly give each legal move an unqiue value. Along side this, Lise constaly shuffles legal moves out of order to generate an beginner's approch by playing moves what it thinks are best by assuming moves based on shuffling method.


## Challenge Lise

Play Lise [Here](https://lichess.org/@/LISEBOT)

# Authors
@jalpp

Lise is fun hobby project I maintain and develope, after Lise drawing to maia9 I am activtly working on the engine to raise its rating to 1500, if you like to contribute feel free, here is [Discord Invite](https://discord.gg/K2NKarM5KV).

