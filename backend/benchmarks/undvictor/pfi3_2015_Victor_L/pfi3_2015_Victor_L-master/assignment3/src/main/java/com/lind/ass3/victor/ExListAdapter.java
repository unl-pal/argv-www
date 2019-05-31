package com.lind.ass3.victor;

import java.util.Random;
import java.util.ArrayList;


public class ExListAdapter {

    public int getGroupCount(){
        Random rand = new Random();
		return rand.nextInt();
    }

    public int getChildrenCount(int groupPosition){
        return 1;
    }

    public Object getGroup(int groupPosition) {
        return null;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    public long getGroupId(int groupPosition) {
        return 0;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}