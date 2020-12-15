/***********************************************************************
 * @file PayParkingLot
 * @author Raiza Soares
 * @Description: Contains the PayParkingLot class and it =s functions
 **********************************************************************/


package soares_raiza;


import java.text.DecimalFormat;
import java.util.Vector;
/***********************************************************************
 * @Class PayParkingLot
 * @author Raiza Soares
 * @Description: The PayParkingLot class contains functions to manipulate
 * a single paid parking lot. It extends the Parking lot class.
 **********************************************************************/
public class PayParkingLot extends ParkingLot
{

    private double hourlyFee, CurrFunds=0.00;
    private Vector r= new Vector();
    private Vector l= new Vector();
    private Vector j= new Vector();

    //constructors
    public PayParkingLot(int s, double hf)
    {
        super(s);
        this.hourlyFee=hf;
    }
    public PayParkingLot(String n, int s)
    {
        super(n,s);
        this.hourlyFee=2.00;
    }
    public PayParkingLot(String n, int s, double hf)
    {
        super(n, s);
        this.hourlyFee= hf;

    }
    /***********************************************************************
     * @function markVehicleEntry
     * @author Raiza Soares
     * @Description: adds additional functionality to markVehicleEntry in
     * ParkingLot class.
     * @parameters: minutes: time the vehicle enters.
     **********************************************************************/

    public int markVehicleEntry(int m)
    {
        int id= super.markVehicleEntry(m);
        if(id>=0) {
            l.add(id, 1);
        }
        return id;
    }
    /***********************************************************************
     * @function markVehicleExit
     * @author Raiza Soares
     * @Description: adds additional functionality to markVehicleExit in
     * ParkingLot class
     * @parameters: minutes: time the vehicle enters, car id
     **********************************************************************/

    public void markVehicleExit(int m, int id)
    {
        if(id>=0 && (int)l.get(id)==1) {
            if(!(m<super.getTime())) {
                super.markVehicleExit(m, id);

                l.set(id, 0);
                r = super.getTimeData();
                //j= super.getTimeData();
                int minEntry = (int) r.get(id);

                r.set(id, (m - minEntry - 15));
            }
            }
        }

    /***********************************************************************
     * @function getProfit
     * @author Raiza Soares
     * @Description: Calculates the profit made by multiplying the hourly fee
     * to the number of minutes the cars spent at the parking lot
     **********************************************************************/
    public double getProfit()
    {
        int sum=0;
        int var=0;
        for(int i=0; i<r.size(); i++)
        {
            if((int)r.get(i) <0)
                var=0;
            else
                var= (int)r.get(i) ;
            sum= sum + var;
        }
        CurrFunds= sum*hourlyFee /60;
        return sum*hourlyFee /60;

    }
    /***********************************************************************
     * @function toString
     * @author Raiza Soares
     * @Description: Overrides the Parking lot's To string function and displays
     * The appropriate message
     **********************************************************************/
    @Override
    public String toString( )
    {
        DecimalFormat deci = new DecimalFormat("##0.00");
        String temp = super.toString();
        temp += " Money collected: $" + deci.format(getProfit());
        return temp;


    }


}
