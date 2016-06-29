package com.vibeosys.tradenow.custompageutils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.vibeosys.tradenow.custompageutils.CustomWidgets.WidgetHeadingView;
import com.vibeosys.tradenow.custompageutils.CustomWidgets.WidgetTextView;
import com.vibeosys.tradenow.custompageutils.CustomWidgets.WidgetVideoView;
import com.vibeosys.tradenow.custompageutils.CustomWidgets.LinkTextView;
import com.vibeosys.tradenow.custompageutils.CustomWidgets.WidgetImageView;
import com.vibeosys.tradenow.custompageutils.pagedata.PageWidgetDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akshay on 29-06-2016.
 */
public class WidgetTypes {

    private Map<String, Widget> mWidgetHashMap = new HashMap<>();

    public enum Widget {
        Link, Image, Video, Text, Heading, WebView, Rss
    }

    private Widget mWidget;
    private Context mContext;
    private PageWidgetDTO mPageWidgetDTO;

    public WidgetTypes(PageWidgetDTO widgetDTO, Context context) {
        initMap();
        this.mPageWidgetDTO = widgetDTO;
        this.mWidget = getWidget(this.mPageWidgetDTO.getWidgetTitle());
        this.mContext = context;
    }

    private Widget getWidget(String widgetTitle) {
        return mWidgetHashMap.get(widgetTitle);
    }

    private void initMap() {
        mWidgetHashMap.put("Image", Widget.Image);
        mWidgetHashMap.put("Link", Widget.Link);
        mWidgetHashMap.put("Video", Widget.Video);
        mWidgetHashMap.put("Text", Widget.Text);
        mWidgetHashMap.put("Heading", Widget.Heading);
        mWidgetHashMap.put("WebView", Widget.WebView);
        mWidgetHashMap.put("Rss", Widget.Rss);
    }

    public View getView() throws NullPointerException, IllegalArgumentException {
        switch (mWidget) {
            case Image:
                return new WidgetImageView(mContext, this.mPageWidgetDTO.getWidgetData());
            case Link:
                return new LinkTextView(mContext, this.mPageWidgetDTO.getWidgetData());
            case Video:
                return new WidgetVideoView(mContext, this.mPageWidgetDTO.getWidgetData());
            case Text:
                return new WidgetTextView(mContext, this.mPageWidgetDTO.getWidgetData());
            case Heading:
                return new WidgetHeadingView(mContext, this.mPageWidgetDTO.getWidgetData());
            case WebView:
                return new TextView(mContext);
            case Rss:
                return new TextView(mContext);
            default:
                return null;
        }
    }
}
