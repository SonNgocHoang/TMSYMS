package tms.com.libre.tms.common;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by quangnv on 4/12/17.
 */

public class IntentManager {
    /**
     * Go to the destination activity
     *
     * @param context The current activity
     * @param cls The class of the destination activity
     * @param bundle Hold some extra data
     * @param flags The adding flag of intent
     */
    public static void startActivity(Context context, Class<?> cls, Bundle bundle, Integer... flags)
    {
        if (context == null || cls == null)
        {
            return;
        }

        try
        {
            Intent it = new Intent(context, cls);
            if (bundle != null)
            {
                it.putExtras(bundle);
            }
            if (flags != null && flags.length > 0)
            {
                for (int flag : flags)
                {
                    it.addFlags(flag);
                }
            }
            context.startActivity(it);
        }
        catch (ActivityNotFoundException e)
        {
        }
    }

    /**
     * Go to the destination activity and receive data then this one finishes
     *
     * @param currentActivity The current activity
     * @param cls The class of the destination activity
     * @param bundle Hold some extra data
     * @param requestCode The request code of function
     * @param flags The adding flag of intent
     */
    public static void startActivityForResult(Activity currentActivity, Class<?> cls, Bundle bundle, int requestCode,
                                              Integer... flags)
    {
        if (currentActivity == null || cls == null)
        {
            return;
        }

        try
        {
            Intent it = new Intent(currentActivity, cls);
            if (bundle != null)
            {
                it.putExtras(bundle);
            }
            if (flags != null && flags.length > 0)
            {
                for (int flag : flags)
                {
                    it.addFlags(flag);
                }
            }
            currentActivity.startActivityForResult(it, requestCode);
        }
        catch (ActivityNotFoundException e)
        {
        }
    }
}
