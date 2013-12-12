package csc7326.mylectures;

import java.util.ArrayList;

import csc7326.sessionview.SessionData;
import csc7326.subjectview.SubjectData;

/**
 * Created by luongnv89 on 06/12/13.
 */
public class SharedData {
    static SubjectData subjectData;

    public static SubjectData getSubjectData() {
        return subjectData;
    }

    public static void setSubjectData(SubjectData subjectData) {
        SharedData.subjectData = subjectData;
    }
}
