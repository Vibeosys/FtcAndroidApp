package com.ftcsolutions.tradenow.newutils.parser;

import com.ftcsolutions.tradenow.newutils.data.XMLObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by akshay on 25-06-2016.
 */
public class XmlParser {

    public static XMLObject parseXml(InputStream is) throws Exception {
        if (is == null) throw new IOException("Inputstream is null from the server");
        String key = null;
        String value = null;

        Stack<String> mStack = null;

        HashMap<String, XMLObject> temp = new HashMap<String, XMLObject>();
        XMLObject mXmlObject = null;
        XmlPullParserFactory mXmlPullParserFactory = XmlPullParserFactory.newInstance();
        mXmlPullParserFactory.setNamespaceAware(true);
        XmlPullParser xpp = mXmlPullParserFactory.newPullParser();
        xpp.setInput(new InputStreamReader(is));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                mStack = new Stack<String>();
            } else if (eventType == XmlPullParser.START_TAG) {
                key = xpp.getName();
                mXmlObject = new XMLObject();
                mXmlObject.setName(key);
                mStack.push(key);
                int attrCount = xpp.getAttributeCount();
                if (attrCount > 0) {
                    HashMap<String, String> attribs = new HashMap<String, String>(attrCount);
                    for (int i = 0; i < attrCount; i++) {
                        attribs.put(xpp.getAttributeName(i), xpp.getAttributeValue(i));
                    }
                    mXmlObject.setParams(attribs);
                }

                temp.put(key, mXmlObject);
            } else if (eventType == XmlPullParser.END_TAG) {
                key = xpp.getName();

                String current = mStack.pop();

                if (mStack.size() == 0) {

                    XMLObject mXmlObject2 = temp.get(key);
                    mXmlObject2.setValue(value);
                    temp.put(current, mXmlObject2);
                    mXmlObject = mXmlObject2;
                } else {
                    String parent = mStack.get(mStack.size() - 1);

                    if (temp.containsKey(key)) {
                        XMLObject mXmlObject2 = temp.get(key);
                        temp.remove(mXmlObject2);
                        mXmlObject2.setValue(value);
                        XMLObject parentObj = temp.get(parent);
                        List<XMLObject> list = parentObj.getChilds();
                        if (list == null) list = new ArrayList<XMLObject>();
                        list.add(mXmlObject2);
                        parentObj.setChilds(list);
                        temp.put(parent, parentObj);
                    }
                }
                value = null;
            } else if (eventType == XmlPullParser.TEXT) {
                value = xpp.getText();
            }
            eventType = xpp.next();
        }
        return mXmlObject;
    }
}