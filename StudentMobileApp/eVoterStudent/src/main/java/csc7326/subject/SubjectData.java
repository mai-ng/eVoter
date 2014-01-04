package csc7326.subject;

import java.util.ArrayList;

import csc7326.session.SessionData;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SubjectData {
    String id;
    String name;
    String date;
    int nbSessions;

    public SubjectData(String id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public SubjectData(SubjectData copy) {
        this.id = copy.getId();
        this.name = copy.getName();
        this.date = copy.getDate();
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

    public int getNbSessions() {
        return nbSessions;
    }

    public void setNbSessions(int nbSessions) {
        this.nbSessions = nbSessions;
    }
}
