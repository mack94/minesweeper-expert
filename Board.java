import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel
{
    private final int NUM_IMAGES = 13;
    private final int CELL_SIZE = 15;

    public final static int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;

    private static int[] field;
    private static boolean inGame;
    private int mines_left;
    private Image[] img;
    private int mines;
    private static int rows;
    private static int cols;
    private int all_cells;
    private JLabel statusbar;

    private String mineStr = "Mines left: ";

    private static int difficulty;
    private static boolean solved = false;

    //Constructor
    public Board(JLabel statusbar, int noOfMines, int noOfRows, int noOfCols)
    {
        //Set the values of the member variables as determined by the MineFrame class
        this.statusbar = statusbar;
        mines = noOfMines;
        rows = noOfRows;
        cols = noOfCols;

        //Declare image array
        img = new Image[NUM_IMAGES];

        //Load images into img
        for (int i = 0; i < NUM_IMAGES; i++)
        {
            img[i] = (new ImageIcon(this.getClass().getResource((i) + ".png"))).getImage();
        }

        setDoubleBuffered(true);

        addMouseListener(new MinesAdapter());
        newGame();
    }

    // set solved (mutator/setter)
    public static void setSolved(boolean newState)
    {
        solved = newState;
    }

    // set inGame (mutator/setter)
    public static void setInGame(boolean newState)
    {
        inGame = newState;
    }

    //set difficulty (mutator/setter)
    public static void setDifficulty(int newDifficulty)
    {
        difficulty = newDifficulty;
    }

    //Gets the field and returns it (getter)
    public static int[] getField()
    {
        return field;
    }

    //Sets the field with a new array (mutator/setter)
    public static void setField(int[] arr)
    {
        field = arr;
    }

    // Push the array 'field' into the undoStack
    public static void pushFieldToUndoStack()
    {
        //Push the array 'field' into the undoStack
        MineFrame.undoStack.push(field.clone());
    }

    // create a new game
    public void newGame()
    {
        Random random;
        int current_col;

        int i = 0;
        int position = 0;
        int cell = 0;

        random = new Random();
        inGame = true;
        mines_left = mines;

        //Assign the amount of cells there are to all_cells
        all_cells = rows * cols;

        //Assign 'field' the size of all_cells
        field = new int[all_cells];

        //Assign cover cell image to all cells on the board
        for (i = 0; i < all_cells; i++)
        {
            field[i] = COVER_FOR_CELL;
        }

        //Set the text for the status bar
        statusbar.setText(mineStr + Integer.toString(mines_left));

        //Reset i to 0
        i = 0;
        while (i < mines)
        {
            //Select a random cell on the board and place a mine in it
            position = (int) (all_cells * random.nextDouble());

            if ((position < all_cells) && (field[position] != COVERED_MINE_CELL))
            {

                current_col = position % cols;
                field[position] = COVERED_MINE_CELL;
                i++;

                if (current_col > 0)
                {
                    cell = position - 1 - cols;
                    if (cell >= 0)
                        if (field[cell] != COVERED_MINE_CELL)
                        {
                            field[cell] += 1;
                        }

                    cell = position - 1;

                    if (cell >= 0)
                        if (field[cell] != COVERED_MINE_CELL)
                        {
                            field[cell] += 1;
                        }

                    cell = position + cols - 1;

                    if (cell < all_cells)
                        if (field[cell] != COVERED_MINE_CELL)
                        {
                            field[cell] += 1;
                        }
                }

                cell = position - cols;
                if (cell >= 0)
                    if (field[cell] != COVERED_MINE_CELL)
                    {
                        field[cell] += 1;
                    }

                cell = position + cols;

                if (cell < all_cells)
                    if (field[cell] != COVERED_MINE_CELL)
                    {
                        field[cell] += 1;
                    }

                if (current_col < (cols - 1))
                {
                    cell = position - cols + 1;

                    if (cell >= 0)
                        if (field[cell] != COVERED_MINE_CELL)
                        {
                            field[cell] += 1;
                        }

                    cell = position + cols + 1;

                    if (cell < all_cells)
                        if (field[cell] != COVERED_MINE_CELL)
                        {
                            field[cell] += 1;
                        }

                    cell = position + 1;

                    if (cell < all_cells)
                        if (field[cell] != COVERED_MINE_CELL)
                        {
                            field[cell] += 1;
                        }
                }
            }
        }
        // save first undo to stack
        pushFieldToUndoStack();
    }

    // search & uncover cell when there isn't a mine around it
    public void find_empty_cells(int j)
    {

        int current_col = j % cols;
        int cell;

        if (current_col > 0)
        {
            cell = j - cols - 1;
            if (cell >= 0)
                if (field[cell] > MINE_CELL)
                {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                    {
                        find_empty_cells(cell);
                    }
                }

            cell = j - 1;
            if (cell >= 0)
                if (field[cell] > MINE_CELL)
                {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                    {
                        find_empty_cells(cell);
                    }
                }

            cell = j + cols - 1;
            if (cell < all_cells)
                if (field[cell] > MINE_CELL)
                {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                    {
                        find_empty_cells(cell);
                    }
                }
        }

        cell = j - cols;
        if (cell >= 0)
            if (field[cell] > MINE_CELL)
            {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL)
                {
                    find_empty_cells(cell);
                }
            }

        cell = j + cols;
        if (cell < all_cells)
            if (field[cell] > MINE_CELL)
            {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL)
                {
                    find_empty_cells(cell);
                }
            }

        if (current_col < (cols - 1))
        {
            cell = j - cols + 1;
            if (cell >= 0)
                if (field[cell] > MINE_CELL)
                {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                    {
                        find_empty_cells(cell);
                    }
                }

            cell = j + cols + 1;
            if (cell < all_cells)
                if (field[cell] > MINE_CELL)
                {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                    {
                        find_empty_cells(cell);
                    }
                }

            cell = j + 1;
            if (cell < all_cells)
                if (field[cell] > MINE_CELL)
                {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                    {
                        find_empty_cells(cell);
                    }
                }
        }
    }

    @Override
    public void paint(Graphics g)
    {

        int cell = 0;
        int uncover = 0;

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {

                cell = field[(i * cols) + j];

                if (inGame && cell == MINE_CELL)
                {
                    inGame = false;
                }

                //Paint mines corresponding to the images
                if (!inGame)
                {
                    if (cell == COVERED_MINE_CELL)
                    {
                        cell = DRAW_MINE;
                    }
                    else if (cell == MARKED_MINE_CELL)
                    {
                        cell = DRAW_MARK;
                    }
                    else if (cell > COVERED_MINE_CELL)
                    {
                        cell = DRAW_WRONG_MARK;
                    }
                    else if (cell > MINE_CELL)
                    {
                        cell = DRAW_COVER;
                    }

                }
                else
                {
                    if (cell > COVERED_MINE_CELL)
                    {
                        cell = DRAW_MARK;
                    }
                    else if (cell > MINE_CELL)
                    {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }
                g.drawImage(img[cell], (j * CELL_SIZE), (i * CELL_SIZE), this);
            }
        }

        if (uncover == 0 && inGame && !solved)
        {
            inGame = false;
            statusbar.setText("Game won");
            new SaveUser(difficulty);
        }
        else if (uncover == -1 && !inGame && solved)
        {
            statusbar.setText("Solved");
        }
        else if (!inGame)
        {
            statusbar.setText("Game lost");
        }
    }

//Click event when user clicked a field
    class MinesAdapter extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean rep = false;

            if (!inGame)
            {
            	MineFrame.startNewGame();
            }

            if ((x < cols * CELL_SIZE) && (y < rows * CELL_SIZE) && MineFrame.playingGame)
            {

                //Rightmouse button - set flag and update statusbar
                if (e.getButton() == MouseEvent.BUTTON3)
                {

                    if (field[(cRow * cols) + cCol] > MINE_CELL)
                    {
                        rep = true;

                        if (field[(cRow * cols) + cCol] <= COVERED_MINE_CELL)
                        {
                            if (mines_left > 0)
                            {
                                field[(cRow * cols) + cCol] += MARK_FOR_CELL;
                                mines_left--;
                                statusbar.setText(mineStr + Integer.toString(mines_left));
                            }
                            else
                            {
                                statusbar.setText("No marks left");
                            }
                        }
                        else
                        {
                            field[(cRow * cols) + cCol] -= MARK_FOR_CELL;
                            mines_left++;
                            statusbar.setText(mineStr + Integer.toString(mines_left));
                        }
                    }

                }
                else
                {
                    //Nothing happens when we click on a marked cell
                    if (field[(cRow * cols) + cCol] > COVERED_MINE_CELL)
                        return;

                    if ((field[(cRow * cols) + cCol] > MINE_CELL) && (field[(cRow * cols) + cCol] < MARKED_MINE_CELL))
                    {

                        field[(cRow * cols) + cCol] -= COVER_FOR_CELL;
                        rep = true;

                        if (field[(cRow * cols) + cCol] == MINE_CELL)
                        {
                            inGame = false;
                        }
                        if (field[(cRow * cols) + cCol] == EMPTY_CELL)
                        {
                            find_empty_cells((cRow * cols) + cCol);
                        }
                    }
                }

                if (rep)
                {
                    repaint();
                    pushFieldToUndoStack();
                }
            }
        }
    }
}
