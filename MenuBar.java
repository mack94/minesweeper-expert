package mines;

import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class MenuBar extends JMenuBar
{
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu, viewMenu, helpMenu, editMenu;
    private JMenuItem saveItem, loadItem, exitItem, newGameItem, pauseItem,
            resolveItem, helpItem, highscore, aboutItem, undoItem, redoItem;
    protected JRadioButtonMenuItem beginnerItem, intermediateItem, expertItem,
            customItem;

    public MenuBar()
    {
        buildFileMenu();
        buildGameMenu();
        buildHighscoreMenu();
        buildHelpMenu();
        buildEditMenu();

        //Add File, View and Help to the JMenuBar
        add(fileMenu);
        add(editMenu);
        add(viewMenu);
        add(highscore);
        add(helpMenu);
    }

    private void buildFileMenu()
    {
        //Create menu items to add to File
        fileMenu = new JMenu("File");
        saveItem = new JMenuItem("Save");
        saveItem.setMnemonic('s');
        saveItem.addActionListener(new SaveListener());
        loadItem = new JMenuItem("Load");
        loadItem.setMnemonic('o');
        loadItem.addActionListener(new LoadListener());
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ExitListener());
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);
    }

    private void buildGameMenu()
    {
        //Create menu items to add to View
        viewMenu = new JMenu("Game");
        pauseItem = new JMenuItem("Pause");
        pauseItem.setMnemonic('p');
        newGameItem = new JMenuItem("New Game");
        newGameItem.setMnemonic('n');

        beginnerItem = new JRadioButtonMenuItem("Beginner");
        beginnerItem.addActionListener(new DifficultyListener(beginnerItem, intermediateItem, expertItem));

        intermediateItem = new JRadioButtonMenuItem("Intermediate", true);
        intermediateItem.addActionListener(new DifficultyListener(beginnerItem, intermediateItem, expertItem));

        expertItem = new JRadioButtonMenuItem("Expert");
        expertItem.addActionListener(new DifficultyListener(beginnerItem, intermediateItem, expertItem));

        customItem = new JRadioButtonMenuItem("Custom...");
        customItem.addActionListener(new CustomGameListener());

        ButtonGroup difficultyGroup = new ButtonGroup();

        difficultyGroup.add(beginnerItem);
        difficultyGroup.add(intermediateItem);
        difficultyGroup.add(expertItem);
        difficultyGroup.add(customItem);

        viewMenu.add(pauseItem);
        viewMenu.add(newGameItem);
        viewMenu.add(beginnerItem);
        viewMenu.add(intermediateItem);
        viewMenu.add(expertItem);
        viewMenu.add(customItem);
    }

    private void buildEditMenu()
    {
        //Create items to add to Edit
        editMenu = new JMenu("Edit");
        undoItem = new JMenuItem("Undo");
        undoItem.setMnemonic('z');
        redoItem = new JMenuItem("Redo");
        redoItem.setMnemonic('y');
        editMenu.add(undoItem);
        editMenu.add(redoItem);
    }

    private void buildHelpMenu()
    {
        //Create menu items to add to Help
        helpMenu = new JMenu("Help");
        resolveItem = new JMenuItem("Solve");
        resolveItem.addActionListener(new Resolver());
        helpItem = new JMenuItem("Help");
        aboutItem = new JMenuItem("About");
        helpMenu.add(resolveItem);
        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);
    }

    private void buildHighscoreMenu()
    {
        highscore = new JMenuItem("Highscore");
        //System.out.println(highscore.getPreferredSize());
        highscore.setPreferredSize(new Dimension(90, 19));
        highscore.setMaximumSize(new Dimension(90, 19));
        highscore.setMinimumSize(new Dimension(90, 19));
        highscore.addActionListener(new HighscoreListener());
    }
}