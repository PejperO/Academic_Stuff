public class Interfejs {
    private String nazwa;
    private int ilosc;
    private final int wymaganaPojemnosc;

    public Interfejs(String nazwa, int ilosc) {
        this.nazwa = nazwa;
        this.ilosc = ilosc;
        this.wymaganaPojemnosc = getIloscWymagana();
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getWymaganaPojemnosc() {
        return wymaganaPojemnosc;
    }

    public int getIlosc() {
        return ilosc;
    }
    public int getIloscWymagana() {
        this.ilosc +=2;
        int potega=1;

        do {
            potega *= 2;
        }while (potega < this.ilosc);

        return potega;
    }

    public int getIloscZer() {

        int count =0;

        int potega=1;


        while (potega <= ilosc){
            potega*=2;
            count++;

        }



        return count;


    }

    public String maska (int iloscZer){
        String maskaSiec = "";
        String maskaHost = "";
        for (int i = 0; i < 32-iloscZer; i++) {
            maskaSiec+="1";
        }
        for (int i = 0; i < iloscZer; i++) {
            maskaHost+="0";

        }
        String result= withDots(maskaSiec+maskaHost);
        result=binaryToText(result);



        return result;




    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
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
        String result="";

        int decimal=0;

        for (int i = 0; i < arr.length; i++) {
            decimal=Integer.parseInt(arr[i],2);
            result+=String.valueOf(decimal)+" ";

        }

        return  result;

    }

    @Override
    public String toString() {
        return "Nazwa=:" + nazwa +  "  |  " +
                "Wymagana ilość miejsc=:" + getWymaganaPojemnosc();
    }
}
