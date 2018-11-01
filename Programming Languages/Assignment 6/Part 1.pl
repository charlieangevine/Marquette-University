% Jack Pfeiffer 
% Assignment 6
% Part 1

% running a stop sign
% speeding
% making an illegal left turn
% running a red light

% 1. Norma did not run either a red light or a stop sign.
% 2. Andy was never stopped from speeding.
% 3. Edward made an illegal left turn.
% 4. Olivia was always careful to stop at a stop sign.

% suspect(name, stopSign, speeding, illegalLeft, runningRed).

zebra :-
    suspects(Suspects),
    
    member(suspect("norma", false, _, _, false), Suspects),
    member(suspect("andy", _, false, _, _), Suspects),
    member(suspect("edward", _, _, true, _), Suspects),
    member(suspect("olivia", false, _, _, _), Suspects),
    
    member(suspect(_, true, false, false, false), Suspects),
    member(suspect(_, false, true, false, false), Suspects),
    member(suspect(_, false, false, true, false), Suspects),
    member(suspect(_, false, false, false, true), Suspects),
    
    print_suspects(Suspects).
    
suspects([
        suspect(_, _, _, _, _),
        suspect(_, _, _, _, _),
        suspect(_, _, _, _, _),
        suspect(_, _, _, _, _)
]).

print_suspects([A|B]) :- !,
        write(A), nl,
        print_suspects(B).
print_suspects([]).

:- initialization(zebra).