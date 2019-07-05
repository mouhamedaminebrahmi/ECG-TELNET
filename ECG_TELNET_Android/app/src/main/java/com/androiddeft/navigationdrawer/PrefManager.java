package com.androiddeft.navigationdrawer;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * créer un tuto pour l'application avec 3 vues avant le lancement de l'application
 */
public class PrefManager {
    //utiliser préférences partagé pour lancer cette interface une seul fois lors de lancement de la premier fois
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // préférences partagé mode
    int PRIVATE_MODE = 0;

    // Nom du fichier de préférences partagé
    private static final String PREF_NAME = "androidhive-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}