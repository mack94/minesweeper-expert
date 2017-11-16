:- use_module('../helpers').

% Basic reducer: reduces the numbers in fields, to the number minus amount of mines-flags in each field neighbourhood 

nothing(B).

reduce(BoardInit, BoardResult) :- nothing(BoardInit).