![L8smartlight](http://corcheaymedia.com/l8/wp-content/plugins/wp-l8-styles/images/logo.png)
L8 smartlight Android SDK V1.0
=========================

**Note:** 
We are working on a new SDK version 2.0 and so several things of the existing version 1.0 will be changed in few weeks. If you use V1.0, your code will not be compatible with the next version, of course you are free to use it. Apologies for this inconvenience but our objective is to create much better software that is not possible with the existing design. We are in continuous evolution thanks to all!.

## 1. Installation:

1.Clone the projects. 
    
2.Import in eclipse and check as a library.
    
3.Create a new projet and select l8-sdk-android as a referenced library. Maybe is also a good idea to get into the classpath.
    
    - Insert in AndroidManifest.xml the activity.

        <activity
            android:name="com.l8smartlight.sdk.android.bluetooth.DeviceListActivity"
            android:label="@string/txt_select_device"
            android:theme="@android:style/Theme.Dialog" 
        />
 
4.Insert in strings.xml: string txt_select_device.

       <string name="txt_select_device">Select device</string>

	   

## 2. Quick start

You have to instantiate AndroidL8Manager and implement OnL8ManagerReadyListener

**Example of use:**

```java
public class MainActivity extends Activity implements AndroidL8Manager.OnL8ManagerReadyListener{

	private L8 l8;
	private AndroidL8Manager l8Manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//AndroidL8Manager initialization
		l8Manager = new AndroidL8Manager(this, this);
		l8Manager.setOnL8ManagerReadyListener(this);
		l8Manager.init();
	}

	//AndroidL8Manager notifies if it is ready.
	@Override
	public void onL8ManagerReady(boolean success) {
		discoverL8s();
	}

	//You have to destroy l8Manager, it releases the exiting Bluetooth connection
	@Override
	protected void onDestroy() {
		l8Manager.onDestroy();
	}


	public void pressButton(View view){
		try {
			//L8 class has functions to control a L8 device 
			//set x y Led
			l8.setLED(0, 0, Color.BLUE);
			//set Super led
			l8.setSuperLED(Color.RED);
		} catch (L8Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void discoverL8s() {
		final Activity activity = this;
		new AsyncTask<Void, Void, List<L8>>() {

			protected List<L8> doInBackground(Void... params) {
				//L8s list
				List<L8> l8s = null;
				try {
					//Search l8s, it is a blocking task. 
					l8s = l8Manager.discoverL8sAndLoadEmulator();
				} catch (L8Exception e) {
					activity.runOnUiThread(new Runnable() {
						public void run() {
							System.out.println("Some error happened while discovering L8s! You will not be able to set any light.");
							//Do something here
								
							//..
						}
					});
				}
				return l8s;
			};

			protected void onPostExecute(List<L8> l8s) {
					try {
						if (l8s != null) {
							if (l8s.size() > 0) {
								//First l8 discovered, V1.0 does not support multicontrol.
								l8 = l8s.get(0);
								if (l8s.get(0).getConnectionType() == ConnectionType.RESTful) {
									System.out.println("No L8s could be found! A simul8tor was created for you. You can find it at"+  l8.getConnectionURL());    
									//Do something here
									
									//..
								} else {
									System.out.println("Connected to L8");
									//Do something here
									
									//..
								   }
								}
							}
					} catch (L8Exception e) {
						System.out.println("No L8s could be found! We coldn't create a simul8tor neither... You will not be able to set any light.");
						//Do something here
						
						//..
					}
			};
		}.execute();
	}
	
	
}


```