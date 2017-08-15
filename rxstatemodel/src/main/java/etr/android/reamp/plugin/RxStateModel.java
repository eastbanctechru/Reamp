package etr.android.reamp.plugin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import etr.android.reamp.mvp.SerializableStateModel;
import rx.subjects.BehaviorSubject;

public class RxStateModel extends SerializableStateModel{

    private void writeObject(ObjectOutputStream out) throws IOException, IllegalAccessException {
        Field[] declaredFields = getClass().getDeclaredFields();
        List<SubjectMeta> fieldList = new ArrayList<>(declaredFields.length);
        for (Field field : declaredFields) {
            Class<?> type = field.getType();
            if (type.isAssignableFrom(BehaviorSubject.class)) {
                BehaviorSubject subject = (BehaviorSubject) field.get(this);
                fieldList.add(new SubjectMeta(field.getName(), subject.getValue()));
            }
        }
        out.defaultWriteObject();
        out.writeInt(fieldList.size());
        for (SubjectMeta subjectMeta : fieldList) {
            out.writeObject(subjectMeta);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException, IllegalAccessException {
        in.defaultReadObject();
        int subjectsCount = in.readInt();
        for (int i = 0; i < subjectsCount; i++) {
            SubjectMeta bpair = (SubjectMeta) in.readObject();
            for (Field field : getClass().getDeclaredFields()) {
                if (field.getName().equals(bpair.name)) {
                    field.set(this, BehaviorSubject.create(bpair.value));
                }
            }
        }
    }


    public static class SubjectMeta implements Serializable {
        public Object value;
        public String name;

        public SubjectMeta(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }
}
