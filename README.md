# Moving Robot


## Usage
The interface and API require that a starting instruction and at least one move/turn
are specified.

### Frontend
The leftmost column should be used to specify a sequence of instructions.
This column is divided into two sections: _Start_ and _Moves/Turns_.

To specify a starting instruction (_Start Section_):
- Select the starting direction from the dropdown labeled _Direction_
- Use the _Steps_ number field to specify the number of steps
to be taken in the specified direction

To Add Moves/Turns (_Moves/Turns Section_):
- Select the move/turn from the dropdown in the _Moves/Turns_ section
- Enter the frequency of the move in the number field labeled _Frequency_ and click
the _Add_ button. The specified instruction and its frequency will be added to
the table in the middle column. Several move/turn instructions can be added this way.

Once the desired starting position and steps have been specified, clicking on the
_Execute_ button sends the sequence to the backend. The backend returns the updated
position and direction which the frontend uses to render the position and direction
of the robot in the grid in the rightmost column. The _Reset_ button can be used to 
reset the starting position clear already specified steps.

### Requirements
The application requires Java 11 and an internet connection (as external static resources are referenced).

### Assumptions
- It is assumed that the instruction __# EAST__ means to move __# STEPS__ to the EAST.
For example: 3 EAST means move 3 steps in the direction of the EAST.
- The robot's starting position is always the cell [0,0] in the top left corner
- An execution order must include a starting instruction and at least one other instruction

