package ankit.com.videoeditor.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Ankit on 28-Jan-15.
 */
public class CommonUtil {

    public static void showToast(Context cont, String message)
    {
        Toast.makeText(cont, message, Toast.LENGTH_SHORT).show();
    }
}
