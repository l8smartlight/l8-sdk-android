L8 smartlight Android SDK
==============

    - Clon the projets 
    
    - Import in eclipse and check as a library
    
    - Create a new projet and select  l8-sdk-android as a referenced library. Maybe is also a good idea to get into the classpath
    
    - Insert in AndroidManifest.xml the activity 
 
    - Insert in strings.xml : string txt_select_device.
    
    
   
          <activity
            android:name="com.l8smartlight.sdk.android.bluetooth.DeviceListActivity"
            android:label="@string/txt_select_device"
            android:theme="@android:style/Theme.Dialog" 
            
            />
            
           <string name="txt_select_device">Select device</string>
