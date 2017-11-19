:- use_module('basic_patterns').
:- use_module('../helpers').

%LEGEND
%A - any field
%X - any field except virgin field
%m - mine field(virgin on map - not yet discovered)

%check if there are exactly 3 virgins adjecent to number 2 on board(given by its coords)
virgin_count_3(B55, Coord2X, Coord2Y) :-
    helpers:cut3x3(B55, Coord2X, Coord2Y, B33),
    basic_patterns:count_virgins_2D(B33, VirginCount),
    helpers:value_eq(VirginCount, 3).

% A | A | A | A | A
% A | A | A | A | A
%-1 |-1 | m | A | A
% 1 | 2 | X | A | A
% X | X | X | A | A
is_12_pattern(B55) :-
    helpers:field_value(B55, 2, 4, 2),
    virgin_count_3(B55, 2, 4),
    helpers:is_virgin(B55, 3, 3),
    helpers:is_virgin(B55, 2, 3),
    helpers:is_virgin(B55, 1, 3),
    helpers:field_value(B55, 1, 4, 1),
    !.

% X | X | X | A | A
% 1 | 2 | X | A | A
%-1 |-1 | m | A | A
% A | A | A | A | A
% A | A | A | A | A
is_12_pattern(B55) :-
    helpers:field_value(B55, 2, 2, 2),
    virgin_count_3(B55, 2, 2),
    helpers:is_virgin(B55, 3, 3),
    helpers:is_virgin(B55, 2, 3),
    helpers:is_virgin(B55, 1, 3),
    helpers:field_value(B55, 1, 2, 1),
    !.

% X | 1 |-1 | A | A
% X | 2 |-1 | A | A
% X | X | m | A | A
% A | A | A | A | A
% A | A | A | A | A
is_12_pattern(B55) :-
    helpers:field_value(B55, 2, 2, 2),
    virgin_count_3(B55, 2, 2),
    helpers:is_virgin(B55, 3, 3),
    helpers:is_virgin(B55, 3, 2),
    helpers:is_virgin(B55, 3, 1),
    helpers:field_value(B55, 2, 1, 1),
    !.

% A | A |-1 | 1 | X
% A | A |-1 | 2 | X
% A | A | m | X | X
% A | A | A | A | A
% A | A | A | A | A
is_12_pattern(B55) :-
    helpers:field_value(B55, 4, 2, 2),
    virgin_count_3(B55, 4, 2),
    helpers:is_virgin(B55, 3, 3),
    helpers:is_virgin(B55, 3, 2),
    helpers:is_virgin(B55, 3, 1),
    helpers:field_value(B55, 4, 1, 1),
    !.

% A | A | X | X | X
% A | A | X | 2 | 1
% A | A | m |-1 |-1
% A | A | A | A | A
% A | A | A | A | A
is_12_pattern(B55) :-
    helpers:field_value(B55, 4, 2, 2),
    virgin_count_3(B55, 4, 2),
    helpers:is_virgin(B55, 3, 3),
    helpers:is_virgin(B55, 4, 3),
    helpers:is_virgin(B55, 5, 3),
    helpers:field_value(B55, 5, 2, 1),
    !.

% A | A | A | A | A
% A | A | A | A | A
% A | A | m |-1 |-1
% A | A | X | 2 | 1
% A | A | X | X | X
is_12_pattern(B55) :-
    helpers:field_value(B55, 4, 4, 2),
    virgin_count_3(B55, 4, 4),
    helpers:is_virgin(B55, 3, 3),
    helpers:is_virgin(B55, 4, 3),
    helpers:is_virgin(B55, 5, 3),
    helpers:field_value(B55, 5, 4, 1),
    !.

% A | A | A | A | A
% A | A | A | A | A
% A | A | m | X | X
% A | A |-1 | 2 | X
% A | A |-1 | 1 | X
is_12_pattern(B55) :-
    helpers:field_value(B55, 4, 4, 2),
    virgin_count_3(B55, 4, 4),
    helpers:is_virgin(B55, 3, 3),
    helpers:is_virgin(B55, 3, 4),
    helpers:is_virgin(B55, 3, 5),
    helpers:field_value(B55, 4, 5, 1),
    !.

% A | A | A | A | A
% A | A | A | A | A
% X | X | m | A | A
% X | 2 |-1 | A | A
% X | 1 |-1 | A | A
is_12_pattern(B55) :-
    helpers:field_value(B55, 2, 4, 2),
    virgin_count_3(B55, 2, 4),
    helpers:is_virgin(B55, 3, 3),
    helpers:is_virgin(B55, 3, 4),
    helpers:is_virgin(B55, 3, 5),
    helpers:field_value(B55, 2, 5, 1),
    !.