//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        byte a=10;
        byte b=5;
        /*Prob 1*/
        System.out.println(a*b);
        /*Prob 2*/
        System.out.print(a/b);
        /*Prob 3*/
        int x=0;
        x++;

        /*Prob 4*/
        x+=5;

        System.out.println();


        /*Next thing*/
        String thanks="Thank you,";
        String name="miguel!";
        String order="Your order number is #";
        int previousOrder=715;

        System.out.println(thanks + " " + name.toUpperCase());
        System.out.println(order + (++previousOrder));

        x=51;
        int y=49;
        if (x==y) {
            System.out.println("Equal");
        } else {
            System.out.println("Unequal");
        }

        if (x==y) {
            System.out.println("1");
        } else if (x>y) {
            System.out.println("2");
        } else {
            System.out.println("3");
        }


        int day = 2;
        switch (day) {
            case 1:
                System.out.println("Saturday");
                break;
            case 2:
                System.out.println("Sunday");
                break;
        }
        day=4;
        switch(day){
            case 1:
                System.out.println("Saturday");
                break;
            case 2:
                System.out.println("Sunday");
                break;
            default:
                System.out.println("Weekend");
        }

        int i=1;
        while(i<6){
            System.out.println(i);
            i++;
        }
        do{
            System.out.println(i);
            i++;
        } while (i<6);

        String weekday="";
        for (day=3; day<=31; day+=3){
            switch (day%7) {
                case 1:
                    weekday = "Monday";
                    break;
                case 2:
                    weekday = "Tuesday";
                    break;
                case 3:
                    weekday = "Wednesday";
                    break;
                case 4:
                    weekday = "Thursday";
                    break;
                case 5:
                    weekday = "Friday";
                    break;
                case 6:
                    weekday = "Saturday";
                    break;
                case 0:
                    weekday = "Sunday";
                    break;
            }
            System.out.println("Day " + day + ", " + weekday);
        }
    }
}