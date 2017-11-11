:- module(basic_patterns, [count_virgins_2D/2, check_basic_pattern/2]).
:- use_module('../helpers').


%counts virgins in 2D part of board, useful for basic strategies
nbh_count([], 0).
nbh_count([Field|MoreFields], NewSum) :-
    virgin_field(Field),
    nbh_count(MoreFields, Sum),
    NewSum is Sum + 1;
    nbh_count(MoreFields, NewSum).

count_virgins_2D(B, Count) :- 
    flatten(B, FlattenBoard),
    nbh_count(FlattenBoard, Count), !.

check_basic_pattern(B, FieldValue) :- count_virgins_2D(B, NumOfNbVirgins), (NumOfNbVirgins =:= FieldValue).     
    