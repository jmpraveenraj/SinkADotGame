


import Properties.PropertyClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * Created by praveen on 12/18/2015.
 */
public class Player
{

    private String name;
    private int noOfStrikes;


    Player()
    {
        this.name = getPlayerName();
        this.noOfStrikes = 0;
    }

    /*
        Used to get the player name with validation.
    */
    private String getPlayerName()
    {
        String name="";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter player name");
        try
        {
            while ( ( (name = br.readLine()).equals("")) || (name).equals(" ") )
            {
                System.out.println("Player name cant be empty, enter again");
            }


        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return name;
    }

    public String getName()
    {
        return this.name;
    }

    public int getNoOfStrikes()
    {
        return this.noOfStrikes;
    }

    public void setNoOfStrikes(int n)
    {
        this.noOfStrikes = n;
    }

    private int mapCol(char ch)
    {
        return (ch - 'A');
    }
    private boolean isValidCell ( String cell)
    {
        PropertyClass p = new PropertyClass();
        boolean isNotValid = false;
        int row , col;
        if ( (cell.length() != 2) )
        {
            isNotValid = true;


        }
        if ( Character.isDigit(cell.charAt(0)))
        {
            System.out.println("hurray  ");
            isNotValid  = true;
        }
        if ( !isNotValid  )
        {
            row = mapCol( cell.charAt(0) );
            col = cell.charAt(1) - '0';
            if ( (row >= p.getpropInt("row")) || ( col >= p.getpropInt("col") ) )
            {
                isNotValid = true;
            }
        }
        if ( cell.equals("Q"))
        {
            isNotValid = false;
        }
       return isNotValid;
    }
    public String guess()
    {

        String cellNo = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean isNotValid;
        try
        {
            this.setNoOfStrikes(this.getNoOfStrikes()+1);
            System.out.println(this.getName() + ", strike no : " + this.getNoOfStrikes());
            cellNo = br.readLine();
            isNotValid = this.isValidCell(cellNo);
            while ( isNotValid )
            {
                System.out.println("Enter correct cell no");
                cellNo = br.readLine();
                isNotValid = this.isValidCell(cellNo);

            }

        }
        catch (Exception e)
        {
            System.out.println( e.getMessage());
        }


        return cellNo;
    }


}
