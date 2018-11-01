% Jack Pfeiffer
% Assignment 6
% Part 3

% 1. Alice was either the first or the last suspect questioned.
%% 2. Joan was the third suspect questioned.
% 3. Sam was neither the first nor the second suspect questioned.
%% 4. Nancy was questioned before Sam and after Oscar.
%% 5. Edward was questioned before Joan and after another woman 

% Suspects:
% - Alice  F
% - Joan   F
% - Sam    M
% - Nancy  F
% - Edward M
% - Oscar  M



zebra :-
    suspects(Suspects),
    
    (Suspects = ([suspect("Alice", _) | _]) ; Suspects = ([_ | suspect("Alice", _)])),
    
%    member(suspect("Alice", female), Suspects),
%    member(suspect("Joan", female), Suspects),
%    member(suspect("Sam", male), Suspects),
%    member(suspect("Nancy", female), Suspects),
%    member(suspect("Edward", male), Suspects),
%    member(suspect("Oscar", male), Suspects),
    
%    before(suspect("Edward", male), suspect("Joan", female), Suspects),
%    after(suspect("Edward", male), suspect(_, female), Suspects),
    
%    before(suspect("Nancy", female), suspect("Sam", male), Suspects),
%    after(suspect("Nancy", female), suspect("Oscar", male), Suspects),
    
    print_suspects(Suspects).
    
suspects([
        suspect(_, _),
        suspect(_, _),
        suspect("Joan", female),
        suspect(_, _),
        suspect(_, _),
        suspect(_, _)
]).

after(A, B, [B, A | _]).
after(A, B, [_ | Y]) :- after(A, B, Y).

before(A, B, [A, B | _]).
before(A, B, [_ | Y]) :- before(A, B, Y).

print_suspects([A|B]) :- !,
        write(A), nl,
        print_suspects(B).
print_suspects([]).

:- initialization(zebra).