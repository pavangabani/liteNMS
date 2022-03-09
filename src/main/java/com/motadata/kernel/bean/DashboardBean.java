package com.motadata.kernel.bean;

import java.util.ArrayList;
import java.util.List;

public class DashboardBean {

    List<Integer> availability=new ArrayList<>(4);

    public List<Integer> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Integer> availability) {
        this.availability = availability;
    }

}
