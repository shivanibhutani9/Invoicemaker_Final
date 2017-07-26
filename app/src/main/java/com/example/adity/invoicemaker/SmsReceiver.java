package com.example.adity.invoicemaker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import static android.R.attr.filter;

/**
 * Created by shivani on 25/7/17.
 */

public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;

    String filter;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            final Object[] pdusObj = (Object[]) bundle.get("pdus");
            for (int i = 0; i < pdusObj.length; i++) {
                SmsMessage currentMessage = getIncomingMessage(pdusObj[i], bundle);
                String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                if (phoneNumber.matches("TFCTOR")) {
                    Toast.makeText(context, "HIEEEEE", Toast.LENGTH_SHORT).show();
                    String message = currentMessage.getDisplayMessageBody();
                    //Pass on the text to our listener.
                    mListener.messageReceived(message);
                }

                }

        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
        private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
            SmsMessage currentSMS;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                currentSMS = SmsMessage.createFromPdu((byte[]) aObject, format);
            } else {
                currentSMS = SmsMessage.createFromPdu((byte[]) aObject);
            }
            return currentSMS;
        }
}