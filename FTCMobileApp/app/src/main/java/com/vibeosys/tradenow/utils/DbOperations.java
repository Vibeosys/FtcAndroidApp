package com.vibeosys.tradenow.utils;

import com.vibeosys.tradenow.data.responsedata.ResponsePageData;
import com.vibeosys.tradenow.data.responsedata.ResponseWidgetData;
import com.vibeosys.tradenow.database.DbRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 30-06-2016.
 */
public class DbOperations {

    private DbRepository dbRepository;

    public DbOperations(DbRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    public boolean addOrUpdatePage(ArrayList<String> jsonInsertList, ArrayList<String> updateJsonList) {
        List<ResponsePageData> insertPageData = ResponsePageData.deserializePageData(jsonInsertList);
        List<ResponsePageData> updatePageData = ResponsePageData.deserializePageData(updateJsonList);

        boolean isInserted = dbRepository.insertPages(insertPageData);
        boolean isUpdated = dbRepository.updatePages(updatePageData);
        return isInserted & isUpdated;
    }

    public boolean addOrUpdateWidgets(ArrayList<String> jsonInsertList, ArrayList<String> updateJsonList) {
        List<ResponseWidgetData> insertWidgetData = ResponseWidgetData.deserializeWidgetData(jsonInsertList);
        List<ResponseWidgetData> updateWidgetData = ResponseWidgetData.deserializeWidgetData(updateJsonList);

        boolean isInserted = dbRepository.insertWidgets(insertWidgetData);
        boolean isUpdated = dbRepository.updateWidgets(updateWidgetData);
        return isInserted & isUpdated;
    }
}
