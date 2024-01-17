import java.sql.SQLOutput;
import java.util.Scanner;

public class PaintCalc {
    public static String validate(String a){
        String ans = a;
        Scanner reader = new Scanner(System.in);
        while (!ans.equals("Y") && !ans.equals("N")) {
            System.out.println("Please enter \"Y\" or \"N\"");
            ans = reader.nextLine().toUpperCase();
        }
        return ans;
    }
    public static void main(String[] args) {
        double potPrice=21.00d;
        double potCover=32.5d;

        Scanner reader = new Scanner(System.in);
        //Check price is correct
        System.out.println("Is the price per pot £" + potPrice + "? Y/N");
        String priceCheck = reader.nextLine().toUpperCase();

        priceCheck = validate(priceCheck);

        if (priceCheck.equals("N")) {
            System.out.println("Please type the price per pot in pounds:");
            potPrice = reader.nextDouble();
        }


        //Check coverage is correct
        System.out.println("Does the paint pot cover " + potCover + " square metres? Y/N");
        String coverCheck = reader.nextLine().toUpperCase();

        coverCheck = validate(coverCheck);

        if (coverCheck.equals("N")) {
            System.out.println("Please enter the area a single pot can cover in square metres:");
            potCover = reader.nextDouble();
        }

        //Surface details
        System.out.println("How many surfaces are being painted?");
        byte surfaces = reader.nextByte();

        System.out.println("Complete the following for each surface to be painted:");

        String rectCheck = "";
        double x;
        double y;
        double totArea = 0;

        //Calc wall area
        for (byte i=1; i<=surfaces; i++) {
            System.out.println("Is this surface rectangular? Y/N");
            rectCheck = reader.nextLine().toUpperCase();
            rectCheck = validate(rectCheck);

            if (rectCheck.equals("Y")) {
                System.out.println("Enter the width of the surface in metres:");
                x = reader.nextDouble();
                System.out.println("Enter the length of the surface in metres:");
                y = reader.nextDouble();
                totArea = totArea + (x * y);
            } else {
                System.out.println("Please enter the area of the surface in square metres:");
                totArea = totArea + reader.nextDouble();
            }
        }

        //Account for windows/doors
        String doorCheck = "";
        System.out.println("Are there any areas of these surfaces which do not require painting? Y/N");
        doorCheck = reader.nextLine().toUpperCase();
        doorCheck = validate(doorCheck);

        byte clean;
        if (doorCheck.equals("Y")) {
            System.out.println("How many areas do not require painting?");
            clean = reader.nextByte();
            for (byte i=1; i<=clean; i++){
                //get clean areas
            }
        }

        //Calc final values
        double tins;
        double price;

        tins = Math.ceil(totArea/potCover);
        price = tins * potPrice;

        System.out.println("The total area to be painted is " + totArea + " square metres, which will require " + tins + " tins of paint, at a total cost of £" + price);
    }
}

//Can change "how many x" questions to use vectors, maybe worse though as will need to keep asking if there are more