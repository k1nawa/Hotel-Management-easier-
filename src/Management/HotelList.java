package Management;

import Information.Hotel;
import utilities.CheckUpdateMethod;
import FileManagement.FileImportExport;
import utilities.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HotelList{
        private ArrayList <Hotel> arrHotel;
        private FileImportExport fm;
        private CheckUpdateMethod cm;
        private static boolean dataChange;
        
    //constructors
    public HotelList() {
        arrHotel = new ArrayList<>();
        cm = new CheckUpdateMethod();
        fm = new FileImportExport();
        if(arrHotel.size()<4){
            System.err.println("load Data to list");
                    
        }
    }
    
    //Menu 1
    public void loadData() {
        try {
            fm.loadDataFromFile(arrHotel, "Hotel.dat");
            while(arrHotel.size()<4){
                System.err.println("Please add hotel until there are 4 hotels at least");
                this.addHotel();
            }
            System.out.println("Load successful!");
                    
        } catch (Exception e) {
            System.out.println("Empty List");
        }
    }
    
    //Menu 2
    public void addHotel() {
        String hotelId;
        String hotelName;
        int hotelRoomAvailable;
        String hotelAddress;
        String hotelPhone;
        int hotelRating;
        int choice = 1;
        while (choice==1) {
            
            hotelId = Utils.getStringreg("Enter hotel's ID (Hxx): ","H\\d{2}","DO NOT LET THIS BOX EMPTY","INPUT MUST BE THE SAME AS PATTERN");
            for (Hotel hotel : arrHotel) {
                if(hotel.getHotelId().equals(hotelId)){
                    System.out.println("ID hotel exists");
                    return;
                }
            }
            hotelName = Utils.getStringreg("Enter hotel's name: ", "^[a-zA-Z\\s]+","DO NOT LET THIS BOX EMPTY","INPUT MUST BE THE SAME AS PATTERN");
            hotelRoomAvailable = Utils.getInt("Enter the number of available rooms: ", 0, 10000);
            hotelAddress = Utils.getStringreg("Enter hotel's address: ","^[a-zA-Z0-9/,\\s]+","DO NOT LET THIS BOX EMPTY","INPUT MUST BE THE SAME AS PATTERN");
            hotelPhone = Utils.getStringreg("Enter hotel's phone number(0xxxxxxxx): ", "0\\d{9}","DO NOT LET THIS BOX EMPTY","INPUT MUST BE THE SAME AS PATTERN");
            hotelRating = Utils.getInt("Enter hotel's rating(0-6): ", 0, 6);
            Hotel hotel = new Hotel(hotelId, hotelName, hotelRoomAvailable, hotelAddress, hotelPhone, hotelRating);
            arrHotel.add(hotel);
            dataChange=true;
            System.out.println("Successful");
            choice = Utils.inputYN("Do you want to continue add Hotel(Y/N): ");
        }
    }
    
    //Menu 3
     public void checkExistsHotel() {
         
        int choice=1;
        while(choice==1){
         Hotel hotelExists= null;
        String id = Utils.getStringreg("Enter id of hotel you want to check (Hxx): ","H\\d{2}","DO NOT LET THIS BOX EMPTY","INPUT MUST BE THE SAME AS PATTERN");
        for (Hotel hotel : arrHotel) {
            if(id.trim().equals(hotel.getHotelId())){
                hotelExists = hotel;
                break;
            }
        }
        if (hotelExists != null) {
            System.out.println("Exist hotel");
            System.out.println(hotelExists.toString());
        }else{
             System.err.println("Hotel does not exist");
            return;
        }
        choice = Utils.inputYN("Do you want to continue check exists Hotel (Y/N): ");
        }
    }
     
     //Menu 4
     public void updateHotelInformation() {
         Hotel hotelUpdate = null;
        String id = Utils.getStringreg("Enter hotel's ID to update information (Hxx): ","H\\d{2}","DO NOT LET THIS BOX EMPTY","INPUT MUST BE THE SAME AS PATTERN");
        for (Hotel hotel : arrHotel) {
            if(id.trim().equals(hotel.getHotelId())){
                hotelUpdate = hotel;
                break;
            }
        }
        if (hotelUpdate!= null) {
            System.out.println("Exist hotel. Here is the hotel you search for: ");
            System.out.println(hotelUpdate.toString());
            cm.updateInforHotel(hotelUpdate);
            System.out.println("Here is hotel's information after update: ");
            System.out.println(hotelUpdate.toString());
            dataChange=true;
        } else {
            System.err.println("Hotel does not exist");
            return;
        }
    }
     
      //Menu 5
     public void deleteHotel() {
         Hotel hotelDelete = null;
        String id = Utils.getStringreg("Enter hotel's ID you want to delete (Hxx): ","H\\d{2}","DO NOT LET THIS BOX EMPTY","INPUT MUST BE THE SAME AS PATTERN");
        for (Hotel hotel : arrHotel) {
            if(id.trim().equals(hotel.getHotelId())){
                hotelDelete = hotel;
                break;
            }
        }
        int choice;
        if (hotelDelete != null) {
            System.out.println("Exist Hotel. Here is the hotel you search for: ");
            System.out.println(hotelDelete.toString());
            choice = Utils.inputYN("Do you ready want to delete this hotel(Y/N): ");
            if (choice==1) {
                arrHotel.remove(hotelDelete);
                System.out.println("The hotel has been deleted from the list successfully!");
                dataChange=true;
            }
        }else{
             System.err.println("Hotel does not exist");
            return;
        } 
    }
    
    //Menu 6
    public void searchHotel() {
        int choice;
        ArrayList<Hotel> arrResult1 = new ArrayList<>();
        ArrayList<Hotel> arrResult2 = new ArrayList<>();
        
        do {
            boolean check = false;
        System.out.println("1. Search by Hotel_id");
        System.out.println("2. Search by Hotel_address");
        System.out.println("3. Exit");
        choice = Utils.getInt("Enter a number from 1 to 3: ", 1, 3);
            switch (choice) {
                case 1:
                    String id = Utils.getStringreg("Enter id of hotel you want to check (Hxx): ","H\\d{2}","DO NOT LET THIS BOX EMPTY","INPUT MUST BE THE SAME AS PATTERN");
                    for (Hotel hotel : arrHotel) {
                        if (hotel.getHotelId().contains(id)) {
                            arrResult1.add(hotel);
                            check = true;
                        }
                    }
                    if (check==false) {
                        System.err.println("Can't found id: " + id);
                    }else{
                        for(Hotel hotel: arrResult1){
                          System.out.println(hotel.toString());  
                        }
                        arrResult1.clear();
                        
                    }
                    break;
                case 2:
                    String address = Utils.getStringreg("Enter hotel's address: ","^[a-zA-Z0-9/,\\s]+","DO NOT LET THIS BOX EMPTY","INPUT MUST BE THE SAME AS PATTERN");

                    for (Hotel hotel : arrHotel) {
                        if (hotel.getHotelAddress().toLowerCase().contains(address.toLowerCase())) {
                            arrResult2.add(hotel);
                            check = true;
                        }
                    }
                    if (check==false) {
                        System.err.println("Can't found address: " + address);
                    }else{
                        Collections.sort(arrResult2,new Comparator<Hotel>(){
                            @Override
                            public int compare(Hotel o1, Hotel o2) {
                                if (o1.getHotelRoomAvailable()-o2.getHotelRoomAvailable() > 0){
                                    return -1;
                                    } else if (o1.getHotelRoomAvailable()-o2.getHotelRoomAvailable() < 0) {
                                             return 1;
                                    } else {
                                        return 0;
                                            }
                            } 
                        });
                        for(Hotel hotel: arrResult2){
                          System.out.println(hotel.toString());  
                        }
                        arrResult2.clear();
                    }
                    break;

                default:
                    System.out.println("Out to main menu");
            }
        } while (!(choice < 1 || choice > 2));
    }
    
    //Menu 7
    public void displayHotel() {
        if (arrHotel.isEmpty()) {
            System.err.println("Empty list");
        }else{
        System.out.printf("|%-9s|%-17s|%-25s|%-66s|%-11s|%-13s|\n", "Hotel_id", "Hotel_Name", "Hotel_Room_Available", "Hotel_Address", "Hotel_Phone", "Hotel_Rating");
        Collections.sort(arrHotel);
        for (Hotel hotel : arrHotel) {
            System.out.println("___________________________________________________________________________________________________________________________________________________");
            System.out.println(hotel.toString());
          
        }
        }
    }

    //Menu 8
    public void saveDataFile(){
        fm.saveDataToFile(arrHotel, "Hotel.dat","File Save successfully!","Fail");
        dataChange=false;
    }
    
    //Menu 9
    public boolean checkOut(){
        if(dataChange==true){
            int choice1;
            choice1=Utils.inputYN("Do you want to save data to file(Y/N): ");
            if(choice1==1){
                fm.saveDataToFile(arrHotel, "Hotel.dat","File Save successfully!","Fail");
                return true;
            }     
        }
        return true;
    }

    
}
