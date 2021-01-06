# for_students

Checkers implementation for terminal.

Start:
Main class to launch a terminal version. CheckerboardTest to follow 70 movements test game.

Description:
Logic is based on two long digits for each player (Light and Dark) where all information about the 12 pieces are kept… ie. state (0-eliminated, 1-in the game), figure (0-pawn, 1-dame), color (0-dark, 1-light) and coordinates.
Ex. 111000111 in bites notation stands for light’s player dame placed in H1 (x=0b000, y=0b111).

The project structure is built around following classes:

Logics: 
1.	Initialization – to create board and two players (Light and Dark) pieces
2.	Conditions – where all requirements concerning correct movements are placed (including all necessary information/messages for incorrect data)
3.	Movement – containing logic for appropriate movement (ex. different behaviour for dame or pawn movement)
4.	Listener – to track any capturing possibilities for different scenarios in checkers (ex. separate approach for dame and pawn capturing)
5.	Checkboard – to combine all the above
6.	Checkers – to introduce terminal altering play powered by Scanner

Data:
7.	Position – where all coordinates are managed
8.	OneFigure – where full info about single piece is located (ex. 0b111000111)
9.	SixFigures – where full info about six pieces are located ( ex. 0b100010001_100010011_100010101_100010111_100001000_100001010 = 9_624_268_687_937_802L)
10.	Key date are kept in three Maps (separate for board, players and capturing movement)

Utilities:
11.	 Additional utilities in utils package to keep constants (Const), all game messages (Messages) and print the game (Print)

Tests:
12.	To cover flag for correct / incorrect movements and test game with 70-ish movements which is end with Light player win (two checkers left – dame and pawn)
