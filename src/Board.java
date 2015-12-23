import Properties.PropertyClass;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by praveen on 12/18/2015.
 */
public class Board
{
    private char[][] board;
    private int row;
    private int col;
    private String space;
    private String dash;
    private int noOfDotCom;
    private List<DotCom> dotComs;
    private int maxDotComLen;
    private int dotComDown;
    private final char[] symbols = {'^', '*' , '$'};
    private final char hit = 'h';
    private final String alreadyHit = "already hit";
    Board()
    {
        PropertyClass p = new PropertyClass();
        this.row = p.getpropInt("row");
        this.col = p.getpropInt("col");
        this.board = new char[this.getRow()][this.getCol()];
        this.space = "  ";
        this.dash =  "------";
        this.noOfDotCom = p.getpropInt("noOfDotComs");
        this.maxDotComLen = p.getpropInt("dotComLen");
        this.dotComs = new ArrayList<>();
        this.dotComDown = 0;
        this.initializeBoard();
    }

    public int getRow()
    {
        return this.row;
    }

    public int getCol()
    {
        return this.col;
    }

    public int getDotComDown()
    {
        return this.dotComDown;
    }
    public int getLen()
    {
        return this.maxDotComLen;
    }

    public int getNoOfDotCom()
    {
        return this.noOfDotCom;
    }

    public void printPlayerBoard()
    {
        int i, j;
        printNumber();
        final int letter = 65;
        for ( i = 0 ; i < this.getRow() ; i++)
        {
            System.out.print((char) (letter + i) + " ") ;
            for ( j = 0 ; j < this.getCol() ; j++ )
            {
                if ( ( this.board[i][j] == this.getHead() )  || ( this.board[i][j] == this.getBody() ) || ( this.board[i][j] == this.getTail() ) )
                {
                    System.out.print(" " + this.space + "|" + this.space);
                }
                else
                {
                    System.out.print(this.board[i][j] + this.space + "|" + this.space);
                }

            }

            printDash();
        }
    }

    public void printBoard()
    {
        int i, j;
        printNumber();
        final int letter = 65;
        for ( i = 0 ; i < this.getRow() ; i++)
        {
            System.out.print( (char)(letter+i) + " " ) ;
            for ( j = 0 ; j < this.getCol() ; j++ )
            {
                System.out.print(this.board[i][j] + this.space + "|" + this.space);
            }

            printDash();
        }
    }

    private void initializeBoard()
    {
        int i , j;
        for ( i = 0 ; i < this.getRow() ; i++)
        {
            for ( j = 0 ; j < this.getCol() ; j++)
            {
                this.board[i][j] = ' ';
            }
        }
    }
    private void printNumber()
    {
        System.out.print(this.space);
        for ( int i = 0 ; i < this.getRow() ; i++)
        {
            System.out.print(i + this.space + "   ");
        }
        System.out.println();
    }
    private void printDash()
    {
        System.out.println();
        System.out.print(" ");
        for ( int i = 0 ; i < this.getCol() ; i++)
        {
            System.out.print(this.dash);
        }
        System.out.println();
    }

    public void placeDotComs()
    {
        int i;
        List<String> tiles;
        for ( i = 0 ; i < this.noOfDotCom ; i++)
        {
            tiles = createDotComTiles();
            if ( tiles.size() > 0)
            {
                //System.out.println("Placed");

                dotComs.add(new DotCom(tiles));
            }
            else
            {
                //System.out.println("Not placed");
            }
        }
        this.noOfDotCom = dotComs.size();
    }

    private char getHead()
    {
        return this.symbols[0];
    }

    private char getBody()
    {
        return this.symbols[1];
    }

    private char getTail()
    {
        return this.symbols[2];
    }

    private List<String> generateTileHori(int stRow , int stCol , int len)
    {
        List<String> tiles = new ArrayList<>();
        String rowAlpha = mapRow(stRow);
        boolean canBePlaced  = true;
        for ( int i = 0 ; i < len && canBePlaced; i++)
        {

            if ( (this.board[stRow][stCol+i] == getHead() ) || (this.board[stRow][stCol+i] == getBody() ) || (this.board[stRow][stCol+i] == getTail() )  )
            {
                canBePlaced = false;
                tiles.clear();
            }
            else
            {
                tiles.add(rowAlpha + (stCol + i));
                if ( i == 0)
                {
                    this.board[stRow][stCol+i] = getHead();
                }
                else if ( i == len - 1)
                {
                    this.board[stRow][stCol+i] = getTail();
                }
                else
                {
                    this.board[stRow][stCol+i] = getBody();
                }
            }


        }
        return tiles;
    }

    private List<String> generateTileVert(int stRow , int stCol , int len)
    {
        List<String> tiles = new ArrayList<>();
        boolean canBePlaced = true;
        for ( int i = 0 ; i < len && canBePlaced ; i++)
        {
            if ( (this.board[stRow+i][stCol] == getHead()) || (this.board[stRow+i][stCol] == getBody()) || (this.board[stRow+i][stCol] == getTail()) )
            {
                canBePlaced = false;
                tiles.clear();
            }
            else
            {
                tiles.add(mapRow(stRow + i) + stCol);
                if ( i == 0)
                {
                    this.board[stRow+i][stCol] = getHead();
                }
                else if ( i == len - 1)
                {
                    this.board[stRow+i][stCol] = getTail();
                }
                else
                {
                    this.board[stRow+i][stCol] = getBody();
                }
            }


        }
        return tiles;
    }

    private List<String> createDotComTiles()
    {
        Random rand = new Random();
        boolean isPlaced = false;
        List <String> tiles = new ArrayList<>();
        int len , lenCount , stRow , stCol, cellCount ;

        cellCount = 1;

        while ( (cellCount <= 3) && !isPlaced )
        {
            cellCount++;
            stRow = rand.nextInt(this.getRow());
            stCol = rand.nextInt(this.getCol());

            // Trying to place dotcom with the cell no repeatedly with decreasing length for 3 times

            lenCount = 1;

            while ( (lenCount <=3) && (!isPlaced) )
            {
                len = rand.nextInt( (this.getLen()-1) - 2 ) + 2 ;       //Generate random int from [2,len-1)
                lenCount ++;
                if ((stCol + len) <= this.getCol()) //can be placed horizontally  (L -> R)
                {
                    tiles = generateTileHori(stRow, stCol, len);
                    if ( !tiles.isEmpty())
                    {
                        isPlaced = true;
                    }

                }
                else if ((stCol - len) >= 0) //can be placed horizontally  (R -> L)
                {
                    tiles = generateTileHori(stRow, (stCol - len), len);
                    if ( !tiles.isEmpty())
                    {
                        isPlaced = true;
                    }
                }
                else if ((stRow + len) <= this.getRow()) //can be placed vertically (T -> D )
                {
                    tiles = generateTileVert(stRow, stCol, len);
                    if ( !tiles.isEmpty())
                    {
                        isPlaced = true;
                    }
                }
                else if ((stRow - len) >= 0) //can be placed vertically (D -> T )
                {
                    tiles = generateTileVert(stRow - len, stCol, len);
                    if ( !tiles.isEmpty())
                    {
                        isPlaced = true;
                    }
                }
            }

        }
        return tiles;
    }

    private String mapRow(int no)
    {
        return Character.toString((char)(no+65) );
    }

    private int mapRow(String cell)
    {
        return cell.charAt(0) - 'A';
    }

    public String analyze(String cell)
    {
        ListIterator itr = this.dotComs.listIterator();
        DotCom dotcom;
        boolean isHit = false;
        String status = "";
        int row = mapRow(cell);
        int col = cell.charAt(1) - '0';
        if ( this.board[row][col] != this.hit)
        {
            while ( itr.hasNext() && !isHit )
            {
                dotcom = (DotCom)itr.next();
                status = dotcom.check(cell);
                if ( status.equals("destroyed") || status.equals("hit") )
                {
                    isHit = true;
                    this.board[row][col] = this.hit;
                }
                if ( status.equals("destroyed"))
                {
                    this.dotComDown++;
                }
            }

        }
        else
        {
            status = this.alreadyHit;
        }
        return status;
    }

    public int dotComRemaining()
    {

        return (this.getNoOfDotCom() - this.getDotComDown());
    }

}
