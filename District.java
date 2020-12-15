/***********************************************************************
 * @file District
 * @author Raiza Soares
 * @Description: Contains the District class and it =s functions
 **********************************************************************/

package soares_raiza;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/***********************************************************************
 * @Class District
 * @author Raiza Soares
 * @Description: The district class contains functions to manipulate
 * many parking lots in the same district.
 **********************************************************************/
public class District {
     private ArrayList<ParkingLot> lots = new ArrayList<ParkingLot>();
     private Vector h= new Vector();
     private Vector g= new Vector();
     //constructors
     public District()
     {

     }
     /***********************************************************************
      * @function markVehicleEntry
      * @author Raiza Soares
      * @Description: moves a vehicle into a given parking lot based on
      * its index
      ********************************************************************/
     public int markVehicleEntry(int index, int m)
     {
          int id;
          id= lots.get(index).markVehicleEntry(m);
          return id;
     }
     /***********************************************************************
      * @function markVehicleExit
      * @author Raiza Soares
      * @Description: moves a vehicle out of a given parking lot based on
      * its index and car id.
      ********************************************************************/

     public void markVehicleExit(int index, int m, int id)
     {
          lots.get(index).markVehicleExit(m, id);
     }

     /***********************************************************************
      * @function add
      * @author Raiza Soares
      * @Description: Adding a parking lot to the district
      ********************************************************************/
     public int add(ParkingLot p)
     {
          lots.add(p);
          return lots.size()-1;
     }

     /***********************************************************************
      * @function isClosed
      * @author Raiza Soares
      * @Description: Checks if all the parking lots are closed
      ********************************************************************/
     public boolean isClosed()
     {
          boolean flag= true;
          for (ParkingLot lot : lots) {
               if (!(lot.isClosed())) {
                    flag = false;
               }
          }
          return flag;
     }
     /***********************************************************************
      * @function getVehiclesParkedInDistrict
      * @author Raiza Soares
      * @Description: Total number of all vehicles parked in the district.
      ********************************************************************/
     public int getVehiclesParkedInDistrict()
     {
          int sum=0;
          for (ParkingLot lot : lots) {
              sum= sum+ lot.getVehiclesInLot();
          }
          return sum;
     }
     //Return a certain parking lot
     public ParkingLot getLot(int index)
     {
          return lots.get(index);
     }

     /***********************************************************************
      * @function getClosedMinutes
      * @author Raiza Soares
      * @Description: Total closed minutes when all the parking lots are closed.
      ********************************************************************/
     public int getClosedMinutes()
     {
          int min=Integer.MAX_VALUE;
          int l=0;
          /*
          for(ParkingLot lot : lots)
          {
               int ent= lot.getMinInt();
               h.add(ent);
               int end= lot.getMinInt2();
               g.add(end);
          }
          int min2= (int) Collections.min(h);
          int max= (int) Collections.max(g);

          return max-min2;
          */

          for (ParkingLot lot : lots) {
               l= lot.getClosedMinutes();
               if(l<min)
               {
                    min= l;
               }
          }
          return min;


     }
     /***********************************************************************
      * @function getTotalMoneyCollected
      * @author Raiza Soares
      * @Description: Total money collected from all payParking lots in the
      * district.
      **********************************************************************/

     public double getTotalMoneyCollected()
     {
          double sum=0.0;
          for (ParkingLot lot : lots) {
               if(lot instanceof PayParkingLot)
               {
                    PayParkingLot temp = (PayParkingLot)lot;
                    sum= sum + temp.getProfit();
               }
          }
          return sum;
     }
     /***********************************************************************
      * @function toString
      * @author Raiza Soares
      * @Description: To string function displays appropriate status after tiers.
      **********************************************************************/
     public String toString( )
     {
          String MegaString = "District status:\n";
          for (ParkingLot lot : lots) {
               MegaString=  MegaString+ lot.toString() + "\n";
          }
          return MegaString;
     }



}
