import prolog.Solver;
import prolog.model.FieldState;
import prolog.model.Input;
import prolog.model.Solution;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {
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
    private final int SOLVED_MINE = 99;

    private Set<Integer> special_field_values =
            new HashSet<>(Arrays.asList(COVER_FOR_CELL, MARK_FOR_CELL, EMPTY_CELL, MINE_CELL,
                    COVERED_MINE_CELL, MARKED_MINE_CELL, DRAW_MINE, DRAW_COVER, DRAW_MARK, DRAW_WRONG_MARK, SOLVED_MINE));

    private static int[] field;
    private static boolean inGame;
    private int mines_left;
    private Image[] img;
    private int mines;
    private static int rows;
    private static int cols;
    private int all_cells;
    private JLabel statusbar;

    private static int difficulty;
    private static boolean solved = false;

    private Solver solver;
    private FieldState[] display_solved_fields;


    //Constructor
    public Board(JLabel statusbar, int noOfMines, int noOfRows, int noOfCols) {
        //Set the values of the member variables as determined by the MineFrame class
        this.statusbar = statusbar;
        mines = noOfMines;
        rows = noOfRows;
        cols = noOfCols;
        solver = new Solver(cols, rows);

        //Declare image array
        img = new Image[NUM_IMAGES];

        //Load images into img
        for (int i = 0; i < NUM_IMAGES; i++) {
            img[i] = (new ImageIcon(this.getClass().getResource((i) + ".png"))).getImage();
        }

        setDoubleBuffered(true);

        addMouseListener(new MinesAdapter());
        newGame();
    }

    // set solved (mutator/setter)
    public static void setSolved(boolean newState) {
        solved = newState;
    }

    // set inGame (mutator/setter)
    public static void setInGame(boolean newState) {
        inGame = newState;
    }

    //set difficulty (mutator/setter)
    public static void setDifficulty(int newDifficulty) {
        difficulty = newDifficulty;
    }

    //Gets the field and returns it (getter)
    public static int[] getField() {
        return field;
    }

    //Sets the field with a new array (mutator/setter)
    public static void setField(int[] arr) {
        field = arr;
    }

    // Push the array 'field' into the undoStack
    public static void pushFieldToUndoStack() {
        //Push the array 'field' into the undoStack
        MineFrame.undoStack.push(field.clone());
    }

    // create a new game
    public void newGame() {
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
        display_solved_fields = new FieldState[all_cells];


        //Assign cover cell image to all cells on the board
        for (i = 0; i < all_cells; i++) {
            field[i] = COVER_FOR_CELL;
        }

        //Reset i to 0
        i = 0;
        while (i < mines) {
            //Select a random cell on the board and place a mine in it
            position = (int) (all_cells * random.nextDouble());

            if ((position < all_cells) && (field[position] != COVERED_MINE_CELL)) {

                current_col = position % cols;
                field[position] = COVERED_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - cols;
                    if (cell >= 0)
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }

                    cell = position - 1;

                    if (cell >= 0)
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }

                    cell = position + cols - 1;

                    if (cell < all_cells)
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                }

                cell = position - cols;
                if (cell >= 0)
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }

                cell = position + cols;

                if (cell < all_cells)
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }

                if (current_col < (cols - 1)) {
                    cell = position - cols + 1;

                    if (cell >= 0)
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }

                    cell = position + cols + 1;

                    if (cell < all_cells)
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }

                    cell = position + 1;

                    if (cell < all_cells)
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                }
            }
        }
        // save first undo to stack
        pushFieldToUndoStack();
    }

    // search & uncover cell when there isn't a mine around it
    public void find_empty_cells(int j) {

        int current_col = j % cols;
        int cell;

        if (current_col > 0) {
            cell = j - cols - 1;
            if (cell >= 0)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }

            cell = j - 1;
            if (cell >= 0)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }

            cell = j + cols - 1;
            if (cell < all_cells)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
        }

        cell = j - cols;
        if (cell >= 0)
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }

        cell = j + cols;
        if (cell < all_cells)
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL) {
                    find_empty_cells(cell);
                }
            }

        if (current_col < (cols - 1)) {
            cell = j - cols + 1;
            if (cell >= 0)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }

            cell = j + cols + 1;
            if (cell < all_cells)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }

            cell = j + 1;
            if (cell < all_cells)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL) {
                        find_empty_cells(cell);
                    }
                }
        }
    }

    @Override
    public void paint(Graphics g) {

        FieldState fieldStateSolver;
        int cell = 0;
        int uncover = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                display_solved_fields[(i * cols) + j] = FieldState.UNKNOWN;
            }
        }

        int[] reduced = new int[field.length];
        System.arraycopy(field, 0, reduced, 0, field.length);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                cell = field[(i * cols) + j];
                fieldStateSolver = display_solved_fields[(i * cols) + j];

                if (fieldStateSolver.equals(FieldState.UNKNOWN)) {
                    int currentX = i;
                    int currentY = j;

                    java.util.List<Solution> solutionList = solver.setInputFromArray(field, currentX, currentY);
                    if (solutionList != null) {
                        solutionList.forEach(solution -> {
                            display_solved_fields[((currentX + solution.getX() - 3) * cols) + (currentY + solution.getY() - 3)] = solution.getFieldState();
                        });
                    }
                    reduced = reduceField(reduced, i, j);
                    solutionList = solver.setInputFromArray(reduced, currentX, currentY);
                    if (solutionList != null) {
                        solutionList.forEach(solution -> {
                            {
                                if (display_solved_fields[((currentX + solution.getX() - 3) * cols) + (currentY + solution.getY() - 3)] == FieldState.UNKNOWN) {
                                    display_solved_fields[((currentX + solution.getX() - 3) * cols) + (currentY + solution.getY() - 3)] = solution.getFieldState();
                                }
                            }
                        });
                    }


                }
            }
        }
        System.out.println("================================================================");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                cell = field[(i * cols) + j];

                if (inGame && cell == MINE_CELL) {
                    inGame = false;
                }

                //Paint mines corresponding to the images
                if (!inGame) {
                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_WRONG_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                    }

                } else {
                    if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }
                Image displayed_image = img[cell];
                if (display_solved_fields[(i * cols) + j].equals(FieldState.MINE)) {
                    field[(i * cols) + j] = SOLVED_MINE;
                    BufferedImage b_img = new BufferedImage(CELL_SIZE, CELL_SIZE, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = b_img.createGraphics();

                    graphics.setPaint(new Color(191, 0, 0));
                    graphics.fillRect(0, 0, b_img.getWidth(), b_img.getHeight());
                    displayed_image = b_img;
//                    update_left_mines(-1);
                }
                if (display_solved_fields[(i * cols) + j].equals(FieldState.SAFE)) {
                    BufferedImage b_img = new BufferedImage(CELL_SIZE, CELL_SIZE, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = b_img.createGraphics();

                    graphics.setPaint(new Color(8, 144, 0));
                    graphics.fillRect(0, 0, b_img.getWidth(), b_img.getHeight());
                    displayed_image = b_img;
                }

                g.drawImage(displayed_image, (j * CELL_SIZE), (i * CELL_SIZE), this);
                //System.out.println(String.format("Field: %d %d Value: %s", j, i, cell));
            }
        }

        if (uncover == 0 && inGame && !solved) {
            inGame = false;
            statusbar.setText("Game won");
            new SaveUser(difficulty);
        } else if (uncover == -1 && !inGame && solved) {
            statusbar.setText("Solved");
        } else if (!inGame) {
            statusbar.setText("Game lost");
        }
    }

    private void update_left_mines(int di) {
        mines_left = mines_left + di;
    }

    private int[] reduceField(int[] field, int currentX, int currentY) {
//        check if checked cell contains number of surrounding mines
        int checkedFieldValue = solver.getSolverInput().getValueFromArray(field, currentX, currentY);
        if (display_solved_fields[currentX * cols + currentY] == FieldState.MINE ||
                checkedFieldValue > 8) return field;

        List<FieldState> roi_region = new ArrayList<>();
        for (int x = Math.max(currentX - 1, 0); x <= Math.min(currentX + 1, cols - 1); x++) {
            for (int y = Math.max(currentY - 1, 0); y <= Math.min(currentY + 1, rows - 1); y++) {
                roi_region.add(display_solved_fields[x * cols + y]);
            }
        }

        long near_mines = roi_region.stream().filter(e -> e.equals(FieldState.MINE)).count();

        System.out.println(String.format("Checking: X: %s Y: %s", currentX, currentY));
        System.out.println("ROI: " + Arrays.toString(roi_region.toArray()) + "Num before: " + field[currentX * cols + currentY]);
        field[currentX * cols + currentY] -= near_mines;
        System.out.println("Num after: " + field[currentX * cols + currentY]);
        return field;
    }

    //Click event when user clicked a field
    class MinesAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean rep = false;

            if (!inGame) {
                MineFrame.startNewGame();
            }

            if ((x < cols * CELL_SIZE) && (y < rows * CELL_SIZE) && MineFrame.playingGame) {

                //Rightmouse button - set flag and update statusbar
                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (field[(cRow * cols) + cCol] > MINE_CELL) {
                        rep = true;

                        if (field[(cRow * cols) + cCol] <= COVERED_MINE_CELL) {
                            if (mines_left > 0) {
                                field[(cRow * cols) + cCol] += MARK_FOR_CELL;
                                update_left_mines(-1);
                            } else {
                                statusbar.setText("No marks left");
                            }
                        } else {
                            field[(cRow * cols) + cCol] -= MARK_FOR_CELL;
                            update_left_mines(+1);
                        }
                    }

                } else {
                    //Nothing happens when we click on a marked cell
                    if (field[(cRow * cols) + cCol] > COVERED_MINE_CELL)
                        return;

                    if ((field[(cRow * cols) + cCol] > MINE_CELL) && (field[(cRow * cols) + cCol] < MARKED_MINE_CELL)) {

                        field[(cRow * cols) + cCol] -= COVER_FOR_CELL;
                        rep = true;

                        if (field[(cRow * cols) + cCol] == MINE_CELL) {
                            inGame = false;
                        }
                        if (field[(cRow * cols) + cCol] == EMPTY_CELL) {
                            find_empty_cells((cRow * cols) + cCol);
                        }
                    }
                }

                if (rep) {
                    repaint();
                    pushFieldToUndoStack();

                }
            }
        }
    }
}
