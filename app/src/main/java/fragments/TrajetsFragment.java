package fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.saobane.mini_project.MainActivity;
import com.example.saobane.mini_project.R;
import com.example.saobane.mini_project.TrajetDB;

import static com.example.saobane.mini_project.Utils.getImage;


/**
 * Created by Saobane on 28/11/2016.
 */

public class TrajetsFragment extends Fragment {

    View myView;

    ListView trajetsList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.fragment_trajets,container,false);
        TrajetDB myDb=new TrajetDB(getActivity());

        trajetsList=(ListView) myView.findViewById(R.id.lvTrajets);

        int user_id=MainActivity.id_user;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myDb.getAllTrajets(user_id));
        trajetsList.setAdapter(adapter);

        return myView;
    }


}
