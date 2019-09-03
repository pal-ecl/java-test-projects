package com.lescure.testAndroid;

import android.os.SystemClock;

public class WSUtils {

    public static EleveBean loadEleveFromWeb() throws Exception {
        SystemClock.sleep(5000);

        return new EleveBean("Nom", "Prénom");
    }
}
