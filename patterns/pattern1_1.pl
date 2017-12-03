:- use_module('../helpers').

% IMPORTANT! 
% at the beggining, a reduction should be made
% add reduction to helpers, because it is reusable 

% Already clicked field but empty or reduced to 0 during reduction, it establish the new edge_function
zero_field(0).

% The agreement that -10 means the end of the game board (all borders).
out_of_board(-10).

% Check whether the field (Bx, By) has edge function as neighbourhood
% Bx, By - coordinates of the checked field
% Bx + SideX means the need to confirm vertical edge (-1 left, +1 right) function existence, SideY = 0 then,
% By + SideY means that we want to confirm the existence of horizontal edge (-1 above, +1 below). SideX = 0 then,
board_edge(Board, Bx, By, SideX, SideY) :- helpers:field_value(Board, Bx + SideX, By + SideY, FieldValue), (out_of_board(FieldValue); zero_field(FieldValue)).

% to each case, need to add Sx, Sy (Safe X, Safe Y - coordinated of field to mark "safe") - deprecated

% is_11_pattern(Board, SideX, SideY, SafeX, SafeY) - function that checks whether 1-1 pattern present
% SideX,Y means where is the edge_function (-1 left, above; 1 right, below)
% SafeX,Y means the number of fields on the board which are safe

% LEGEND
% A - any field
% 0 - corner field or zeroed (reduced to 0) field
% 1 - field with value 1 (or value reduced to 1)
% x - field to mark as safe

% ---------------------------- horizontal pattern
% case 1 :: left edge_function, horizontal pattern 1-1, mark above
% A | A | A | A | A
% A | A | A | A | x
% A | 0 | 1 | 1 | A
% A | A | A | A | A
% A | A | A | A | A
is_11_pattern(B55, -1, 0, 5, 2) :- 
			board_edge(B55, 3, 3, -1, 0),
			helpers:field_value(B55, 3, 3, Value33),
			helpers:field_value(B55, 4, 3, Value43),
			helpers:value_eq(Value33, 1), helpers:value_eq(Value43, 1),			
			helpers:field_value(B55, 5, 2, Value52),
			helpers:virgin_field(Value52).

% case 2 :: right edge_function, horizontal pattern 1-1, mark above
% A | A | A | A | A
% x | A | A | A | A
% A | 1 | 1 | 0 | A
% A | A | A | A | A
% A | A | A | A | A
is_11_pattern(B55, 1, 0, 1, 2) :-
			board_edge(B55, 3, 3, 1, 0),
			helpers:field_value(B55, 3, 3, Value33),
			helpers:field_value(B55, 2, 3, Value23),
			helpers:value_eq(Value33, 1), helpers:value_eq(Value23, 1),
			helpers:field_value(B55, 1, 2, Value12),
			helpers:virgin_field(Value12).

% case 3 :: left edge_function, horizontal pattern 1-1, mark below
% A | 0 | A | A | A
% A | 0 | A | A | A
% A | 0 | 1 | 1 | A
% A | 0 | A | A | x
% A | 0 | A | A | A
is_11_pattern(B55, -1, 0, 5, 4) :- 
			board_edge(B55, 3, 3, -1, 0),
			helpers:field_value(B55, 3, 3, Value33),
			helpers:field_value(B55, 4, 3, Value43),
			helpers:value_eq(Value33, 1), helpers:value_eq(Value43, 1),			
			helpers:field_value(B55, 5, 4, Value54),
			helpers:virgin_field(Value54).

% case 4 :: right edge_function, horizontal pattern 1-1, mark below
% A | A | A | A | A
% A | A | A | A | A
% A | 1 | 1 | 0 | A
% x | A | A | A | A
% A | A | A | A | A
is_11_pattern(B55, 1, 0, 1, 4) :-
			board_edge(B55, 3, 3, 1, 0),
			helpers:field_value(B55, 3, 3, Value33),
			helpers:field_value(B55, 2, 3, Value23),
			helpers:value_eq(Value33, 1), helpers:value_eq(Value23, 1),
			helpers:field_value(B55, 1, 4, Value14),
			helpers:virgin_field(Value14).

% ---------------------------- vertical pattern

% case 5 :: above edge_function, vertical pattern 1-1, mark left
% A | A | A | A | A
% A | A | 0 | A | A
% A | A | 1 | A | A
% A | A | 1 | A | A
% A | x | A | A | A
is_11_pattern(B55, 0, -1, 2, 5) :-
			board_edge(B55, 3, 3, 0, -1),
			helpers:field_value(B55, 3, 3, Value33),
			helpers:field_value(B55, 3, 4, Value34),
			helpers:value_eq(Value33, 1), helpers:value_eq(Value34, 1),
			helpers:field_value(B55, 2, 5, Value25),
			helpers:virgin_field(Value25).

% case 6 :: above edge_function, vertical pattern 1-1, mark right
% A | A | A | A | A
% A | A | 0 | A | A
% A | A | 1 | A | A
% A | A | 1 | A | A
% A | A | A | x | A
is_11_pattern(B55, 0, -1, 4, 5) :-
			board_edge(B55, 3, 3, 0, -1),
			helpers:field_value(B55, 3, 3, Value33),
			helpers:field_value(B55, 3, 4, Value34),
			helpers:value_eq(Value33, 1), helpers:value_eq(Value34, 1),
			helpers:field_value(B55, 4, 5, Value45),
			helpers:virgin_field(Value45).
			
% case 7 :: below edge_function, vertical pattern 1-1, mark left
% A | x | A | A | A
% A | A | 1 | A | A
% A | A | 1 | A | A
% A | A | 0 | A | A
% A | A | A | A | A
is_11_pattern(B55, 0, 1, 2, 1) :-
			board_edge(B55, 3, 3, 0, 1),
			helpers:field_value(B55, 3, 3, Value33),
			helpers:field_value(B55, 3, 2, Value32),
			helpers:value_eq(Value33, 1), helpers:value_eq(Value32, 1),
			helpers:field_value(B55, 2, 1, Value21),
			helpers:virgin_field(Value21).

% case 8 :: below edge_function, vertical pattern 1-1, mark right
% A | A | A | x | A
% A | A | 1 | A | A
% A | A | 1 | A | A
% A | A | 0 | A | A
% A | A | A | A | A
is_11_pattern(B55, 0, 1, 4, 1) :-
			board_edge(B55, 3, 3, 0, 1),
			helpers:field_value(B55, 3, 3, Value33),
			helpers:field_value(B55, 3, 2, Value32),
			helpers:value_eq(Value33, 1), helpers:value_eq(Value32, 1),
			helpers:field_value(B55, 4, 1, Value41),
			helpers:virgin_field(Value41).