package csc7326.main;

import evoter.server.model.Subject;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SharedData {
    static Subject subjectData;

    public static Subject getSubjectData() {
        return subjectData;
    }

    public static void setSubjectData(Subject subjectData) {
        SharedData.subjectData = subjectData;
    }
}
