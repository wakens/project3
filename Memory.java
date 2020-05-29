import java.util.concurrent.TimeUnit;
import java.awt.Component;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Memory extends JFrame
{
    // INSTANCE VARIABLES
    private static final long serialVersionUID = 1L;
    static Window w;
    static String[] FIRST;
    static String[] PLAY;
    static boolean quit;
    private Board mBoard;
    private JButton mRetryButton;
    private JButton mNewButton;
    private JSplitPane mSplitPane;
    private MouseListener btnMouseListener;
    
    // This is just a way to have all the variables inside be static.
    static {
        Memory.w = new Window();
        Memory.FIRST = new String[] { "START GAME", "HELP", "WHAT IS DANGANRONPA", "PASSWORD", "QUIT" };
        Memory.PLAY = new String[] { "START GAME", "GO BACK TO MENU", "QUIT GAME" };
        Memory.quit = false;
    }
    
    // CONSTRUCTOR
    public Memory() {
        this.btnMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 1 && e.getComponent() == Memory.this.mRetryButton) {
                    Memory.this.mBoard.reInit();
                }
                else if (e.getClickCount() == 1 && e.getComponent() == Memory.this.mNewButton) {
                    Memory.this.mBoard.init();
                }
            }
        };
        this.setDefaultCloseOperation(3);
        this.setBackground(Color.WHITE);
        this.add(this.mBoard = new Board(), "Center");
        this.add(this.mSplitPane = new JSplitPane(), "South");
        (this.mRetryButton = new JButton("Retry")).setFocusPainted(false);
        this.mRetryButton.addMouseListener(this.btnMouseListener);
        this.mSplitPane.setLeftComponent(this.mRetryButton);
        (this.mNewButton = new JButton("New Game")).setFocusPainted(false);
        this.mNewButton.addMouseListener(this.btnMouseListener);
        this.mSplitPane.setRightComponent(this.mNewButton);
        this.pack();
        this.setResizable(true);
        this.setVisible(true);
    }
    
    /*
     * This method displays the first question the user is asked and returns
     * their input, it is used in the main method. 
     */
    public static int choice1() {
        final int choice1 = Memory.w.option(Memory.FIRST, "Welcome to Amanda's Danganronpa memory game!\n To start the game, choose start game. \n If you don't know how to play memory choose help. \n If you don't know about danganronpa, choose what is danganronpa.\n If you have a special password input it in passwords, you can find passwords through clues on the \"What Is Danganronpa\" tab.\n If you'd like to quit the game, choose quit.");
        return choice1;
    }
    
    /*
     * This method, main, goes through the memory game.
     * First, it introduces the game and allows the user to choose between 4 options:
     * START GAME: Begins the memory game.
     * HELP: Further explains the game for those that are unfamiliar with memory and how to play it.
     * WHAT IS DANGANRONPA: Explains what Danganronpa is to the user, as well as gives a hint to what the passwords might be.
     * PASSWORD: Allows the user to input a password 'hope' or 'despair' that either win the game for them or cause
     *           them to lose. 
     * After starting the game and completing it you get the amount of times you failed to match, as well as a percentage
     * to motivate the player into doing the game again and doing better than they did before.
     */
    public static void main(final String[] args) throws InterruptedException {
        int choice1 = choice1();
        while (!Memory.quit) {
            if (choice1 == 0) {
                Memory.w.msg("You are forced to find pairs of students to help them escape! \n If you fail to match them all, the remaining students will be left to die!\n If only you had some sort of password to make this easier. . .");
                new Memory();
                Memory.quit = true;
            }
            else if (choice1 == 1) {
                final int choice2 = Memory.w.option(Memory.PLAY, "Memory is very simple, when the game starts the screen will flash with the entire board of images.\n Then you will select an image and another to see if it is a pair. If it is, the card will no longer be selectable.\n Your goal is to match all the pairs using your memory, since they will be turned over unless if you click them.\n Good luck. . .");
                if (choice2 == 0) {
                    choice1 = 1;
                }
                else if (choice2 == 1) {
                    choice1 = choice1();
                }
                else {
                    if (choice2 != 2) {
                        continue;
                    }
                    Memory.quit = true;
                }
            }
            else if (choice1 == 2) {
                final int choice2 = Memory.w.option(Memory.PLAY, "Danganronpa is a video game series in which 16 students wake up inside of a school where they are told they must kill\n each other and get away with it to survive. The headmaster is aiming to bring the students to \ud835\udced\ud835\udcee\ud835\udcfc\ud835\udcf9\ud835\udcea\ud835\udcf2\ud835\udcfb by having\n class trials after each murder where if the murderer gets away with it, the rest of the students die. The only\n way for the student to escape is if they eventually find the mastermind behind the game and defeat them, thus creating \ud835\udcf1\ud835\udcf8\ud835\udcf9\ud835\udcee. ");
                if (choice2 == 0) {
                    choice1 = 1;
                }
                else if (choice2 == 1) {
                    choice1 = choice1();
                }
                else {
                    if (choice2 != 2) {
                        continue;
                    }
                    Memory.quit = true;
                }
            }
            else if (choice1 == 3) {
                final String choice3 = Memory.w.in("Input the password.");
                if (choice3 == null) {
                    final int choice4 = Memory.w.option(Memory.PLAY, "If you didn't want to input a password you shouldn't have chosen it!");
                    if (choice4 == 0) {
                        choice1 = 1;
                    }
                    else if (choice4 == 1) {
                        choice1 = choice1();
                    }
                    else {
                        if (choice4 != 2) {
                            continue;
                        }
                        Memory.quit = true;
                    }
                }
                else {
                    if (choice3.equalsIgnoreCase("hope")) {
                        Memory.w.msg("After inputing the password, the walls around you fall, revealing an island. You're no longer in the school. . .\n . . . but did you escape?");
                        Memory.w.msg("You see the rest of the students who seem to have been let out as well. Good job! You saved everyone! The game ends here.");
                        break;
                    }
                    if (choice3.equalsIgnoreCase("despair")) {
                        Memory.w.msg("After inputing the password, a laugh is heard. The room you're in goes black. . .");
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        new JWindow();
                        Memory.w.msg("You must wait 15 seconds before entering any commands. I'd suggest closing the many tabs you just opened. . .");
                        TimeUnit.SECONDS.sleep(15L);
                        Memory.w.msg("You lost the game, succumbing to despair. . . Play again ?");
                        choice1 = choice1();
                    }
                    else {
                        final int choice4 = Memory.w.option(Memory.PLAY, "ERROR ERROR ERROR\n You got the password incorrect. Come back and guess another time.\n ERROR ERROR ERROR");
                        if (choice4 == 0) {
                            choice1 = 1;
                        }
                        else if (choice4 == 1) {
                            choice1 = choice1();
                        }
                        else {
                            if (choice4 != 2) {
                                continue;
                            }
                            Memory.quit = true;
                        }
                    }
                }
            }
            else {
                if (choice1 != 4) {
                    continue;
                }
                Memory.quit = true;
            }
        }
    }
}
