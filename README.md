# alpha-beta-pruning-minimax
### Description
An implementation of alpha beta pruning to reduce the number of nodes a minimax searching algorithm visits.

The alpha beta pruning implementation is the method alphabeta() in TakeStones.java.
The minimax search implementation is the method get_next_move() is in TakeStones.java.

### Usage

Usage: `java TakeStones [0] [1] [...] [n]`
 
[0] = total number of stones
[1] = number of stones taken
[...] = list of stones taken
[n] = depth

Example usage: `java TakeStones 7 3 1 4 2 3 ` where 1, 4, 2 is the list of stones taken and 3 is depth.
