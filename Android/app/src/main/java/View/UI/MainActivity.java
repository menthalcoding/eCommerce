package View.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.northwindsampledb.R;

public class MainActivity extends Activity {

    ListView listView;
    private String[] items = { "mcs_Address","mcs_Cart","mcs_CartItem","mcs_Category","mcs_Order","mcs_Payment","mcs_Product","mcs_ProductPhoto","mcs_Shipping","mcs_Stock" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.lvMenu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Class<?> classByName = null;
                try {
                    classByName = Class.forName("View.UI." + items[i].toString() + "ListActivity");
                    Intent intent = new Intent(getApplicationContext(), classByName);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
