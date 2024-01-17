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
        System.out.println("Is the price per pot " + potPrice + "? Y/N");
        String priceCheck = reader.nextLine().toUpperCase();

        priceCheck = validate(priceCheck);

        if (priceCheck.equals("N")) {
            System.out.println("Please type the price per pot: £");
            potPrice = reader.nextDouble();
        }


        //Check coverage is correct
        System.out.println("Does the paint pot cover " + potCover + "m^2? Y/N");
        String coverCheck = reader.nextLine().toUpperCase();

        coverCheck = validate(coverCheck);

        if (coverCheck.equals("N")) {
            System.out.println("Please enter the area a single pot can cover in m^2:");
            potCover = reader.nextDouble();
        }

        //Surface details
        System.out.println("How many surfaces are being painted?");
        byte surfaces = reader.nextByte();

        System.out.println("Complete the following for each surface to be painted:");

        String rectCheck = "";
        double x = 0;
        double y = 0;

        for (byte i=1; i<=surfaces; i++) {
            System.out.println("Is this surface rectangular? Y/N");
            rectCheck = reader.nextLine().toUpperCase();
            rectCheck = validate(rectCheck);

            if (rectCheck.equals("Y")) {
                System.out.println();
            }
        }

    }
}

/*
Scanner reader = new Scanner(System.in);
            System.out.println("Your prompt here");
            String input = reader.nextLine();
 */