import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        String ip = "163.151.36.51";
        int maska=23;
        int iloscDopodziału = 15;
        boolean A_Z = false; // od Z-A true , A-Z false


        //Przekształcenie ip na binarny

        System.out.println("Adres ip ");
        String binary = toBinary(ip);
        //System.out.println(withDots(binary));
        System.out.println(ip);

        System.out.println();

        //Adres podsieci
//        System.out.println("Adres podsieci w binarnym");
        String siec = podsiec(binary,maska);
//        System.out.println(withDots(siec));

        System.out.println("Adres podsieci w dziesiętnym");
        String siecWithDots = withDots(siec);
        System.out.println(binaryToText(siecWithDots));

        System.out.println();
        System.out.println();

        //Adres rozgłoszeniowy
//        System.out.println("Adres rozgłoszeniowy w binarnym");
        String rozgloszeniowy = rozgloszeniowy(binary,maska);
//        System.out.println(withDots(rozgloszeniowy));

        System.out.println("Adres rozgłoszeniowy w dzieśiętnym");
        System.out.println(binaryToText(withDots(rozgloszeniowy)));


        //DRUGA CZĘŚC

        System.out.println();



        List<Interfejs> interfejs = interfejsy(iloscDopodziału);

//        for (Interfejs in:interfejs) {
//            System.out.println(in.getNazwa() + " " + in.getIlosc() + " wymagana ilosć miejsca " + in.getWymaganaPojemnosc()
//                    +" ilosć zer do maski " + in.getIloscZer() + " Adres maski " + in.maska(in.getIloscZer()) + "/" + String.valueOf(32- in.getIloscZer()));
//
//        }

        System.out.println();

        System.out.println("Pojemność sieci to ");

        System.out.println("Pojemność sieci to " +pojemnoscSieci(maska,interfejs));
        System.out.println();

        String podsieci = binaryToText(siecWithDots);
        System.out.println("Pierwsza sieć do przyznania " + podsieci);
        List<Interfejs> finalna = podzialSieci(podsieci,interfejs,A_Z);
        finalna.forEach(interfejs1 -> System.out.println(interfejs1 + "  |  "  + " Adres maski " + interfejs1.maska(interfejs1.getIloscZer()) + "/"
                + String.valueOf(32- interfejs1.getIloscZer())));


        // Podział

        System.out.println();


        System.out.println("Pierwsza podsieć do przydziału " + binaryToText(siecWithDots));
        podzial(finalna,binaryToText(siecWithDots));



    }


    public static String toBinary (String number){

        String[] arr = number.split("\\.");

        String output ="";
        String join ="";
        for (int i = 0 ; i < arr.length ; i ++) {

            String bin = Integer.toBinaryString(Integer.parseInt(arr[i]));
            output = String.format("%8s", bin).replace(' ', '0');
            join+=output;
        }

        return join;

    }

    public static String podsiec(String binary, int limit){
        String result ="";
        String arr[]= binary.split("");
        for (int i = 0; i < limit; i++) {
            result+=arr[i];
        }
        String czescHostowa="";
        for (int i = 0; i < 32-limit; i++) {
            czescHostowa+="0";

        }

        return result+czescHostowa;
    }

    public static String rozgloszeniowy(String binary, int limit){

        String result ="";
        String arr[]= binary.split("");
        for (int i = 0; i < limit; i++) {
            result+=arr[i];
        }
        String czescHostowa="";
        for (int i = 0; i < 32-limit; i++) {
            czescHostowa+="1";

        }

        return result+czescHostowa;

    }


    public static String withDots (String binary){
        String[] arr = binary.split("");
        String result ="";

        for (int i = 0; i < arr.length; i++) {
            result+=arr[i];
            if (((i+1) % 8 == 0)){
                result+=".";
            }

        }

        return result;

    }

    public static String binaryToText(String binary) {

        String[] arr = binary.split("\\.");

        int decimal=0;
        String result ="";

        for (int i = 0; i < arr.length; i++) {
            decimal=Integer.parseInt(arr[i],2);
            result+= String.valueOf(decimal) + " ";


        }
        return result;

    }

    //Część interfejsy

    public static List<Interfejs> interfejsy(int ilosc){

        List<Interfejs> lista= new ArrayList<Interfejs>();
        Scanner scanner = new Scanner(System.in);

        while (ilosc>0){
            System.out.println("Podaj nazwe");
            String nazwa= scanner.next();
            System.out.println("Podaj wartosc do zaadresowania");
            int ile = scanner.nextInt();
            lista.add(new Interfejs(nazwa,ile));
            ilosc--;
        }

        return lista;

    }

    public static int pojemnoscSieci(int maska, List<Interfejs> interfejs){

        int result = (int) Math.pow(2,(32- maska));
        int wymSieci= interfejs.stream().mapToInt(Interfejs::getWymaganaPojemnosc).sum();
        System.out.println("Suma pojemności wszystkich podsieci docelowych "+ wymSieci);

        if (result>=wymSieci){
            System.out.println("wszystkie sieci docelowe zmieszczą się w sieci źródłowej");
        }
        else {
            System.out.println("wszystkie sieci docelowe NIE zmieszczą się w sieci źródłowej");
        }

        return result;
    }

    public static List<Interfejs> podzialSieci (String podsiec, List<Interfejs> interfejsLista,boolean kolejność){
        String[] arr = podsiec.split(" ");

//        List <Interfejs> posortowanaLiterkami = interfejsLista.stream().sorted(Comparator.comparing(Interfejs::getNazwa)).collect(Collectors.toList());
//        List<Interfejs> posortowanaWielkością = interfejsLista.stream().sorted(Comparator.comparing(Interfejs::getIloscWymagana)).collect(Collectors.toList());



        List<Interfejs> posortowana = new ArrayList<>();
        if (kolejność == true) {
            Comparator<Interfejs> comparator = null;
            comparator = Comparator.comparing(Interfejs::getWymaganaPojemnosc).reversed();
            comparator = comparator.thenComparing(Comparator.comparing(Interfejs::getNazwa).reversed());
            posortowana= interfejsLista.stream().sorted(comparator).collect(Collectors.toList());
        }
        else {
            Comparator<Interfejs> comparator = null;
            comparator = Comparator.comparing(Interfejs::getWymaganaPojemnosc).reversed();
            comparator = comparator.thenComparing(Comparator.comparing(Interfejs::getNazwa));
            posortowana= interfejsLista.stream().sorted(comparator).collect(Collectors.toList());

        }

        return posortowana;

    }

    public static void podzial(List<Interfejs> interfejs, String pierwsza){

        List <String> arr = new ArrayList<>();
        String[]split = pierwsza.split(" ");



        interfejs.forEach(interfejs1 -> {
            String dane = split[2];
            String dane2 =split[3];
            int doPrzerobki =0;
            String[] rozgloszeniowy ;

            switch (interfejs1.getWymaganaPojemnosc()){

                case 4:
                    doPrzerobki = Integer.parseInt(dane2);
                    doPrzerobki+= 4;
                    if (doPrzerobki >= 255){
                        split[2] = String.valueOf(Integer.parseInt(split[2]) + 1);
                        split[3] = String.valueOf(0);

                        System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        if ((Integer.parseInt(rozgloszeniowy[3].replace("]","")) == 0 )){
                            rozgloszeniowy[3] = "255";

                        }



                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]","")) - 1 );
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));
                    }  else {
                        split[3] = String.valueOf(doPrzerobki);


                        System.out.println("Kolejna podsieć " + interfejs1.getNazwa() + " to " + Arrays.toString(split));

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]", "")) - 1);
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa() + " to " + Arrays.toString(rozgloszeniowy));
                    }
                    break;

                case 8:
                    doPrzerobki = Integer.parseInt(dane2);
                    doPrzerobki+= 8;
                    if (doPrzerobki >= 255){
                        split[2] = String.valueOf(Integer.parseInt(split[2]) + 1);
                        split[3] = String.valueOf(0);

                        System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        if ((Integer.parseInt(rozgloszeniowy[3].replace("]","")) == 0 )){
                            rozgloszeniowy[3] = "255";

                        }



                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]","")) - 1 );
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));
                    }  else {
                        split[3] = String.valueOf(doPrzerobki);


                        System.out.println("Kolejna podsieć " + interfejs1.getNazwa() + " to " + Arrays.toString(split));

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]", "")) - 1);
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa() + " to " + Arrays.toString(rozgloszeniowy));
                    }
                    break;

                case 16:
                    doPrzerobki = Integer.parseInt(dane2);
                    doPrzerobki+= 16;
                    if (doPrzerobki >= 255){
                        split[2] = String.valueOf(Integer.parseInt(split[2]) + 1);
                        split[3] = String.valueOf(0);

                        System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        if ((Integer.parseInt(rozgloszeniowy[3].replace("]","")) == 0 )){
                            rozgloszeniowy[3] = "255";

                        }



                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]","")) - 1 );
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));
                    }  else {
                        split[3] = String.valueOf(doPrzerobki);


                        System.out.println("Kolejna podsieć " + interfejs1.getNazwa() + " to " + Arrays.toString(split));

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]", "")) - 1);
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa() + " to " + Arrays.toString(rozgloszeniowy));
                    }
                    break;

                case 32:
                    doPrzerobki = Integer.parseInt(dane2);
                    doPrzerobki+= 32;
                    if (doPrzerobki >= 255){
                        split[2] = String.valueOf(Integer.parseInt(split[2]) + 1);
                        split[3] = String.valueOf(0);

                        System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        if ((Integer.parseInt(rozgloszeniowy[3].replace("]","")) == 0 )){
                            rozgloszeniowy[3] = "255";

                        }



                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]","")) - 1 );
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));
                    }  else {
                        split[3] = String.valueOf(doPrzerobki);


                        System.out.println("Kolejna podsieć " + interfejs1.getNazwa() + " to " + Arrays.toString(split));

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]", "")) - 1);
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa() + " to " + Arrays.toString(rozgloszeniowy));
                    }
                    break;

                case 64:
                    doPrzerobki = Integer.parseInt(dane2);
                    doPrzerobki+= 64;
                    if (doPrzerobki >= 255){
                        split[2] = String.valueOf(Integer.parseInt(split[2]) + 1);
                        split[3] = String.valueOf(0);

                        System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        if ((Integer.parseInt(rozgloszeniowy[3].replace("]","")) == 0 )){
                            rozgloszeniowy[3] = "255";

                        }



                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]","")) - 1 );
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));
                    }  else {
                        split[3] = String.valueOf(doPrzerobki);


                        System.out.println("Kolejna podsieć " + interfejs1.getNazwa() + " to " + Arrays.toString(split));

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]", "")) - 1);
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa() + " to " + Arrays.toString(rozgloszeniowy));
                    }
                    break;

                case 128:
                    doPrzerobki = Integer.parseInt(dane2);
                    doPrzerobki+= 128;
                    if (doPrzerobki >= 255){
                        split[2] = String.valueOf(Integer.parseInt(split[2]) + 1);
                        split[3] = String.valueOf(0);

                        System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        if ((Integer.parseInt(rozgloszeniowy[3].replace("]","")) == 0 )){
                            rozgloszeniowy[3] = "255";

                        }



                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]","")) - 1 );
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));
                    }  else {
                        split[3] = String.valueOf(doPrzerobki);


                        System.out.println("Kolejna podsieć " + interfejs1.getNazwa() + " to " + Arrays.toString(split));

                        rozgloszeniowy = Arrays.toString(split).split(", ");


                        rozgloszeniowy[3] = String.valueOf(Integer.parseInt(rozgloszeniowy[3].replace("]", "")) - 1);
                        System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa() + " to " + Arrays.toString(rozgloszeniowy));
                    }
                    break;

                case 256:
                    doPrzerobki = Integer.parseInt(dane);
                    doPrzerobki += 1;
                    split[2] = String.valueOf(doPrzerobki);

                    System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );


                    rozgloszeniowy = Arrays.toString(split).split(", ");
                    rozgloszeniowy[2] = String.valueOf(Integer.parseInt(rozgloszeniowy[2]) - 1 );
                    rozgloszeniowy[3] = "255";
                    System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));


                    break;

                case 512:
                    doPrzerobki = Integer.parseInt(dane);
                    doPrzerobki += 2;
                    split[2] = String.valueOf(doPrzerobki);




                    System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                    rozgloszeniowy = Arrays.toString(split).split(", ");
                    rozgloszeniowy[2] = String.valueOf(Integer.parseInt(rozgloszeniowy[2]) - 1 );
                    rozgloszeniowy[3] = "255";
                    System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));

                    break;

                case 1024:



                    doPrzerobki = Integer.parseInt(dane);
                    doPrzerobki += 4;
                    split[2] = String.valueOf(doPrzerobki);




                    System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                    rozgloszeniowy = Arrays.toString(split).split(", ");
                    rozgloszeniowy[2] = String.valueOf(Integer.parseInt(rozgloszeniowy[2]) - 1 );
                    rozgloszeniowy[3] = "255";
                    System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));



                    break;

                case 2048:

                    doPrzerobki = Integer.parseInt(dane);
                    doPrzerobki += 8;
                    split[2] = String.valueOf(doPrzerobki);




                    System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                    rozgloszeniowy = Arrays.toString(split).split(", ");
                    rozgloszeniowy[2] = String.valueOf(Integer.parseInt(rozgloszeniowy[2]) - 1 );
                    rozgloszeniowy[3] = "255";
                    System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));
                    break;


                case 4096:
                    doPrzerobki = Integer.parseInt(dane);
                    doPrzerobki += 16;
                    split[2] = String.valueOf(doPrzerobki);




                    System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                    rozgloszeniowy = Arrays.toString(split).split(", ");
                    rozgloszeniowy[2] = String.valueOf(Integer.parseInt(rozgloszeniowy[2]) - 1 );
                    rozgloszeniowy[3] = "255";
                    System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));
                    break;

                case 8192:
                    doPrzerobki = Integer.parseInt(dane);
                    doPrzerobki += 32;
                    split[2] = String.valueOf(doPrzerobki);




                    System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                    rozgloszeniowy = Arrays.toString(split).split(", ");
                    rozgloszeniowy[2] = String.valueOf(Integer.parseInt(rozgloszeniowy[2]) - 1 );
                    rozgloszeniowy[3] = "255";
                    System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));
                    break;

                case 16384:

                    doPrzerobki = Integer.parseInt(dane);
                    doPrzerobki += 64;
                    split[2] = String.valueOf(doPrzerobki);




                    System.out.println("Kolejna podsieć " +interfejs1.getNazwa() + " to " + Arrays.toString(split) );

                    rozgloszeniowy = Arrays.toString(split).split(", ");
                    rozgloszeniowy[2] = String.valueOf(Integer.parseInt(rozgloszeniowy[2]) - 1 );
                    rozgloszeniowy[3] = "255";
                    System.out.println("Adres rozgłoszeniowy dla " + interfejs1.getNazwa()+ " to " +Arrays.toString(rozgloszeniowy));



                    break;
            }









        });




    }


}
