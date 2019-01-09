package mobile.una.com.flowledge.model;

/**
 * Created by abdalla on 1/12/18.
 */

public class AreaData {

    private String AreaName;
    private int AreaImage;

    public AreaData(String areaName, int areaImage) {
        AreaName = areaName;
        AreaImage = areaImage;
    }

    public AreaData() {
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public int getAreaImage() {
        return AreaImage;
    }

    public void setAreaImage(int areaImage) {
        AreaImage = areaImage;
    }


}
