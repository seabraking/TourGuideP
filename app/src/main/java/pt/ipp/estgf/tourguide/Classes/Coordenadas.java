package pt.ipp.estgf.tourguide.Classes;

/**
 * Created by Vitor on 20/11/2015.
 */
public class Coordenadas {
    private String latitude;
    private String longitude;

    public Coordenadas(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
