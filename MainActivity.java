package com.example.ips.khalsaguide_indoornavigationsystemforblinds;



import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * The MainActivity class of the application.
 * contains a sub-class Beacon.
 * @author Tarun Thakur
 * @author Dr. Inderpreet Singh
 * @author Dr. Navjot Kaur
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The MainActivity class of the application.
     * contains a sub-class Beacon.
     * @author xyz
     * @author abc
     * @author pqr
     */

    /**
     * A simp[le class which contains the information
     * about the bluetooth beacons.
     *
     */

    public class Beacon {
        /**
         * @var string add stores the address of the tag
         * @var string key stores the name of the locaation
         * where this tag is located.
         */
        public String add;
        public String key;

        /**
         * @var int f,b,r,l are flags representing
         * whether forward, backward, right and left
         * movement is aloowed or not.
         * Initiaaly all the flags are true.
         */
        int f, b, r, l;

        /**
         * @param k
         * @param a sets the "key" to the value of k
         *          sets "add" to the value of a
         */
        Beacon(String k, String a) {
            key = k;
            add = a;
            f = 1;// directional flags
            b = 1;
            r = 1;
            l = 1;
        }

        public String getAdd() {
            return add;
        }

        public void setAdd(String s) {
            this.add = s;
        }

        public String getKey() {
            return this.key;
        }
    }

    // assigning the default bluetooth adapter to BluetoothAdapter object "bt"
    BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();

    // a 2-D matrix representing the relative position of the tags
    String[][] keys = {{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "Science Block", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "c5", "c5", "c4", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "Admin Block", "c2", "0", "0", "0", "0", "Library", "0", "0", "0"},
            {"0", "0", "0", "29", "0", "0", "c1", "0", "0", "Principal Office", "c3", "c3", "0", "10+11+12", "0"},
            {"0", "Computer Lab 1", "0", "27+28", "0", "0", "c1", "0", "0", "Main Hall", "0", "0", "0", "8+9", "0"},
            {"0", "Computer Lab 2", "c6", "c6", "c6", "c6", "23+st+wr2", "17 20 elecLab", "edp+st+wr1", "Main Hall", "st+wr3", "st+wr3", "st+wr3", "gcr+cl4+st4", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "Main Gate", "0", "0", "0", "1+2+3+4", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"}};

    // stores the address of the tags at their corresponding location in the "keys" matrix
    String[][] uuid = {{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "11:00:00:02:04:11", "0", "0", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "1D:00:00:01:88:42", "1D:00:00:01:88:42", "17:00:00:01:A5:59", "0", "0", "0"},
            {"0", "0", "0", "0", "0", "35:00:00:02:4B:6E", "33:00:00:02:0D:14", "0", "0", "0", "0", "34:00:00:01:EB:E7", "0", "0", "0"},
            {"0", "0", "0", "1A:00:00:02:2A:B1", "0", "0", "27:00:00:01:5B:03", "0", "0", "13:00:00:02:26:95", "36:00:00:02:3F:86", "36:00:00:02:3F:86", "0", "13:00:00:02:26:95", "0"},
            {"0", "11:00:00:02:04:11", "0", "2C:00:00:01:AC:83", "0", "0", "27:00:00:01:5B:03", "0", "0", "2C:00:00:01:AA:BE", "0", "0", "0", "2A:00:00:02:54:8D", "0"},
            {"0", "17:00:00:01:A7:14", "24:00:00:02:81:23", "24:00:00:02:81:23", "24:00:00:02:81:23", "24:00:00:02:81:23", "35:00:00:02:54:F7", "2C:00:00:01:AA:E4", "26:00:00:02:7F:2F", "2C:00:00:01:AA:BE", "37:00:00:02:17:9C", "37:00:00:02:17:9C", "37:00:00:02:17:9C", "2C:00:00:01:A8:51", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "36:00:00:02:40:EC", "0", "0", "0", "24:00:00:02:7B:E0", "0"},
            {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"}};

    /**
     * @var string source stores the name of the initial
     * position of the user
     * @var string destination stores the name of the destination
     * chosen by the user
     */
    public String source, destination;
    /**
     * @var boolean foundSource becomes true when the source is found
     * @var boolean asn becomes true when function "assignAdd" is executed
     * @var boolean sch becomes true when function "search" is executed
     * These booleans are defined to stop the execution of the next function
     * until the previious function was executed.
     *
     *
     */
    public boolean foundSource = false, asn = false, sch = false, broad = false;
    /**
     * @var string path - array to store the calculated path
     * @var string dir - array to store the directions
     * @var string bt_add - array to store the address of the
     * BT devices
     */
    public String[] path = new String[30];
    public String[] dir = new String[30];
    public String[] bt_add = new String[30];

    /**
     * @var int srow stores the value of the row corresponding to the
     * source.
     * @var int scol stores the value of the column corresponding to the
     * destination
     */
    public int srow = 0, scol = 0, row, col, k = 0, i = 0, j = 0, r = 0;

    public int[] arow = new int[30];
    public int[] acol = new int[30];

    public Beacon[][] a = new Beacon[9][15];
    private Boolean exit = false;



    /**
     * A function showToast
     * @param s String
     * shows the string s as toast
     */

    public void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }


    /**
     * executes when the submit button is clicked by the user
     *
     * @param view
     * @var destination stores the value of the element of the list
     * selected by the user
     * calls function "startBluetooth"
     */
    public void getDest(View view) // Button function
    {
        final int CODE = 5; // app defined constant used for onRequestPermissionsResult

        String[] permissionsToRequest =
                {
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WAKE_LOCK
                };

        boolean allPermissionsGranted = true;

        for (String permission : permissionsToRequest) {
            allPermissionsGranted = allPermissionsGranted && (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED);
        }

        if (!allPermissionsGranted) {
            ActivityCompat.requestPermissions(this, permissionsToRequest, CODE);
        }


        srow = 0;
        scol = 0;
        k = 0;
        i = 0;
        j = 0;
        r = 0;

        a = new Beacon[9][15];
        /**
         * flushes the previous values stored
         * in the
         */
        for (int g = 0; g < 30; g++) {
            path[g] = null;
            dir[g] = null;
            bt_add[g] = null;
            arow[g] = 0;
            acol[g] = 0;


        }
        // path = new String[30];
        //dir = new String[30];
        //bt_add = new String[20];
        foundSource = false;
        asn = false;
        sch = false;
        broad = false;

        Spinner place = (Spinner) findViewById(R.id.spinner);
        destination = String.valueOf(place.getSelectedItem());
        if (destination.equals("Select your destination")) {
            showToast("Please Select A Valid Path");
        } else {
            showToast("your destination is " + destination);
            if (destination.equals("Room 1") || destination.equals("Room 2") || destination.equals("Room 3") || destination.equals("Room 4")) {
                destination = "1+2+3+4";
            } else if (destination.equals("Room 10") || destination.equals("Room 11") || destination.equals("Room 12")) {
                destination = "10+11+12";
            } else if (destination.equals("Room 8") || destination.equals("Room 9")) {
                destination = "8+9";
            } else if (destination.equals("Computer Lab 4") || destination.equals("Girls Common Room")) {
                destination = "gcr+cl4+st4";
            } else if (destination.equals("EDP Room")) {
                destination = "edp+st+wr1";

            } else if (destination.equals("Room 17") || destination.equals("Room 20") || destination.equals("Electronics Lab")) {
                destination = "17 20 elecLab";
            } else if (destination.equals("Room 28") || destination.equals("Room 27")) {
                destination = "27+28";
            } else if (destination.equals("Room 29")) {
                destination = "29";
            }else if (destination.equals("Room 23")){
                destination = "23+st+wr2";
            }
            startBluetooth();
        }
    }
    /**
     * a function to turn on the bluetooth
     * checks if the bluetooth is already on or not
     * if the bluetooth is not on then, turn it on
     * when bluetooth is on, start searching for new devices
     * by sending intents to the broadcastReciever
     */
    public void startBluetooth() {
        if (bt != null) {
            if (bt.isEnabled()) {
                bt.startDiscovery();
                showToast("Scanning initiated");
            } else {
                bt.enable();
                bt.startDiscovery();
                showToast("Scanning initiated");
            }
            /**
             * sends intent "ACTION_FOUND" to the broadcastReciever
             * to perform a specific task as soon as a new device is found.
             */
            IntentFilter f1 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(myReciever, f1);
            /**
             * sends intent "ACTION_DISCOVERY_FINISHED" to the broadcastReciever
             * to preform a specific task as soon as discovery is finished
             */
            IntentFilter f2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(myReciever, f2);
        } else {
            showToast("your device doesn\'t support bluetooth");
        }
    }


    /**
     *
     */
    public void assignAdd() {
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 14; j++) {
                a[i][j] = new Beacon(keys[i][j], uuid[i][j]);
            }
        }
        asn = true;
    }



    public void search(String src, String dest) {
        int p, q, m, n;
        p =i;
        q = j;
        findadd(dest); //finding address of destination using function
        m = srow;
        n = scol;
        arow[k] = p; // saving the values of row and col in a array and path
        acol[k] = q;
        path[k] = a[p][q].key;
        bt_add[k] = a[p][q].add;

        while (true) {
            if ((m == p) && (n == q))   //comparing if destination and source are same.
            {   dir[k]="Reached";
                fixDir();

                for(int g=0;g<=k;g++) {
                    if (dir[g].equals("F")) {
                        dir[g] = "Forward";
                    } else if (dir[g].equals("L")) {
                        dir[g] = "Left";
                    } else if (dir[g].equals("R")) {
                        dir[g] = "Right";
                    }

                    if (path[g].equals("1+2+3+4")) {
                        path[g] = "Room 1,2,3,4";
                    } else if (path[g].equals("10+11+12") ) {
                        path[g] = "Room 10,11,12";
                    } else if (path[g].equals("8+9") ) {
                        path[g] = "Room 8,9";
                    } else if (path[g].equals("gcr+cl4+st4") ) {
                        path[g] = "Girls Common Room";
                    } else if (path[g].equals("edp+st+wr1")) {
                        path[g] = "EDP Room";
                    } else if (path[g].equals("17 20 elecLab") ) {
                        path[g] = "Electronics Lab";
                    } else if (path[g].equals("27+28") ) {
                        path[g] = "Room 27,28";
                    } else if (path[g].equals("29")) {
                        path[g] = "Room 29";
                    }
                    else if(path[g].equals("23+st+wr2")){
                        path[g]="Room 23";
                    }else if(path[g].equals("c1") || path[g].equals("c3") || path[g].equals("c4") || path[g].equals("c5") || path[g].equals("c6")){
                        path[g]="Corridor";
                    }else if(path[g].equals("c2")){
                        path[g]="Jannat";
                    }
                }
                break;
            } else {
                row = p;
                col = q;
                if ((a[row - 1][col].key != "0") && (a[row][col].f != 0)) //checking forward
                {
                    k++;
                    path[k] = a[row - 1][col].key;
                    bt_add[k] = a[row - 1][col].add;
                    arow[k] = row - 1;
                    acol[k] = col;
                    a[row - 1][col].b = 0;
                    a[row][col].f = 0;
                    dir[k-1] = "F";
                    p--;
                } else if ((a[row][col - 1].key != "0") && (a[row][col].l != 0)) //checking left
                {
                    k++;
                    path[k] = a[row][col - 1].key;
                    bt_add[k] = a[row][col - 1].add;
                    arow[k] = row;
                    acol[k] = col - 1;
                    a[row][col - 1].r = 0;
                    a[row][col].f = 0;
                    a[row][col].l = 0;
                    dir[k-1] = "L";
                    q--;
                } else if ((a[row + 1][col].key != "0") && (a[row][col].b != 0)) //checking backward
                {
                    k++;
                    path[k] = a[row + 1][col].key;
                    bt_add[k] = a[row + 1][col].add;
                    arow[k] = row + 1;
                    acol[k] = col;
                    a[row + 1][col].f = 0;
                    a[row][col].f = 0;
                    a[row][col].l = 0;
                    a[row][col].b = 0;
                    dir[k-1] = "B";
                    p++;
                } else if ((a[row][col + 1].key != "0") && (a[row][col].r != 0))  //checking right
                {
                    k++;
                    path[k] = a[row][col + 1].key;
                    bt_add[k] = a[row][col + 1].add;
                    a[row][col + 1].l = 0;
                    arow[k] = row;
                    acol[k] = col + 1;
                    a[row][col].f = 0;
                    a[row][col].l = 0;
                    a[row][col].b = 0;
                    a[row][col].r = 0;
                    dir[k-1] = "R";
                    q++;
                } else   //if all directions not allowed
                {
                    k--;
                    a[row][col].f = 0;
                    a[row][col].l = 0;
                    a[row][col].b = 0;
                    a[row][col].r = 0;
                    p = arow[k]; //current
                    q = acol[k];
                }
            }
        }
        sch = true;
    }
    /**
     * a simple function to find
     * the address of the BT tag
     *
     * @param s
     */

    public void findadd(String s) {
        someLabel:
        for (srow = 0; srow <= 8; srow++) {
            for (scol = 0; scol <= 13; scol++) {
                if (keys[srow][scol].equals(s)) {
                    break someLabel;
                }
            }
        }
    }


    /**
     * A function to update the direction
     * to take at every node w.r.t the frame of reference
     * of the current node.
     *
     * @param a direction array to be updated
     * @param i the index of the element in the
     *          direction array
     */
    public void changeDir(String[] a, int i) {
        switch (a[i]) {
            case "R":
                for (int j = i; a[j] != null; j++) {
                    switch (a[j]) {
                        case "F":
                            a[j] = "L";
                            break;
                        case "B":
                            a[j] = "R";
                            break;
                        case "R":
                            a[j] = "F";
                            break;
                        case "L":
                            a[j] = "B";
                            break;
                    }
                }
                break;
            case "L":
                for (int j = i; a[j] != null; j++) {
                    switch (a[j]) {
                        case "F":
                            a[j] = "R";
                            break;
                        case "B":
                            a[j] = "L";
                            break;
                        case "R":
                            a[j] = "B";
                            break;
                        case "L":
                            a[j] = "F";
                            break;
                    }
                }
                break;
            case "B":
                for (int j = i; a[j] != null; j++) {
                    switch (a[j]) {
                        case "F":
                            a[j] = "B";
                            break;
                        case "B":
                            a[j] = "F";
                            break;
                        case "R":
                            a[j] = "L";
                            break;
                        case "L":
                            a[j] = "R";
                            break;
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * function to calculate the final
     * values of the elements in
     *
     * @var string array direction
     */


    public void fixDir() {
        for (int i = 0; i < dir.length; i++) {
            if(i == 0)
            {
                if (dir[i] != "F") {
                    changeDir(dir, i);
                }
            }
            if (dir[i] != null) {
                if ((dir[i].equals("F"))) {
                    continue;
                } else {
                    if ((dir[i].equals(dir[i + 1]))) {
                        changeDir(dir, (i + 1));
                    }
                }
            }
            else
            {
                break;
            }
        }
    }


    /**
     * onCreate function to contain the nethods
     * to execute as soon as the app is launched
     *
     * @param savedInstanceState
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    /**
     * A BroadCastReciever object to perform following tasks
     * 1.Identify the source tag
     * 2.Keep seaching for
     * the BT tags in background and
     * 3.Keep the track of the user's position
     */

    BroadcastReceiver myReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context p1, Intent p2) {
            String action = p2.getAction();
            // tasks to perform when a new BT tag is found
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = p2.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String address = device.getAddress();

                // for identifying source
                if (!broad) {


                    for (i = 0; i <= 8; i++) {
                        for (j = 0; j <= 13; j++) {
                            if (uuid[i][j] != null) {
                                if (uuid[i][j].equals(address)) {

                                    source = keys[i][j];
                                    showToast("Source Detected " + "\n Calculating Path");
                                    assignAdd();
                                    if (asn) {
                                        search(source, destination);
                                        broad = true;

                                    }
                                }
                            }
                            if (broad) {
                                break;
                            }
                        }
                        if (broad) {
                            break;
                        }
                    }

                }

                //for helping the user in transversal
                else {

                    if (address.equals(bt_add[r])) {
                         if ((Math.abs(p2.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)) <= 88) ||  (bt_add[r] == "2C:00:00:01:AA:BE" && Math.abs(p2.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)) <= 96 && path[r-1] != "Girls Common Room")) {
                            if (r < k) {
                                if (path[r] == (path[r + 1])) {
                                    if (r != 0) {
                                        if (dir[r] != "Forward") {
                                            showToast("You are at " + path[r] + "\n   Move   " + dir[r] + "\n towards   " + path[r + 2]);
                                            r += 2;
                                        } else if (dir[r + 1] != "Forward") {
                                            r += 1;
                                            showToast("You are at " + path[r] + "\n   Move   " + dir[r] + "\ntowards   " + path[r + 1]);
                                            r += 1;
                                        } else if (dir[r] == "Forward" && dir[r + 1] == "Forward") {
                                            showToast("You are at " + path[r] + " \n  Move   " + dir[r] + " \ntowards   " + path[r + 2]);
                                            r += 2;
                                        }
                                    } else {
                                        showToast("You are at " + path[r] + "\n   Move   " + dir[r] + " \ntowards   " + path[r + 2]);
                                        r += 2;
                                    }
                                } else {
                                    showToast("You are at " + path[r] + "\n   Move   " + dir[r] + " \ntowards   " + path[r + 1]);
                                    r += 1;
                                }


                            } else {
                                showToast(" you have reached " + path[k]);
                                showToast(" you have reached " + path[k]);
                                showToast(" you have reached " + path[k]);
                                r += 1;

                            }
                        }


                    }
                }
            }
            // for keeping the bluetooth in permanent ON state
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

                bt.startDiscovery();


            }
        }
    };


    @Override
    public void onBackPressed(){

        if (exit) {
            bt.cancelDiscovery();
            bt.disable();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            startActivity(intent);
            finish(); // finish activity
            System.exit(0);
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(myReciever);
    }
}





