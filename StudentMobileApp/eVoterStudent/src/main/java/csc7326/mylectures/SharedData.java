package csc7326.mylectures;

import csc7326.subject.SubjectData;

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
