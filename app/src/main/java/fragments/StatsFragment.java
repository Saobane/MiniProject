package fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.saobane.mini_project.MainActivity;
import com.example.saobane.mini_project.R;
import com.example.saobane.mini_project.TrajetDB;
import com.example.saobane.mini_project.UtilisateurDB;

import java.util.ArrayList;


/**
 * Created by Saobane on 11/12/2016.
 */

public class StatsFragment extends Fragment{
    View myView;

    TrajetDB myDb;
    UtilisateurDB uDb;

    TextView tvNmbrTrj;
    TextView tvScore;
    TextView tvMontant;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.stats_layout,container,false);

tvNmbrTrj=(TextView) myView.findViewById(R.id.tvNombreTrajet);
        tvScore=(TextView) myView.findViewById(R.id.tvScoreS);
        tvMontant=(TextView) myView.findViewById(R.id.tvMontant);
        myDb = new TrajetDB(this.getActivity());

        int id=MainActivity.id_user;
        myDb.open();

        ArrayList<String> nbrTrj=myDb.getAllTrajets(id);
        ArrayList<String> nbrTrjAll=myDb.getAllTrajets();

       //double score=Double.parseDouble(MainActivity.score)/nbrTrjAll.size();

      tvNmbrTrj.setText(""+nbrTrj.size());
        tvScore.setText(MainActivity.scoreF);
tvMontant.setText(""+myDb.getMontant(id)+" euros ");



myDb.close();
        return myView;
    }
}
