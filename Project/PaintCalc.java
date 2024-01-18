import java.sql.SQLOutput;
import java.util.Scanner;
import java.math.*;

public class PaintCalc {
    public static String validate(String a) {
        String ans = a;
        Scanner reader = new Scanner(System.in);
        while (!ans.equals("Y") && !ans.equals("N")) {
            System.out.println("Please enter \"Y\" or \"N\"");
            ans = reader.nextLine().toUpperCase();
        }
        return ans;
    }

    public static void main(String[] args) {
        double potPrice = 21.00d;
        double potCover = 32.5d;

        Scanner reader = new Scanner(System.in);
        //Check price is correct
        System.out.println("Is the price per pot £" + potPrice + "? Y/N");
        String priceCheck = reader.nextLine().toUpperCase();

        priceCheck = validate(priceCheck);


        //If incorrect input is entered first, works fine. If correct input entered first (double or int), needs to be entered twice
        if (priceCheck.equals("N")) {
            System.out.println("Please type the price per pot in pounds: ");
            potPrice = -1d;
            do {
                while (!reader.hasNextDouble()) {
                    System.out.println("Please enter a number larger than, or equal to 0: ");
                    reader.next();
                }
                if (reader.nextDouble() < 0) {
                    System.out.println("Please enter a number larger than, or equal to 0: ");
                }
                potPrice = Double.parseDouble(reader.next());
            } while (potPrice < 0);
        }


        //Check coverage is correct
        System.out.println("Does the paint pot cover " + potCover + " square metres? Y/N");
        String coverCheck = reader.nextLine().toUpperCase();

        coverCheck = validate(coverCheck);

        if (coverCheck.equals("N")) {
            System.out.println("Please enter the area a single pot can cover in square metres:");
            potCover = 0d;
            while (potCover <= 0) {
                while (!reader.hasNextDouble()) {
                    System.out.println("Please enter a number larger than 0: ");
                    reader.next();
                }
                if (reader.nextDouble() <= 0) {
                    System.out.println("Please enter a number larger than 0: ");
                    }
                potCover = reader.nextDouble();
            }
        }

        //Surface details
        System.out.println("How many surfaces are being painted?");
        byte surfaces = 0;

        while (surfaces <= 0) {
            while (!reader.hasNextByte()) {
                System.out.println("Please enter a whole, positive number: ");
                reader.next();
            }
            if (reader.nextByte() <= 0) {
                System.out.println("Please enter a whole, positive number: ");
            }
            surfaces = reader.nextByte();
        }


        System.out.println("Complete the following for each surface to be painted:");

        String rectCheck;
        double x = 0;
        double y = 0;
        double totArea = 0;

        //Calc wall area
        for (byte i = 1; i <= surfaces; i++) {
            System.out.println("Is this surface rectangular? Y/N");
            rectCheck = reader.nextLine().toUpperCase();
            rectCheck = validate(rectCheck);

            if (rectCheck.equals("Y")) {

                //Width
                System.out.println("Enter the width of the surface in metres: ");
                while (x <= 0) {
                    while (!reader.hasNextDouble()) {
                        System.out.println("Please enter a value larger than 0: ");
                        reader.next();
                    }
                    if (reader.nextDouble() <= 0) {
                        System.out.println("Please enter a value larger than 0: ");
                    }
                    x = reader.nextDouble();
                }

                //Length
                System.out.println("Enter the length of the surface in metres:");
                while (y <= 0) {
                    while (!reader.hasNextDouble()) {
                        System.out.println("please enter a value larger than 0: ");
                        reader.next();
                    }
                    if (reader.nextDouble() <= 0) {
                        System.out.println("Please enter a value larger than 0: ");
                    }
                    y = reader.nextDouble();
                }

                totArea = totArea + (x * y);
            } else {
                System.out.println("Please enter the area of the surface in square metres:");
                double area = 0;
                while (area <= 0) {
                    while (!reader.hasNextDouble()) {
                        System.out.println("Please enter a value larger than 0: ");
                        reader.next();
                    }
                    if (reader.nextDouble() <= 0) {
                        System.out.println("Please enter a value larger than 0: ");
                    }
                    area = reader.nextDouble();
                    totArea = totArea + area;
                }
            }
        }

        //Account for windows/doors
        String doorCheck;
        double doorArea = 0;
        System.out.println("Are there any areas of these surfaces which do not require painting? Y/N");
        doorCheck = reader.nextLine().toUpperCase();
        doorCheck = validate(doorCheck);

        byte clean = 0;
        if (doorCheck.equals("Y")) {
            do {
                System.out.println("How many areas do not require painting?");

                while (clean <= 0) {
                    while (!reader.hasNextByte()) {
                        System.out.println("Please enter a whole, positive number: ");
                        reader.next();
                    }
                    if (reader.nextByte() <= 0) {
                        System.out.println("Please enter a whole, positive number: ");
                    }
                    clean = reader.nextByte();
                }


                System.out.println("Answer the following for each area:");
                for (byte i = 1; i <= clean; i++) {
                    System.out.println("Is the area rectangular? Y/N");
                    rectCheck = reader.nextLine().toUpperCase();
                    rectCheck = validate(rectCheck);

                    if (rectCheck.equals("Y")) {
                        //Width
                        System.out.println("Enter the width of the surface in metres: ");
                        while (x <= 0) {
                            while (!reader.hasNextDouble()) {
                                System.out.println("Please enter a value larger than 0: ");
                                reader.next();
                            }
                            if (reader.nextDouble() <= 0) {
                                System.out.println("Please enter a value larger than 0: ");
                            }
                            x = reader.nextDouble();
                        }

                        //Length
                        System.out.println("Enter the length of the surface in metres:");
                        while (y <= 0) {
                            while (!reader.hasNextDouble()) {
                                System.out.println("please enter a value larger than 0: ");
                                reader.next();
                            }
                            if (reader.nextDouble() <= 0) {
                                System.out.println("Please enter a value larger than 0: ");
                            }
                            y = reader.nextDouble();
                        }

                        doorArea = doorArea + (x * y);
                    } else {
                        System.out.println("Please enter the area of the surface in square metres:");
                        double area = 0;
                        while (area <= 0) {
                            while (!reader.hasNextDouble()) {
                                System.out.println("Please enter a value larger than 0: ");
                                reader.next();
                            }
                            if (reader.nextDouble() <= 0) {
                                System.out.println("Please enter a value larger than 0: ");
                            }
                            area = reader.nextDouble();
                            doorArea = doorArea - area;
                        }
                    }
                }
                if (doorArea >= totArea) {
                    System.out.println("The area you have entered to not be painted is larger than the area entered to be painted. Please re-enter unpainted areas.");
                }
            } while (doorArea >= totArea);
        }


        //Calc final values and output


        double tins = 0;
        double price = 0;

        tins = Math.ceil(totArea/potCover);
        int tinsF = (int) tins;

        price = tins * potPrice;

        switch (tinsF) {
            case 1:
                System.out.println("The total area to be painted is " + totArea + " square metres, which will require " + tinsF + " tin of paint, at a total cost of £" + price);
                break;
            default:
                System.out.println("The total area to be painted is " + totArea + " square metres, which will require " + tinsF + " tins of paint, at a total cost of £" + price);
                break;
        }
    }
}