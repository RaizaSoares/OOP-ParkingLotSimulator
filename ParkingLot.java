/***********************************************************************
 * @file ParkingLot
 * @author Raiza Soares
 * @Description: Contains the ParkingLot class and it =s functions
 **********************************************************************/

package soares_raiza;

import java.text.DecimalFormat;
import java.util.Vector;

/***********************************************************************
 * @Class ParkingLot
 * @author Raiza Soares
 * @Description: The ParkingLot class contains functions to manipulate
 * a single parking lot
 **********************************************************************/

public class ParkingLot {

    private int minEntry, minExit, id=-1, spaces, spacesIni, closedTime=0, currTime=0;
    private int NumVehicles=0;
    private String name="test";
    private Vector times= new Vector();
    private Vector list= new Vector();
    public static final int CLOSED_THRESHOLD =80;
    public ParkingLot(int s)
    {
        this.spacesIni= s;
        this.spaces= s;

    }
    public ParkingLot(String n, int s)
    {
        this.spacesIni= s;
        this.name= n;
        this.spaces= s;
    }

    public String getName()
    {
        return name;
    }
    public int getVehiclesInLot()
    {
        return NumVehicles;
    }
    /***********************************************************************
     * @function isClosed
     * @author Raiza Soares
     * @Description: Checks if a parking lot is closed.
     ********************************************************************/
    public boolean isClosed()
    {
        if(NumVehicles>= (spacesIni * CLOSED_THRESHOLD)/100.0) {
            return true; //in case lot is full
        }

        return false;

    }
    /***********************************************************************
     * @function markVehicleEntry
     * @author Raiza Soares
     * @Description: moves a vehicle into a given parking lot
     ********************************************************************/
    public int markVehicleEntry(int m)
    {
        if(currTime> m)
            return -1;
        currTime= m;
        if(!isClosed()) {
            minEntry = m;
        }
        if(spaces>0) {
            spaces = spaces - 1;
            NumVehicles = NumVehicles + 1;

            id = id + 1;
            times.add(id,m);
            list.add(id,1);
            return id;
        }
        else {
            return -1;
        }
    }
    public Vector getTimeData()
    {
        return times;
    }
    public int getTime()
    {
        return currTime;
    }
    /***********************************************************************
     * @function markVehicleEntry
     * @author Raiza Soares
     * @Description: moves a vehicle out of a given parking lot
     ********************************************************************/
    public void markVehicleExit(int m, int id)
    {
            if (currTime > m)
                return;
            currTime = m;
            boolean flag = false;
            flag = isClosed();
            spaces = spaces + 1;
            NumVehicles = NumVehicles - 1;
            list.set(id, 0);
            if (flag == true && isClosed() == false) {
                minExit = m;
                closedTime = closedTime + minExit - minEntry;

            }

    }
    public int getClosedMinutes()
    {
        return closedTime;
    }
    public int getMinInt()
    {
        return minEntry;
    }
    public int getMinInt2()
    {
        return minExit;
    }
    /***********************************************************************
     * @function toString
     * @author Raiza Soares
     * @Description:  displays The appropriate message required for status.
     **********************************************************************/
    public String toString( )
    {
        String string = String.format("Status for %s parking lot:%2d vehicles ", name, NumVehicles);
        if(isClosed())
        {
            string= string+ "(CLOSED)";
        }
        else
        {
            string = string + "(";
            if(spacesIni- spaces ==0)
            {
                string = string + "0%)";
            }
           else {
                DecimalFormat deci = new DecimalFormat("##0.#");

                string = string + deci.format((spacesIni - spaces) * 100.0 / spacesIni);
                string = string + "%)";
            }

        }

        return string;
    }

}
