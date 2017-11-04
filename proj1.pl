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

%% -------------------------- Helpful rules 

% check's if this field was already uncovered/not clicked 
virgin_field(X) :- X is 0.
%check's if the whole list (row or column) is uncovered/not clicked
is_empty_list([]).
is_empty_list([X|Y]) :- virgin_field(X), is_empty_list(Y).
%check's if the whole board is uncovered/not clicked
is_empty([]).
is_empty([BH|BT]) :- is_empty_list(BH), is_empty(BT).

%% -------------------------- Game rules application
%
% first case : newly started game
% case result: all movements are proper 
all_moves_available(B) :- is_empty(B).

