package mines;

import javax.swing.*;
import java.awt.event.*;

public class MenuBar extends JMenuBar
{
  private JMenuBar menuBar = new JMenuBar();
  private JMenu file, view, help;
  private JMenuItem save, load, exit, newGame, beginner, intermediate, expert, custom, pause, resolve;
  
  public MenuBar()
  {
    //Create menu items to add to File
    file = new JMenu("File");
    save = new JMenuItem("Save");
    load = new JMenuItem("Load");
    exit = new JMenuItem("Exit");
    file.add(save);
    file.add(load);
    file.add(exit);

    //Create menu items to add to View
    view = new JMenu("Game");
    newGame = new JMenuItem("New Game");
    beginner = new JMenuItem("Beginner");
    intermediate = new JMenuItem("Intermediate");
    expert = new JMenuItem("Expert");
    custom = new JMenuItem("Custom...");
    pause = new JMenuItem("Pause");
    resolve = new JMenuItem("Resolve");
    view.add(newGame);
    view.add(beginner);
    view.add(intermediate);
    view.add(expert);
    view.add(custom);
    view.add(pause);
    view.add(resolve);
      
    //Add Help to the JMenu
    help = new JMenu("Help");

    //Add File, View and Help to the JMenuBar
    menuBar.add(file);
    menuBar.add(view);
    menuBar.add(help);
  }
}