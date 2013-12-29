package csc7326.subjectview;

import java.util.ArrayList;

import csc7326.sessionview.SessionData;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SubjectData {
    String id;
    String name;
    String date;
    ArrayList<SessionData> listSession = new ArrayList<SessionData>();

    public SubjectData(String id, String name, String date, ArrayList<SessionData> listSession) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.listSession = listSession;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<SessionData> getListSession() {
        return listSession;
    }

    public void setListSession(ArrayList<SessionData> listSession) {
        this.listSession = listSession;
    }
}
