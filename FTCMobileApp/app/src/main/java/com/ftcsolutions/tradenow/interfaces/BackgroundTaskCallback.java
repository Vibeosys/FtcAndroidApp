package com.ftcsolutions.tradenow.interfaces;

/**
 * Callback for handling async task states
 */
public interface BackgroundTaskCallback {
    void onResultReceived(String downloadedJson);
}
