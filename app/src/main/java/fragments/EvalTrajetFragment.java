package fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.saobane.mini_project.MainActivity;
import com.example.saobane.mini_project.R;
import com.example.saobane.mini_project.TrajetDB;
import com.example.saobane.mini_project.UtilisateurDB;

import static android.R.attr.name;

/**
 * Created by Saobane on 11/12/2016.
 */

public class EvalTrajetFragment extends Fragment implements AdapterView.OnItemClickListener {

    View myView;
    ArrayAdapter<String> adapter;
    TrajetDB myDb;
    Dialog rankDialog;
    RatingBar ratingBar;
    UtilisateurDB uDb;

    ListView lvEvalTrajets;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.eval_trajet_layout,container,false);
        lvEvalTrajets=(ListView) myView.findViewById(R.id.lvTrajetsEval);
        myDb=new TrajetDB(getActivity());

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, myDb.getAllTrajets());
        lvEvalTrajets.setAdapter(adapter);
        uDb= new UtilisateurDB(getActivity());

        lvEvalTrajets.setOnItemClickListener(this);



        return myView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

uDb.open();
         rankDialog = new Dialog(getActivity(), R.style.FullHeightDialog);
        rankDialog.setContentView(R.layout.rank_dialog);
        rankDialog.setCancelable(true);
        ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
        ratingBar.setRating(2);

        TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
        text.setText(adapter.getItem(position));

        Button evaluerButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
        evaluerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float f=ratingBar.getRating();
                int id=MainActivity.id_user;
                udateScor(id,f);
                rankDialog.dismiss();
                uDb.close();
            }
        });
        //now that the dialog is set up, it's time to show it

        rankDialog.show();
    }

    public void  udateScor(int id,float f){

        uDb.updateScore(id,f);
    }
}
