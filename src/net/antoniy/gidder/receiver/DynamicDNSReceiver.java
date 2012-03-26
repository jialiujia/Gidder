package net.antoniy.gidder.receiver;

import net.antoniy.gidder.app.GidderApplication;
import net.antoniy.gidder.dns.DynamicDNSManager;
import net.antoniy.gidder.ui.util.GidderCommons;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DynamicDNSReceiver extends BroadcastReceiver {

	private final static String TAG = DynamicDNSReceiver.class.getSimpleName();
	
	public final static int STANDART_REQUEST = 0;
	public final static int SCHEDULED_REQUEST = 1;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		GidderApplication application = (GidderApplication)context.getApplicationContext();
		
    	long lastDynDnsUpdateTime = application.getUpdateDynDnsTime();
    	boolean scheduled = intent.getBooleanExtra("scheduled", false);
		if(scheduled || ((System.currentTimeMillis() - lastDynDnsUpdateTime > GidderApplication.UPDATE_DYNDNS_INTERVAL) 
    			&& GidderCommons.isWifiConnected(context))) {
			
			Log.i(TAG, "DynamicDNSReceiver ready to update!");
			
			DynamicDNSManager dynDnsManager = new DynamicDNSManager(context);
			dynDnsManager.update();
			application.setUpdateDynDnsTime(System.currentTimeMillis());
		}
		
	}

}
