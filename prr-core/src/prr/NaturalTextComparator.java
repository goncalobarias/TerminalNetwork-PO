package prr;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class NaturalTextComparator implements Comparator<String>, Serializable {

    /** Class serial number. */
    private static final long serialVersionUID = 202210191154L;

    private transient Collator _collator;

    public NaturalTextComparator() {
        _collator = Collator.getInstance(Locale.getDefault());
        // setting the strength to PRIMARY let's us ignore case and accentuated
        // characters
        _collator.setStrength(Collator.PRIMARY);
    }

    private void readObject(ObjectInputStream ois) throws IOException,
      ClassNotFoundException {
        ois.defaultReadObject();
        _collator = Collator.getInstance(Locale.getDefault());
        _collator.setStrength(Collator.PRIMARY);
    }

    @Override
    public int compare(String s1, String s2) {
        return _collator.compare(s1, s2);
    }

}
