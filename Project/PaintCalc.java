import java.sql.SQLOutput;
import java.util.Scanner;

public class PaintCalc {
    public static void main(String[] args) {
        double potPrice=21.00d;
        double potCover=32.5d;

        Scanner reader = new Scanner(System.in);
        //Check price is correct
        System.out.println("Is the price per pot " + potPrice + "? Y/N");
        String priceCheck = reader.nextLine().toUpperCase();

        while (!priceCheck.equals("Y") && !priceCheck.equals("N")){
            System.out.println("Please enter \"Y\" or \"N\"");
            priceCheck = reader.nextLine().toUpperCase();
        }

        if (priceCheck.equals("N")) {
            System.out.println("Please type the price per pot: £");
            potPrice = reader.nextDouble();
        }


        //Check coverage is correct
        System.out.println("Does the paint pot cover " + potCover + "m^2? Y/N");
        String coverCheck = reader.nextLine().toUpperCase();

        while   (!coverCheck.equals("Y") && !coverCheck.equals("N")){
            System.out.println("Please enter \"Y\" or \"N\"");
            coverCheck = reader.nextLine().toUpperCase();
        }

        if (coverCheck.equals("N")) {
            System.out.println("Please enter the area a single pot can cover in m^2:");
            potCover = reader.nextDouble();
        }

        //Surface details
        System.out.println("How many surfaces are being painted?");
        byte surfaces = reader.nextByte();

        for (byte i=1; i<=surfaces; i++) {

        }
        System.out.println("Hellooooo");
    }
}

/*
Scanner reader = new Scanner(System.in);
            System.out.println("Your prompt here");
            String input = reader.nextLine();
 */