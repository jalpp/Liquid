
This repo contains source code for (Li)chess search engine bot's Discord chess engine, and (Li)quid chess engine both are part of Lise bot code family.

# Liquid Search Engine [LISE]
Liquid chess engine tries to replicate human play, self adjusting according to opponent's Lichess blitz rating, named Liquid as it adapts to opponent's rating play meaning plays strong against stronger players, weak against beginner players.

## Liquid comes in 4 modes
- Beast [2400+]
- Strong [1900 - 2400]
- Novice [1400 - 1900]
- Beginner [0 - 1400]

## Liquid's helpers

Liquid uses Stockfish's best move to come up with the right move, but according to the mode it runs in, it can self adjust to using Stockfish or its own logic.

- Stockfish [running at various depths for various modes]
- Negamax algorithm 

## Liquid's Eval

  - amount of pieces both sides have
  - both kings are attacked
  - board space and center space 
  - look at captures
  - look at which pieces are where



# Lise Chess Engine

Lise is a very simple engine that runs at low depths and performs chess engine role in Discord, its part of Lichess search engine bot's repo code

# Lise mode

- Beginner

## Lise's helper
simple negamax method to search for moves at lower depths


## Challenge (Li)quid search engine Lise

Please only challenge white side casual/rated 

Play Lise [Here](https://lichess.org/@/LISEBOT)

# Authors
@jalpp


