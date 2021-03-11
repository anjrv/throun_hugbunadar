package hotels;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class Logic {
    public ArrayList<Hotel> getHotels(String[] params)  {
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        String sql = "SELECT name, region, address, image, accessibility, gym, spa FROM hotels";
        
        // for (int i = 0; i < params.length; i++) {
        //     sql += "WHERE " + params[i];
        // }
        // sql += "FROM hotels";

        try {
            CachedRowSet crs = QueryEngine.queryHotels(sql);
            while (crs.next()) {
                hotels.add(new Hotel(crs.getString("name")
                                    ,crs.getString("address")
                                    ,crs.getString("image")
                                    ,crs.getInt("region")
                                    ,crs.getBoolean("accessibility")
                                    ,crs.getBoolean("gym")
                                    ,crs.getBoolean("spa")
                                    ,new ArrayList<Room>()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return hotels;
    }

    /*
     * public static ArrayList<Room> getRooms(String[] params) {  }
     * 
     * public static ArrayList<Reservation> getReservations(String[] params) {  }
     * 
     * public static Review[] getReviews(String[] params) {  }
     * 
     * public static void setReservation(Reservation r) {  }
     * 
     * public static void setReview(Review r) {  }
     */
}
