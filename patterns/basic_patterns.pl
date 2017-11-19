:- module(basic_patterns, [count_virgins_2D/2, check_basic_pattern/1, is_virgin_mine_basic_pattern/1]).
:- use_module('../helpers').

%counts virgins in 2D part of board, useful for basic strategies
nbh_count([], 0).
nbh_count([Field|MoreFields], NewSum) :-
    helpers:virgin_field(Field),
    nbh_count(MoreFields, Sum),
    NewSum is Sum + 1;
    nbh_count(MoreFields, NewSum).

count_virgins_2D(B, Count) :- 
    flatten(B, FlattenBoard),
    nbh_count(FlattenBoard, Count), !.

%check if number if there is the same number of neighbouring virgin fields as the number on field
check_basic_pattern(B33) :- 
    helpers:field_value(B33, 2, 2, FieldValue),
    count_virgins_2D(B33, NumOfNbVirgins), 
    (NumOfNbVirgins =:= FieldValue).

%check if any of the surronding fields with number fulfill basic_pattern with given virgin field
%get_basic_virgin_mark([], helpers:sure_mine). %TODO: mark sure mine with some number?
%is_mine_basic_pattern(B55, FieldMark) :-
is_virgin_mine_basic_pattern(B55) :-
    helpers:get_adj_coords(3, CoordX),
    helpers:get_adj_coords(3, CoordY),
    helpers:field_value(B55, CoordX, CoordY, Value),
    helpers:number_field(Value),
    helpers:cut3x3(B55, CoordX, CoordY, B33),
    check_basic_pattern(B33),
    !.

    
    