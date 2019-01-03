package fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.saobane.mini_project.ItineMapsActivity;
import com.example.saobane.mini_project.R;
import com.example.saobane.mini_project.TrajetDB;


/**
 * Created by Saobane on 28/11/2016.
 */

public class SearchCovoitFragment extends Fragment implements View.OnClickListener {

    View myView;

    ListView searchTrajets;

    EditText departSearch,arriveeSearch,dateSearch;
    ArrayAdapter<String> adapter;
    Button valider;
    TrajetDB myDb;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.searchcovoit_layout,container,false);
         myDb=new TrajetDB(getActivity());


        departSearch=(EditText) myView.findViewById(R.id.etPointDeparSearch);
        arriveeSearch=(EditText) myView.findViewById(R.id.etPointDarvSearch);
        dateSearch=(EditText) myView.findViewById(R.id.etDateSearch);
        searchTrajets=(ListView) myView.findViewById(R.id.lvTrajetsSearch);
        valider=(Button) myView.findViewById(R.id.bValiderRecherche);


        valider.setOnClickListener(this);

        searchTrajets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=adapter.getItem(position);

              Intent intent=new Intent(getActivity(),ItineMapsActivity.class);




                intent.putExtra("depart",departSearch.getText().toString());
                intent.putExtra("arrivee",arriveeSearch.getText().toString());
                startActivity(intent);
            }
        });






        return myView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){


        case R.id.bValiderRecherche:


            if(departSearch.getText().toString().isEmpty() || arriveeSearch.getText().toString().isEmpty() || dateSearch.getText().toString().isEmpty()) {


            }
            else
            {
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myDb.getAllTrajetsSeach(departSearch.getText().toString(), arriveeSearch.getText().toString(), dateSearch.getText().toString()));
                searchTrajets.setAdapter(adapter);
            }


        break;
        }
    }
}
