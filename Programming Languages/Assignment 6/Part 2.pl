% Jack Pfeiffer
% Assignment 6 
% Part 2

% 1. English is taught in Room 120.
% 2. The History teacher uses an overhead projector.
% 3. Ms. Smith teaches in Room 200.
% 4. The Math class meets in the hour before the class in Room 223.
% 5. The course in Room 200 uses models.
% 6. Mr. Whitherspoon uses the blackboard.
% 7. Ms. Johnson teaches the hour before Science is taught.
% 8. Room 105 holds a class at 11:00AM.
% 9. Mr. Adams teaches in Room 233.
% 10. A lecturn is used in the class at 9:00AM.

% course(Subject, Room, Teacher, Presentation).

zebra :-
    courses(Courses),
    
    member(course(english, room120, _, _), Courses),
    member(course(history, _, _, projector), Courses),
    member(course(_, room200, "Ms. Smith", _), Courses),
    member(course(_, _, "Mr. Witherspoon", blackboard), Courses),
    member(course(_, room223, "Mr. Adams", _), Courses),
    member(course(_, room200, _, models), Courses),
    
    before(course(math, _, _, _), course(_, room223, _, _), Courses),
    before(course(_, _, "Ms. Johnson", _), course(science, _, _, _), Courses),
    
    print_courses(Courses).
    
courses([
        course(_, _, _, lecture), %  9:00AM
        course(_, _, _, _),       % 10:00AM
        course(_, room105, _, _), % 11:00AM
        course(_, _, _, _)        % 12:00PM
]).

before(A, B, [A, B | _]).
before(A, B, [_ | Y]) :- before(A, B, Y).

print_courses([A|B]) :- !,
        write(A), nl,
        print_courses(B).
print_courses([]).

:- initialization(zebra).