:- module(helpers, []).

% Expert system responsible for predicting proper movement to win the minesweeper game. 
% System written as a project for Expert Systems classes on IET department of AGH University of Science and Technology 
% 
% 
% Authors: 
% * Maciej Makowka
% * Krzysztof Wegrzynski
% * Michal Zygmunt
% 
% (C) 11.2017

%% -------------------------- Explanations
% B - board
% M - mine

next_move(B, Bx, By).

%% -------------------------- Settings
% number of neighbouring mines
zero.
one.
two.
three.
four.
five.
six.
seven.
eight.
sure_mine.

%% -------------------------- Helpful rules 

% check's if this field was already uncovered/not clicked 
virgin_field(-1).

%check's if the whole list (row or column) is uncovered/not clicked
is_empty_list([]).
is_empty_list([X|Y]) :- virgin_field(X), is_empty_list(Y).
%check's if the whole board is uncovered/not clicked
is_empty([]).
is_empty([BH|BT]) :- is_empty_list(BH), is_empty(BT).
%return field value
%element_value(X, 1, X).
element_value([X|_], 1, X) :- !.
element_value([_|T], N0, V) :- N0 > 1, N1 is N0-1, element_value(T, N1, V).
%return whole column
get_column([BH|_], 1, BH) :- !.
get_column([_|BT], N0, Col) :- N0 > 1, N1 is N0-1, get_column(BT, N1, Col).
%return field (x,y) value
field_value(B, Bx, By, V) :- get_column(B, Bx, Col), element_value(Col, By, V).

%init result matrix, all fields start as mines
build_matrix(RowSize, ColSize, Mat) :- length(C, ColSize), length(Mat, RowSize), maplist(=(-1), C), maplist(copy_term(C), Mat).

%% -------------------------- Game rules application
%
% first case : newly started game
% case result: all movements are proper
all_moves_available(B) :- is_empty(B).

% other case:
%

% determines, whether this movement is virgin
% conditions: it is virgin field (still uncovered), ...
is_virgin(B, Bx, By) :- field_value(B, Bx, By, V), virgin_field(V).

%cutting neighbourhood 3x3 region from area, given center of 3x3
%here: TODO:
% cut3x3(Board, Cx, Cy) :- 




