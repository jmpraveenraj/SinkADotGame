

import Properties.PropertyClass;


/**
 * Created by praveen on 12/18/2015.
 */
public class Game
{
    private Player player;
    private Board board;
    private int sleepTime;

    Game()
    {
        PropertyClass p = new PropertyClass();
        this.player = new Player();
        this.board = new Board();
        this.sleepTime = p.getpropInt("sleepTime");
    }

    public void start()
    {

        readyGame();
        play();

    }

    private void readyGame()
    {
        System.out.println("Welcome " + this.player.getName() + " to SinkADotCom Game!!!!!!!!!!");
        boolean waitProb = false;
        while( !waitProb )
        {
            System.out.println("Setting up the game board");
            try
            {
                Thread.sleep(this.sleepTime);
                waitProb = true;
                System.out.println("Placing the dot coms on the board.....");
                Thread.sleep(this.sleepTime);
                this.board.placeDotComs();
                System.out.println("Game is ready .. ");
                System.out.println(this.board.getNoOfDotCom() + " ships placed ");
                //this.board.printPlayerBoard();
                this.board.printBoard();
                System.out.println("Start your attack");
            }
            catch(InterruptedException e)
            {
                waitProb = false;
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
                System.out.println("Faced with some problem, please wait, the game is going to reboot....");
            }
        }
    }

    private void play()
    {

        String cellNo , status;
        boolean finished = false;
        while ( !finished &&  ( ! ( (cellNo = this.player.guess()).equals("Q") ) )  )
        {

            status = this.board.analyze(cellNo);
            if ( status.equals("not hit"))
            {
                System.out.println("Not Hit");
            }
            else if ( status.equals("hit"))
            {
                System.out.println("Successfully Hit");
            }
            else if ( status.equals("destroyed"))
            {
                System.out.println("Hurray!! Destroyed.. " + this.board.getDotComDown() + " down " + this.board.dotComRemaining() + " to go");

            }
            if ( this.board.dotComRemaining() == 0)
            {
                finished = true;
            }
            //this.board.printPlayerBoard();
            this.board.printBoard();
        }
        System.out.println("Thanq for playing..... " + this.board.getDotComDown() + " dot coms destroyed");
        if ( finished )
        {
            System.out.println("CONGRATS.....YOU ARE A WINNER!!!!!!!!!!!!");
        }
    }

}
