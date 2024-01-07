
This repo contains source code for (Li)chess search engine bot's Discord chess engine, and (Li)quid chess engine both are part of the Lise bot code family.

# Liquid Search Engine [LISE]
Liquid chess engine tries to replicate human play, self-adjusting according to the opponent's Lichess blitz rating, named Liquid as it adapts to the opponent's rating play meaning plays strong against stronger players, weak against beginner players.

## Liquid comes in 4 modes
- Beast [2400+]
- Strong [1900 - 2400]
- Novice [1400 - 1900]
- Beginner [0 - 1400]

## Liquid's helpers

Liquid uses Stockfish's best move to come up with the right one, but according to its mode, it can self-adjust to using Stockfish or its logic.

- Stockfish [running at various depths for various modes] 
(The Stockfish developers (see AUTHORS file). Stockfish [Computer software]. https://github.com/official-stockfish/Stockfish)
- Negamax algorithm
- frequency-based blunder logic that plays a stockfish or the best move according to eval function and uses random frequency technique to "throw" like a human and mess up but at same time play the best move

## Liquid's Eval

  - the amount of pieces both sides have
  - both kings are attacked
  - board space and center space 
  - look at captures
  - look at which pieces are where



# Lise Chess Engine

Lise is an effortless engine that runs at low depths and performs a chess engine role in Discord, it's part of the Lichess search engine bot's repo code

## Lise mode

- Beginner

## Lise's helper
simple negamax method to search for moves at lower depths


## Challenge (Li)quid search engine Lise

Please only challenge white side casual/rated 

Play Lise [Here](https://lichess.org/@/LISEBOT)

# Authors
@jalpp


