== How to use MazeManager ==

Author: Nick Porillo
Site: https://github.com/nsporillo/MazeManager

MazeIO:
  New maze:
    - Click File, New
    - Enter a maze name
    - Enter number of rows
    - Enter number of columns
  Save maze:
    - Click File, Save
    - Maze will be saved under '<mazename>.maze'
  Load maze:
    - Click File, Load
    - Select a maze from the drop down list
    - Select OK, maze will replace whatever maze was loaded

Maze Editing:
  Tooltips:
    - A 'tooltip' is the current Tile you're set to be placing
    - To change a tooltip, Click Tile and select a new tooltip
    - Available tooltips are Start, Open, Wall, Finish
    - Start = The single maze start point
    - Open = An empty tile
    - Wall = A barrier tile
    - Finish = The single maze end point
  Tile setting:
    - Left click to change a tile to your tooltip tile
    - Right click to change any tile to an open tile

Maze Solving:
  Solve:
    - Click Run, Solve
    - Maze will be solved if possible, otherwise an error will be displayed
  Reset:
    - Click Run, Reset
    - All tiles except Wall, Start, and End will be set to Open (resets maze)