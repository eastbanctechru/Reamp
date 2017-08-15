package example.reamp.subject;

import etr.android.reamp.plugin.RxStateModel;
import rx.subjects.BehaviorSubject;

public class SubjectStateModel extends RxStateModel {
    public transient BehaviorSubject<String> nameSubject = BehaviorSubject.create((String) null);
    public transient BehaviorSubject<String> passSubject = BehaviorSubject.create((String) null);
}
