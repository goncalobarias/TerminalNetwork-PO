package prr.util;

import java.util.Comparator;
import java.util.Locale;
import java.text.Collator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.Serial;

/**
 * Comparator used between strings that provides a lexicographic order and
 * ignores case characters.
 */
public class NaturalTextComparator implements Serializable, Comparator<String> {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202210191154L;

    private transient Collator _collator;

    public NaturalTextComparator() {
        _collator = Collator.getInstance(Locale.getDefault());
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException,
      ClassNotFoundException {
        ois.defaultReadObject();
        _collator = Collator.getInstance(Locale.getDefault());
    }

    @Override
    public int compare(String s1, String s2) {
        return _collator.compare(s1, s2);
    }

}
