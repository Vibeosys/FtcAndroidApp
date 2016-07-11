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
        boolean isInserted = false, isUpdated = false;
        List<ResponsePageData> insertPageData = ResponsePageData.deserializePageData(jsonInsertList);
        List<ResponsePageData> updatePageData = ResponsePageData.deserializePageData(updateJsonList);

        if (!jsonInsertList.isEmpty() && !updateJsonList.isEmpty()) {
            isInserted = dbRepository.insertPages(insertPageData);
            isUpdated = dbRepository.updatePages(updatePageData);
            return isInserted & isUpdated;
        } else if (jsonInsertList.isEmpty() && !updateJsonList.isEmpty()) {
            dbRepository.deleteWidget(updatePageData);
            isUpdated = dbRepository.updatePages(updatePageData);
            return isUpdated;
        } else if (!jsonInsertList.isEmpty() && updateJsonList.isEmpty()) {
            isInserted = dbRepository.insertPages(insertPageData);
            return isInserted;
        } else {
            return false;
        }
    }

    public boolean addOrUpdateWidgets(ArrayList<String> jsonInsertList, ArrayList<String> updateJsonList) {
        boolean isInserted = false, isUpdated = false;
        List<ResponseWidgetData> insertWidgetData = ResponseWidgetData.deserializeWidgetData(jsonInsertList);
        List<ResponseWidgetData> updateWidgetData = ResponseWidgetData.deserializeWidgetData(updateJsonList);

        if (!jsonInsertList.isEmpty() && !updateJsonList.isEmpty()) {
            isInserted = dbRepository.insertWidgets(insertWidgetData);
            isUpdated = dbRepository.updateWidgets(updateWidgetData);
            return isInserted & isUpdated;
        } else if (jsonInsertList.isEmpty() && !updateJsonList.isEmpty()) {
            isUpdated = dbRepository.updateWidgets(updateWidgetData);
            return isUpdated;
        } else if (!jsonInsertList.isEmpty() && updateJsonList.isEmpty()) {
            isInserted = dbRepository.insertWidgets(insertWidgetData);
            return isInserted;
        } else {
            return false;
        }
    }
}
