import java.util.*;
//import all of the java utilities

public class MineSweeper {

    // create two 2D arrays, one for visible, and one for hidden
    private int[][] fieldVisible = new int[10][10];
    private int[][] fieldHidden = new int[10][10];

    // create the main method
    public static void main(String[] args) {
        // everytime the class is run, it will create/instantiate the game,
        // and it will create start the game.
        MineSweeper M = new MineSweeper();
        M.startGame();
    }

    public void startGame() {
        // 1. Display the entry-level game/introduction messages
        System.out.println("\n\n================ Welcome to MineSweeper! =============\n");
        // 2. Setup the minesweeper playfield
        setupField(1);
        // 3. Run the game until the player wins/loses
        boolean flag = true;
        while (flag) {
            displayVisible();
            flag = playMove();
            // 4. Display final message and break the check loop
            if (checkWin()) {
                displayHidden();
                System.out.println("\n============== You Won!!! ===============\n");
                break;
            }
        }
    }

    // sets up the mines in the play field
    public void setupField(int diff) {
        // controls that mines are set 10 times
        int var = 0;
        while (var != 10) {
            // create random variable
            Random random = new Random();
            // return random number between 0-9
            int i = random.nextInt(10);
            // return random number between 0-9
            int j = random.nextInt(10);
            // System.out.println("i: " + i + " j: " + j);
            // set the random grid item to 100
            fieldHidden[i][j] = 100;
            // increase the control by 1 until there are 10 mines
            var++;
        }
        // build hidden will build the hidden matrix
        buildHidden();
    }

    // build the hidden matrix
    // consisting of the mine proximity neighbor numbers and the mines
    public void buildHidden()
    // FINDNG BOMBS
    // We will choose each cell and count the number of bombs present in
    // all of its neighboring cells. This value will be saved in the hidden
    // matrix cell.
    {
        // iterate 9 times
        for (int i = 0; i < 10; i++) {
            // iterate 9 times
            for (int j = 0; j < 10; j++) {
                // count variable
                int cnt = 0;
                // if the fieldHidden is NOT a mine...
                if (fieldHidden[i][j] != 100) {
                    // ...and if the i is NOT 0
                    if (i != 0) {

                        if (fieldHidden[i - 1][j] == 100)
                            cnt++;
                        if (j != 0) {
                            if (fieldHidden[i - 1][j - 1] == 100)
                                cnt++;
                        }

                    }
                    // ...or NOT a 9
                    if (i != 9) {
                        if (fieldHidden[i + 1][j] == 100)
                            cnt++;
                        if (j != 9) {
                            if (fieldHidden[i + 1][j + 1] == 100)
                                cnt++;
                        }
                    }

                    // also, if j is not equal to 0 or 9
                    if (j != 0) {
                        if (fieldHidden[i][j - 1] == 100)
                            cnt++;
                        if (i != 9) {
                            if (fieldHidden[i + 1][j - 1] == 100)
                                cnt++;
                        }
                    }
                    if (j != 9) {
                        if (fieldHidden[i][j + 1] == 100)
                            cnt++;
                        if (i != 0) {
                            if (fieldHidden[i - 1][j + 1] == 100)
                                cnt++;
                        }
                    }

                    fieldHidden[i][j] = cnt;
                }
            }
        }

    }

    // display the current progress or all of the visible tiles
    public void displayVisible() {
        // print a tab
        System.out.print("\t ");
        // print 10 i's with spaces on either end
        for (int i = 0; i < 10; i++) {
            System.out.print(" " + i + " ");
        }
        System.out.print("\n");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < 10; j++) {
                if (fieldVisible[i][j] == 0) {
                    System.out.print("?");
                } else if (fieldVisible[i][j] == 50) {
                    System.out.print(" ");
                } else {
                    System.out.print(fieldVisible[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }

    // expose the selected cell and its neighbors, and if its a bomb, display error
    public boolean playMove() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Row Number: ");
        int i = sc.nextInt();
        System.out.print("Enter Column Number: ");
        int j = sc.nextInt();

        // if they select a value out of the play zone, or one that is revealed
        if (i < 0 || i > 9 || j < 0 || fieldVisible[i][j] != 0) {
            System.out.print("\nIncorrect Input!!");
            return true;
        }

        // if bomb, end game and reveal all
        if (fieldHidden[i][j] == 100) {
            displayHidden();
            System.out.print("Oops! You stepped on a mine!\n============GAME OVER============");
            return false;
        } else if (fieldHidden[i][j] == 0) {
            fixVisible(i, j);
        } else {
            fixNeighbours(i, j);
        }

        return true;
    }

    public void fixVisible(int i, int j) {
        fieldVisible[i][j] = 50;
        if (i != 0) {
            fieldVisible[i - 1][j] = fieldHidden[i - 1][j];
            if (fieldVisible[i - 1][j] == 0)
                fieldVisible[i - 1][j] = 50;
            if (j != 0) {
                fieldVisible[i - 1][j - 1] = fieldHidden[i - 1][j - 1];
                if (fieldVisible[i - 1][j - 1] == 0)
                    fieldVisible[i - 1][j - 1] = 50;

            }
        }
        if (i != 9) {
            fieldVisible[i + 1][j] = fieldHidden[i + 1][j];
            if (fieldVisible[i + 1][j] == 0)
                fieldVisible[i + 1][j] = 50;
            if (j != 9) {
                fieldVisible[i + 1][j + 1] = fieldHidden[i + 1][j + 1];
                if (fieldVisible[i + 1][j + 1] == 0)
                    fieldVisible[i + 1][j + 1] = 50;
            }
        }
        if (j != 0) {
            fieldVisible[i][j - 1] = fieldHidden[i][j - 1];
            if (fieldVisible[i][j - 1] == 0)
                fieldVisible[i][j - 1] = 50;
            if (i != 9) {
                fieldVisible[i + 1][j - 1] = fieldHidden[i + 1][j - 1];
                if (fieldVisible[i + 1][j - 1] == 0)
                    fieldVisible[i + 1][j - 1] = 50;
            }
        }
        if (j != 9) {
            fieldVisible[i][j + 1] = fieldHidden[i][j + 1];
            if (fieldVisible[i][j + 1] == 0)
                fieldVisible[i][j + 1] = 50;
            if (i != 0) {
                fieldVisible[i - 1][j + 1] = fieldHidden[i - 1][j + 1];
                if (fieldVisible[i - 1][j + 1] == 0)
                    fieldVisible[i - 1][j + 1] = 50;
            }
        }
    }

    public void fixNeighbours(int i, int j) {
        Random random = new Random();
        int x = random.nextInt() % 4;

        fieldVisible[i][j] = fieldHidden[i][j];

        if (x == 0) {
            if (i != 0) {
                if (fieldHidden[i - 1][j] != 100) {
                    fieldVisible[i - 1][j] = fieldHidden[i - 1][j];
                    if (fieldVisible[i - 1][j] == 0)
                        fieldVisible[i - 1][j] = 50;
                }
            }
            if (j != 0) {
                if (fieldHidden[i][j - 1] != 100) {
                    fieldVisible[i][j - 1] = fieldHidden[i][j - 1];
                    if (fieldVisible[i][j - 1] == 0)
                        fieldVisible[i][j - 1] = 50;
                }

            }
            if (i != 0 && j != 0) {
                if (fieldHidden[i - 1][j - 1] != 100) {
                    fieldVisible[i - 1][j - 1] = fieldHidden[i - 1][j - 1];
                    if (fieldVisible[i - 1][j - 1] == 0)
                        fieldVisible[i - 1][j - 1] = 50;
                }

            }
        } else if (x == 1) {
            if (i != 0) {
                if (fieldHidden[i - 1][j] != 100) {
                    fieldVisible[i - 1][j] = fieldHidden[i - 1][j];
                    if (fieldVisible[i - 1][j] == 0)
                        fieldVisible[i - 1][j] = 50;
                }
            }
            if (j != 9) {
                if (fieldHidden[i][j + 1] != 100) {
                    fieldVisible[i][j + 1] = fieldHidden[i][j + 1];
                    if (fieldVisible[i][j + 1] == 0)
                        fieldVisible[i][j + 1] = 50;
                }

            }
            if (i != 0 && j != 9) {
                if (fieldHidden[i - 1][j + 1] != 100) {
                    fieldVisible[i - 1][j + 1] = fieldHidden[i - 1][j + 1];
                    if (fieldVisible[i - 1][j + 1] == 0)
                        fieldVisible[i - 1][j + 1] = 50;
                }
            }
        } else if (x == 2) {
            if (i != 9) {
                if (fieldHidden[i + 1][j] != 100) {
                    fieldVisible[i + 1][j] = fieldHidden[i + 1][j];
                    if (fieldVisible[i + 1][j] == 0)
                        fieldVisible[i + 1][j] = 50;
                }
            }
            if (j != 9) {
                if (fieldHidden[i][j + 1] != 100) {
                    fieldVisible[i][j + 1] = fieldHidden[i][j + 1];
                    if (fieldVisible[i][j + 1] == 0)
                        fieldVisible[i][j + 1] = 50;
                }

            }
            if (i != 9 && j != 9) {
                if (fieldHidden[i + 1][j + 1] != 100) {
                    fieldVisible[i + 1][j + 1] = fieldHidden[i + 1][j + 1];
                    if (fieldVisible[i + 1][j + 1] == 0)
                        fieldVisible[i + 1][j + 1] = 50;
                }
            }
        } else {
            if (i != 9) {
                if (fieldHidden[i + 1][j] != 100) {
                    fieldVisible[i + 1][j] = fieldHidden[i + 1][j];
                    if (fieldVisible[i + 1][j] == 0)
                        fieldVisible[i + 1][j] = 50;
                }
            }
            if (j != 0) {
                if (fieldHidden[i][j - 1] != 100) {
                    fieldVisible[i][j - 1] = fieldHidden[i][j - 1];
                    if (fieldVisible[i][j - 1] == 0)
                        fieldVisible[i][j - 1] = 50;
                }

            }
            if (i != 9 && j != 0) {
                if (fieldHidden[i + 1][j - 1] != 100) {
                    fieldVisible[i + 1][j - 1] = fieldHidden[i + 1][j - 1];
                    if (fieldVisible[i + 1][j - 1] == 0)
                        fieldVisible[i + 1][j - 1] = 50;
                }
            }
        }
    }

    // check if the player has evaded all mines
    public boolean checkWin() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (fieldVisible[i][j] == 0) {
                    if (fieldHidden[i][j] != 100) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void displayHidden() {
        System.out.print("\t ");
        for (int i = 0; i < 10; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + "\t| ");
            for (int j = 0; j < 10; j++) {
                if (fieldHidden[i][j] == 0) {
                    System.out.print(" ");
                } else if (fieldHidden[i][j] == 100) {
                    System.out.print("X");
                } else {
                    System.out.print(fieldHidden[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }
}
