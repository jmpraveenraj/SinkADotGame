

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praveen on 12/18/2015.
 */
public class DotCom
{
    private int length;
    private List<String> cells;
    private List<String> hits;

    DotCom(List<String> cell)
    {
        this.length = cell.size();
        this.cells = cell;
        this.hits = new ArrayList<>();
    }

    public String check(String cell)
    {
        String status = "";
        if ( isHit(cell) )
        {
            status = "hit";
            if ( isDestroyed() )
            {
                status = "destroyed";
            }
        }
        else
        {
            status = "not hit";
        }
        return status;
    }

    private boolean isHit( String cell)
    {
        boolean hit = false;
        if ( this.cells.contains(cell))
        {
            hit = true;
            this.hits.add(cell);
        }
        return hit;
    }

    private boolean isDestroyed()
    {
        boolean destroyed = false;
        if ( this.hits.size() == this.length)
        {
            destroyed = true;
        }
        return destroyed;
    }
}
