package soares_raiza;

import java.text.DecimalFormat;

/**
 * @author
 */
public class ParkingTests {

    /**
     * Main test method: calls other tests.
     *
     * @param args Ignored command-line arguments
     */
    public static void main(String[] args) {
        testSmallLot();
        System.out.println();

        testComingAndGoing();
        System.out.println();

        testParkingLot();
        System.out.println();

        testOverfillingLot();
        System.out.println();

        testTinyDistrict();
        System.out.println();

        testDistrict();
        System.out.println();

        testHeavyUsage();
        System.out.println();

        testSmallLotPaid();
        System.out.println();

        testLotPaidWithGlitches();
        System.out.println();

        testPaidDistrict();
        System.out.println();
        System.out.println("All tests finished.");
    }

    ///////////////////////////////////////////////////////////
    // simple tests - basic functionality

    /**
     * Test creating a parking lot with a single space.
     */
    public static void testSmallLot() {
        System.out.println("Tier 1: Testing a Parking lot with one space.............................................");
        ParkingLot driveway = new ParkingLot("blacktop", 1);
        if (!driveway.getName().equals("blacktop"))
            System.out.println(">>>>>>>>>> .getName() not working");
        if (Math.abs(ParkingLot.CLOSED_THRESHOLD - 80) > 0.01)
            System.out.println(">>>>>>>>>> Incorrect threshold for closed lot.");
        if (driveway.isClosed())
            System.out.println(">>>>>>>>>> Empty driveway is closed.");
        int carID = driveway.markVehicleEntry(5);
        if (!driveway.isClosed())
            System.out.println(">>>>>>>>>> Driveway with something in it is not closed.");
        if (!driveway.toString().equals("Status for blacktop parking lot: 1 vehicles (CLOSED)"))
            System.out.println(">>>>>>>>>> Incorrect status: " + driveway);
        if (carID != 0 )
            System.out.println(">>>>>>>>>> Incorrect carID (should be 0): " + carID);
        driveway.markVehicleExit(400, 0);
        if (driveway.getVehiclesInLot() != 0)
            System.out.println(">>>>>>>>>> Empty driveway should have no vehicles in it.");
        if (driveway.getClosedMinutes() != 395)
            System.out.println(">>>>>>>>>> Wrong number of minutes while sleeping overnight.");
        System.out.println(driveway);
    }


    ///////////////////////////////////////////////////////////
    // parking lot tests

    /**
     * Test what happens when lot fills, empties, then refills.
     */
    public static void testComingAndGoing() {
        System.out.println("\nTier 2: Testing Parking lots multiple coming and going cars..............................");
        ParkingLot busy = new ParkingLot(5);
        int carID1 = busy.markVehicleEntry(5);
        int carID2 = busy.markVehicleEntry(10);
        int carID3 = busy.markVehicleEntry(12);
        if ( busy.isClosed() )
            System.out.println(">>>>>>>>>> Error: Busy lot should not be closed at time 12");
        if (carID1 != 0 )
            System.out.println(">>>>>>>>>> Incorrect carID1 (should be 0): " + carID1);
        if (carID2 != 1 )
            System.out.println(">>>>>>>>>> Incorrect carID1 (should be 1): " + carID2);
        if (carID3 != 2 )
            System.out.println(">>>>>>>>>> Incorrect carID1 (should be 2): " + carID3);
        busy.markVehicleEntry(15);
        if ( !busy.isClosed() )
            System.out.println(">>>>>>>>>> Error: Busy lot should be closed at time 15");
        if ( busy.getVehiclesInLot() != 4 )
            System.out.println(">>>>>>>>>> Error: Busy lot should have four vehicles at time 15");
        busy.markVehicleEntry(20);
        if ( busy.getVehiclesInLot() != 5 )
            System.out.println(">>>>>>>>>> Error: Busy lot should have five vehicles at time 20");
        busy.markVehicleExit(23, 0);
        if ( busy.getVehiclesInLot() != 4 )
            System.out.println(">>>>>>>>>> Error: Busy lot should have four vehicles at time 23");
        busy.markVehicleExit(25, 0);
        busy.markVehicleExit(25, 0);
        if ( busy.getClosedMinutes() != 10 )
            System.out.println(">>>>>>>>>> Error: busy parking lot should have been closed for 10 minutes at time 30");
        busy.markVehicleEntry(33);
        int carID4 = busy.markVehicleEntry(35);
        if (carID4 != 6 )
            System.out.println(">>>>>>>>>> Incorrect carID4 (should be 6): " + carID4);
        if ( !busy.isClosed() )
            System.out.println(">>>>>>>>>> Error: Busy lot should be closed at time 35");
        if ( busy.getVehiclesInLot() != 4 )
            System.out.println(">>>>>>>>>> Error: Busy lot should have four vehicles at time 35");
        busy.markVehicleEntry(40);
        if ( busy.getVehiclesInLot() != 5 )
            System.out.println(">>>>>>>>>> Error: Busy lot should have five vehicles at time 40");
        busy.markVehicleExit(45, 0);
        if ( busy.getVehiclesInLot() != 4 )
            System.out.println(">>>>>>>>>> Error: Busy lot should have four vehicles at time 45");
        busy.markVehicleExit(50, 0);
        busy.markVehicleExit(54, 0);
        busy.markVehicleExit(60, 0);
        busy.markVehicleExit(60, 0);
        if ( busy.getClosedMinutes() != 25 )
            System.out.println(">>>>>>>>>> Error: busy parking lot should have been closed for 25 minutes");
        if ( busy.getVehiclesInLot() != 0 )
            System.out.println(">>>>>>>>>> Error: Busy lot should be empty at end");
        System.out.println(busy);
    }

    /**
     * Uses other static methods to test the ParkingLot class.
     * Creates a lot with four spaces that is used for most of
     * the tests.
     */
    public static void testParkingLot() {
        ParkingLot lot = new ParkingLot(4);
        System.out.println("\nTier 3: Stress Testing a Parking lot....................................................");

        if (!lot.getName().equals("test"))
            System.out.println(">>>>>>>>>> Lot name should be 'test'.");

        testFillingLot(lot);
        testRefillingLot(lot);
        testEmptyingLot(lot);
        test0TimeEntryExit();
        lot = new ParkingLot(3);

        lot.markVehicleEntry(3);
        lot.markVehicleEntry(3);

        if (!lot.toString().equals("Status for test parking lot: 2 vehicles (66.7%)"))
            System.out.println(">>>>>>>>>> Error: Percentage formatting error.");
    }

    /**
     * Tests adding three vehicles to a lot with four spaces. Vehicles enter
     * at times 10, 12, and 13, the status is checked. Another vehicle
     * enters at time 20 and the status is checked again. One vehicle
     * then leaves followed by a final status check.
     *
     * @param lot ParkingLot with four spaces
     */
    private static void testFillingLot(ParkingLot lot) {
        System.out.println("\n.........................Tier 3a: Testing a Parking lot closures........................");
        lot.markVehicleEntry(10);
        lot.markVehicleEntry(12);
        lot.markVehicleEntry(13);
        System.out.println(lot);
        if (lot.isClosed())
            System.out.println(">>>>>>>>>> Error: lot closed with just 3 vehicles.");
        if (lot.getClosedMinutes() != 0)
            System.out.println(">>>>>>>>>> Error: closed minutes should be 0.");
        lot.markVehicleEntry(20);
        System.out.println(lot);
        if (!lot.isClosed())
            System.out.println(">>>>>>>>>> Error: lot not closed with 4 vehicles.");
        lot.markVehicleExit(31, 0);
        if (lot.isClosed())
            System.out.println(">>>>>>>>>> Error: lot closed after first exit.");
        if (lot.getClosedMinutes() != 11)
            System.out.println(">>>>>>>>>> Error: expected 11 minutes of closed time, got "
                    + lot.getClosedMinutes() + ".");
        System.out.println(lot);
    }

    /**
     * This continues the test started in testFillingLot and assumes
     * initially there are three vehicles in the lot. One leaves and
     * two more enter. A vehicle leaves at 57 minutes, and the status
     * is rechecked. Finally, the parking lot is returned to closed at 71
     * minutes.
     *
     * @param lot ParkingLot with four spaces; must be same lot passed to testFillingLot()
     */
    private static void testRefillingLot(ParkingLot lot) {
        System.out.println("\n..................Tier 3b: Testing emptying then refilling the lot......................");
        if (lot.getVehiclesInLot() != 3)
            System.out.println(">>>>>>>>>> Error: expected 3 vehicles on lot.");
        lot.markVehicleExit(45, 0);
        // lot has 2 vehicles
        if (!lot.toString().equals("Status for test parking lot: 2 vehicles (50%)"))
            System.out.println(">>>>>>>>>> Unexpected status @ 45: " + lot);
        lot.markVehicleEntry(50);
        lot.markVehicleEntry(52);
        lot.markVehicleExit(57, 0);    // add 5 minutes
        // returned to 3 vehicles
        if (lot.getVehiclesInLot() != 3)
            System.out.println(">>>>>>>>>> Error: expected 3 vehicles on lot at 57.");
        lot.markVehicleEntry(71);
        if (!lot.isClosed())
            System.out.println(">>>>>>>>>> Error: expected closed lot at 71 minutes.");
        System.out.println(lot);
    }

    /**
     * This continues the test started in testRefillingLot and assumes
     * all four spaces are filled. A vehicle leaves at 79 minutes,
     * followed by vehicles at 91, 92, and 94. The status is checked to
     * make sure no vehicles are on the lot.
     *
     * @param lot ParkingLot with four spaces; must be same lot passed to testReFillingLot()
     */
    private static void testEmptyingLot(ParkingLot lot) {
        System.out.println("\n.............................Tier 3c: Testing a emptying a parking lot...................");
        if (lot.getVehiclesInLot() != 4)
            System.out.println(">>>>>>>>>> Error: expected 4 vehicles on lot.");
        lot.markVehicleExit(79, 0);    // add 8 minutes
        if (lot.getClosedMinutes() != 24)
            System.out.println(">>>>>>>>>> Error: expected 24 minutes of closed time, got "
                    + lot.getClosedMinutes() + ".");
        if (lot.isClosed())
            System.out.println(">>>>>>>>>> Error: expected non-closed lot at 79 minutes.");
        lot.markVehicleExit(91, 0);
        lot.markVehicleExit(92, 0);
        lot.markVehicleExit(94, 0);
        if (!lot.toString().equals("Status for test parking lot: 0 vehicles (0%)"))
            System.out.println(">>>>>>>>>> Unexpected status @ 94: " + lot);
        if (lot.getVehiclesInLot() != 0)
            System.out.println(">>>>>>>>>> Error: lot should be empty at 94 minutes.");
        if (lot.getClosedMinutes() != 24)
            System.out.println(">>>>>>>>>> Error: expected 24 minutes of closed time at end");
        System.out.println("Final status: " + lot);
        System.out.println();
    }

    /**
     * Test entry/exit where the time is in the past, and that entering
     * and exiting within the same minute works.
     */
    public static void test0TimeEntryExit() {
        System.out.println("\nTier 4: Testing a Parking with invalid times (shoudl have not output)..................");
        ParkingLot lot = new ParkingLot(6);

        lot.markVehicleEntry(50);
        if (lot.getVehiclesInLot() != 1)
            System.out.println(">>>>>>>>>> Error: expecting 1 vehicle at time 50.");
        // try to enter, exit in past
        lot.markVehicleEntry(49);
        if (lot.getVehiclesInLot() != 1)
            System.out.println(">>>>>>>>>> Error: expecting 1 vehicle at time -1.");
        lot.markVehicleExit(49, 0);
        if (lot.getVehiclesInLot() != 1)
            System.out.println(">>>>>>>>>> Error: expecting 1 vehicle at time -1b.");
        // enter, exit all at once
        lot.markVehicleEntry(51);
        lot.markVehicleEntry(51);
        lot.markVehicleEntry(51);
        if (lot.getVehiclesInLot() != 4)
            System.out.println(">>>>>>>>>> Error: expecting 4 vehicles at time 51.");
        if (lot.isClosed())
            System.out.println(">>>>>>>>>> Error: lot should not be closed at time 51.");
        lot.markVehicleEntry(51);
        if (lot.getVehiclesInLot() != 5)
            System.out.println(">>>>>>>>>> Error: expecting 5 vehicles at time 51b.");
        if (!lot.isClosed())
            System.out.println(">>>>>>>>>> Error: lot should be closed at time 51b.");
        lot.markVehicleExit(51, 0);
        if (lot.getVehiclesInLot() != 4)
            System.out.println(">>>>>>>>>> Error: expecting 4 vehicles at time 51c.");
        if (lot.getClosedMinutes() != 0)
            System.out.println(">>>>>>>>>> Error: expecting 0 minutes closed at time 51c.");
    }

    /**
     * Test some boundary conditions on lot closure including what happens if a vehicle
     * enters when the lot is marked as being closed.
     */
    public static void testOverfillingLot() {
        System.out.println("\nTier 5: Testing a Parking lot closures...................................................");
        ParkingLot lot = new ParkingLot("blacktop", 9);
        for (int i = 0; i < 7; ++i) {
            lot.markVehicleEntry(2);
        }
        lot.markVehicleEntry(3);
        if (!lot.isClosed()) {
            System.out.println(">>>>>>>>>> Error: expected 9-vehicle driveway to be closed at time 3");
        }
        lot.markVehicleEntry(4);
        if ( lot.getVehiclesInLot() != 9 )
            System.out.println(">>>>>>>>>> Error: lot should have 9 vehicles at time 4");
        lot.markVehicleEntry(5);
        if ( lot.getVehiclesInLot() != 9 )
            System.out.println(">>>>>>>>>> Error: lot should have 9 vehicles at time 5");

        lot.markVehicleExit(12, 0);
        lot.markVehicleExit(12, 0);
        if (lot.isClosed()) {
            System.out.println(">>>>>>>>>> Error: expected 9-vehicle driveway to be open at time 12");
        }
        if (lot.getClosedMinutes() != 9) {
            System.out.println(">>>>>>>>>> Error: 9-vehicle driveway test should have 9 minutes closed but you have "
                    + lot.getClosedMinutes());
        }

        lot = new ParkingLot(10);
        for (int i = 0; i < 7; ++i) {
            lot.markVehicleEntry(2);
        }
        lot.markVehicleEntry(3); // 8: now closed
        if (!lot.isClosed()) {
            System.out.println(">>>>>>>>>> Error: expected 10-vehicle driveway to be closed at time 3");
        }
        lot.markVehicleExit(12, 0); // 7: no longer closed
        if (lot.isClosed()) {
            System.out.println(">>>>>>>>>> Error: expected 10-vehicle driveway to be open at time 12");
        }
        lot.markVehicleEntry(13); // 8: now closed
        lot.markVehicleExit(15, 0); // 7: no longer closed
        if (lot.getClosedMinutes() != 11) {
            System.out.println(">>>>>>>>>> Error: 10-vehicle driveway test should have 11 closed minutes but you have "
                    + lot.getClosedMinutes());
        }

        System.out.println(lot);
    }

    ///////////////////////////////////////////////////////////
    // district tests

    /**
     * Create district with small parking lots, fill them, then
     * ensure one is not closed.
     */
    public static void testTinyDistrict() {
        String initalOutput = "District status:\n" +
                "Status for red parking lot: 0 vehicles (0%)\n" +
                "Status for green parking lot: 0 vehicles (0%)\n" +
                "Status for blue parking lot: 0 vehicles (0%)\n";

        System.out.println("\nTier 6: Testing a District with 3 tiny parking lots......................................");
        District ourTown = new District();
        int redLot = ourTown.add(new ParkingLot("red", 1));
        int greenLot = ourTown.add(new ParkingLot("green", 1));
        int blueLot = ourTown.add(new ParkingLot("blue", 2));
        System.out.print("Tiny " + ourTown);
        if (!ourTown.toString().equals(initalOutput))
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> incorrect district toString()");
        if (ourTown.getVehiclesParkedInDistrict() != 0)
            System.out.println(">>>>>>>>>> Expected 0 vehicles in tiny district");
        int carID1 = ourTown.markVehicleEntry(greenLot, 5);
        int carID2 = ourTown.markVehicleEntry(redLot, 7);
        int carID3 = ourTown.markVehicleEntry(blueLot, 10);
        int carID4 = ourTown.markVehicleEntry(blueLot, 12);
        if (carID1 != 0 )
            System.out.println(">>>>>>>>>> Incorrect carID1 (should be 0): " + carID1);
        if (carID2 != 0 )
            System.out.println(">>>>>>>>>> Incorrect carID2 (should be 0): " + carID2);
        if (carID3 != 0 )
            System.out.println(">>>>>>>>>> Incorrect carID3 (should be 0): " + carID3);
        if (carID4 != 1 )
            System.out.println(">>>>>>>>>> Incorrect carID4 (should be 1): " + carID4);
        if (ourTown.getVehiclesParkedInDistrict() != 4)
            System.out.println(">>>>>>>>>> Expected 4 vehicles in tiny district");
        if (!ourTown.isClosed())
            System.out.println(">>>>>>>>>> Error in tiny district at 12: should be closed.");
        ourTown.markVehicleExit(greenLot, 15, 0);
        if (ourTown.isClosed())
            System.out.println(">>>>>>>>>> Error in tiny district at 15: should be open.");
        if (ourTown.getVehiclesParkedInDistrict() != 3)
            System.out.println(">>>>>>>>>> Expected 3 vehicles in tiny district at time 15");
        System.out.print(ourTown.toString());
        ourTown.markVehicleExit(redLot, 17, 0);
        ourTown.markVehicleEntry(greenLot, 18);
        if (ourTown.isClosed())
            System.out.println(">>>>>>>>>> Error in tiny district at 18: should be open.");
        System.out.print("Final Tiny" + ourTown);
        System.out.println("Lots were closed for " + ourTown.getClosedMinutes()
                + " min. in tiny district.");
    }

    /**
     * Test District class with three lots.
     */
    public static void testDistrict() {
        System.out.println("Tier 7: Testing a District with 3 normal parking lots...................................");
        District airport = new District();
        int brown = airport.add(new ParkingLot("brown", 10));
        int green = airport.add(new ParkingLot("green", 15));
        int black = airport.add(new ParkingLot("black", 12));
        for (int i = 0; i < 7; i++) {
            airport.markVehicleEntry(brown, i);
            airport.markVehicleEntry(green, i);
            airport.markVehicleEntry(green, i);
            airport.markVehicleEntry(black, i);
            if (airport.isClosed())
                System.out.println(">>>>>>>>>> Error: airport closed at time 7.");
        }
        if (airport.getVehiclesParkedInDistrict() != 28)
            System.out.println(">>>>>>>>>> Expected 28 vehicles in airport");
        System.out.println("Airport at time 7:");
        System.out.print(airport);
        System.out.println();

        airport.markVehicleEntry(brown, 8);
        if (airport.isClosed())
            System.out.println(">>>>>>>>>> Error: airport closed at time 8.");
        System.out.println("Airport at time 8:");
        System.out.print(airport);
        System.out.println();

        ParkingLot blackLot = airport.getLot(black);
        if (!blackLot.getName().equals("black"))
            System.out.println(">>>>>>>>>> Black lot has the wrong name.");
        if (blackLot.getVehiclesInLot() != 7)
            System.out.println(">>>>>>>>>> Expecting 7 vehicles in black lot at time 8.");
        airport.markVehicleEntry(black, 9);
        airport.markVehicleEntry(black, 10);
        airport.markVehicleEntry(black, 10);
        if (blackLot.getVehiclesInLot() != 10)
            System.out.println(">>>>>>>>>> Expecting 10 vehicles in black lot at time 10.");
        if (!airport.isClosed())
            System.out.println(">>>>>>>>>> Error: airport not closed at time 10.");
        System.out.println("Airport at time 10:");
        System.out.print(airport);
        System.out.println();
        if (airport.getVehiclesParkedInDistrict() != 32)
            System.out.println(">>>>>>>>>> Expected 32 vehicles in airport");
    }


    /**
     * Test a number of vehicles entering and exiting three lots in a district.
     * Entry and exit is controlled by a string of digits in which 1-3
     * represents a vehicle entering lots 0-2 and 4-6 represents an exit.
     * Time passes with one minute between each entry or exit.
     * To help with debugging, 's' can be put in the string to print the
     * status at that time; add more output to the status report if you
     * find it helpful.
     */
    static void testHeavyUsage() {
        System.out.println("Tier 8: Testing heavier usage...........................................................");
        District town = new District();
        town.add(new ParkingLot("pink", 25));
        town.add(new ParkingLot("blue", 30));
        town.add(new ParkingLot("gray", 10));

        String usage = "111243432523124321322156421123666324121345534534221" +
                // fill up lots
                "1111111122111122221111211122222233222222" +
                // a period of constant in/out traffic
                "4141414141525252525252636363636363636363636363636363636414141" +
                // a number of vehicles leave
                "55556556655454645546454446" +
                // random behavior at the end of the period
                "113232312434524621525241255146254245654241422211122661212321232";
        int min = 60;             // no activity until an hour into the day
        for (int i = 0; i < usage.length(); i++) {
            char cmd = usage.charAt(i);
            if (cmd <= '3')
                town.markVehicleEntry((int) (cmd - '0' - 1), min);
            else
                town.markVehicleExit((int) (cmd - '3' - 1), min, 0);
            min++;
        }
        if (town.getVehiclesParkedInDistrict() != 47)
            System.out.println(">>>>>>>>>> Expected 47 vehicles in town");
        if(town.getClosedMinutes()!=42)
            System.out.println(">>>>>>>>>> At end of day, all lots should be closed 42 minutes. They were closed "
                    + town.getClosedMinutes() + " min.");

        System.out.println("Final " + town);
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    //test paid parking


    /**
     * Test creating a parking lot with a single space.
     */
    public static void testSmallLotPaid() {
        System.out.println("Tier 9: Testing paid parking spot.......................................................");
        PayParkingLot driveway = new PayParkingLot("reserved", 1, 1.5);
        PayParkingLot unnamed = new PayParkingLot( 1, 1.5);
        if (!driveway.getName().equals("reserved"))
            System.out.println(">>>>>>>>>> .getName not working");
        if (!unnamed.getName().equals("test"))
            System.out.println(">>>>>>>>>> default name is not working");
        if (Math.abs(ParkingLot.CLOSED_THRESHOLD - 80) > 0.01)
            System.out.println(">>>>>>>>>> Incorrect threshold for closed lot.");
        if (driveway.isClosed())
            System.out.println(">>>>>>>>>> Empty driveway is closed.");
        int id = driveway.markVehicleEntry(5);
        if (!driveway.isClosed())
            System.out.println(">>>>>>>>>> Driveway with something in it is not closed.");
        if (!driveway.toString().equals("Status for reserved parking lot: 1 vehicles (CLOSED) Money collected: $0.00"))
            System.out.println(">>>>>>>>>> Incorrect status: " + driveway);
        driveway.markVehicleExit(400, -1);
        if (driveway.getVehiclesInLot() != 1)
            System.out.println(">>>>>>>>>> An invalid id should be ignored.");
        driveway.markVehicleExit(400, id);
        if (driveway.getVehiclesInLot() != 0)
            System.out.println(">>>>>>>>>> Empty driveway should have no vehicles in it.");
        if (driveway.getClosedMinutes() != 395)
            System.out.println(">>>>>>>>>> Wrong number of minutes while sleeping overnight.");
        if (Math.abs(driveway.getProfit() - 9.5 ) > 0.01 )
            System.out.println(">>>>>>>>>> Wrong profit (" + driveway.getProfit()+"). It should be $9.50. Do not charge for initial time.");
        System.out.println(driveway);
    }

    /**
     * Test creating a parking lot with a single space.
     */
    public static void testLotPaidWithGlitches() {
        System.out.println("Tier 10: Testing paid parking lot........................................................");
        PayParkingLot lot = new PayParkingLot("Glitchy", 10);
        for (int i = 1; i < 9; i++) {
            lot.markVehicleEntry(1);
        }

        if (!lot.isClosed())
            System.out.println(">>>>>>>>>> Error: lot is NOT closed at time 8.");

        int car9 = lot.markVehicleEntry(9);
        int car10 = lot.markVehicleEntry(10);
        int car11 = lot.markVehicleEntry(10);
        if (lot.getVehiclesInLot() != 10)
            System.out.println(">>>>>>>>>> Error: lot should have 10 vehicle at time 10.");

        if (car11 != -1)
            System.out.println(">>>>>>>>>> Error: a car was marked as entered, but it was not");

        lot.markVehicleExit(25, car10);
        lot.markVehicleExit(25, car10);
        if (lot.getVehiclesInLot() != 9)
            System.out.println(">>>>>>>>>> Error: lot should have 9 vehicles at time 9.");

        lot.markVehicleExit(8, car9);
        if (lot.getVehiclesInLot() != 9)
            System.out.println(">>>>>>>>>> Error: car left before ticket was paid.");

        if (Math.abs(lot.getProfit() - 0 ) > 0.01 )
            System.out.println(">>>>>>>>>> Wrong profit (" + lot.getProfit()+"). It should be $0. Car charged at invalid time");

        lot.markVehicleExit(95, car9);
        if (lot.getVehiclesInLot() != 8)
            System.out.println(">>>>>>>>>> Error: car should paid and be out of in the parking lot.");

        if (Math.abs(lot.getProfit() - 2.36 ) > 0.01 )
            System.out.println(">>>>>>>>>> Wrong profit (" + lot.getProfit()+"). Car tried to paid before exiting. It should be $2.36");

        int car12 = lot.markVehicleEntry(100);
        if (lot.getVehiclesInLot() != 9)
            System.out.println(">>>>>>>>>> Error: car should be driving to exit of the parking lot.");

        lot.markVehicleExit(135, car12);
        if (Math.abs(lot.getProfit() - 3.03 ) > 0.01 )
            System.out.println(">>>>>>>>>> Wrong profit (" + lot.getProfit()+"). Car should have paid. It should still be $3.03");

        if (lot.getVehiclesInLot() != 8)
            System.out.println(">>>>>>>>>> Error: car paid should be out of the parking lot.");

        System.out.println(lot);

    }


    public static void testPaidDistrict() {
        System.out.println("Tier 11: Testing a District with 2 normal, and 3 paid parking lots.......................");
        DecimalFormat format = new DecimalFormat("#.##");

        District town = new District();
        town.add(new ParkingLot("pink", 2));
        town.add(new ParkingLot("blue", 3));
        town.add(new PayParkingLot("red paid", 2, 3));
        town.add(new PayParkingLot("gray paid", 3, 2));
        town.add(new PayParkingLot("green paid", 4));

        //many entries
        town.markVehicleEntry(0, 1);
        town.markVehicleEntry(1, 2);
        int car1 = town.markVehicleEntry(2, 3);
        int car2 = town.markVehicleEntry(3, 4);
        int car3 = town.markVehicleEntry(4, 5);
        town.markVehicleEntry(1, 6);
        town.markVehicleEntry(1, 7);
        town.markVehicleEntry(0, 8);
        town.markVehicleEntry(2, 9);
        town.markVehicleEntry(2, 10);
        town.markVehicleEntry(3, 11);
        town.markVehicleEntry(3, 11);
        town.markVehicleEntry(4, 11);
        town.markVehicleEntry(4, 11);
        int car4 = town.markVehicleEntry(4, 11);

        town.markVehicleExit(0, 25, 0);
        town.markVehicleExit(1, 26,0);
        town.markVehicleExit(2, 17, car1);
        town.markVehicleExit(3, 26, car2);
        town.markVehicleExit(4, 40, car3);
        town.markVehicleExit(4, 30, car4);


        if (town.getVehiclesParkedInDistrict() != 9)
            System.out.println(">>>>>>>>>> Expected 9 vehicles in town");

        if(town.getClosedMinutes()!=14)
            System.out.println(">>>>>>>>>> At end of day, all lots should be closed 14 minutes. They were closed "
                    + town.getClosedMinutes() + " min.");

        if(Math.abs(town.getTotalMoneyCollected()- 0.9) > 0.01)
            System.out.println(">>>>>>>>>> At end of day, $0.90 should have been collect. $"
                    + format.format(town.getTotalMoneyCollected() )+ " was collected.");

        String result = "District status:\n" +
                "Status for pink parking lot: 1 vehicles (50%)\n" +
                "Status for blue parking lot: 2 vehicles (66.7%)\n" +
                "Status for red paid parking lot: 1 vehicles (50%) Money collected: $0.00\n" +
                "Status for gray paid parking lot: 2 vehicles (66.7%) Money collected: $0.23\n" +
                "Status for green paid parking lot: 3 vehicles (75%) Money collected: $0.67\n";
        if(!result.equals(town.toString()))
            System.out.println(">>>>>>>>>> District toString is incorrect. It should be: " + town);
        else
            System.out.println("Final Result---------------------- \n" + town);
    }


}
