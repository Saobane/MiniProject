package fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saobane.mini_project.MainActivity;
import com.example.saobane.mini_project.R;
import com.example.saobane.mini_project.Trajet;
import com.example.saobane.mini_project.TrajetDB;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.TrafficModel;
import com.google.maps.model.TravelMode;

/**
 * Created by Saobane on 28/11/2016.
 */

public class AjoutTrajetFragment extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {

    View myView;

    TextView tvDistance,tvDuree,tvDureeTraf;

    EditText etPointDepart,etPointDarrivee,etDate,etHeure, etRetard,etContribution;
    Button enregistrer;
    CheckBox cBox;
     TrajetDB trajetsdb ;
     Trajet tr=new Trajet();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.activity_ajouttrajet,container,false);

        etPointDepart=(EditText) myView.findViewById(R.id.etPointDepar);
        etPointDarrivee=(EditText) myView.findViewById(R.id.etPointDarv);
        etDate=(EditText) myView.findViewById(R.id.etDate);
        etHeure=(EditText) myView.findViewById(R.id.etHeure);
        etRetard=(EditText) myView.findViewById(R.id.etRetard);
        etContribution=(EditText) myView.findViewById(R.id.etContribution);
        cBox=(CheckBox) myView.findViewById(R.id.cBox);
        enregistrer=(Button) myView.findViewById(R.id.bEnregistrerTrajet);
        tvDistance=(TextView)myView.findViewById(R.id.tvDistance);
        tvDuree=(TextView)myView.findViewById(R.id.tvDuree);
        tvDureeTraf=(TextView)myView.findViewById(R.id.tvDureeTraf);

        trajetsdb = new TrajetDB(this.getActivity());
        enregistrer.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bEnregistrerTrajet:



                trajetsdb.open();


                if(etPointDepart.getText().toString().isEmpty() || etPointDarrivee.getText().toString().isEmpty() || etDate.getText().toString().isEmpty()|| etHeure.getText().toString().isEmpty()
                        || etRetard.getText().toString().isEmpty() || etContribution.getText().toString().isEmpty()

                        ){

                    Toast.makeText(this.getActivity(), "Veillez entrez tout les champs", Toast.LENGTH_SHORT).show();

                }else{
                    tr.setDepart(etPointDepart.getText().toString());
                    tr.setArrivee(etPointDarrivee.getText().toString());
                    tr.setDate(etDate.getText().toString());
                    tr.setHeure(etHeure.getText().toString());
                    tr.setRetard(etRetard.getText().toString());
                    tr.setContribution(etContribution.getText().toString());

                        if(cBox.isChecked())
                        {
                            tr.setAuto(1);

                        }else{

                            tr.setAuto(0);

                        }


                         tr.setIdUser(MainActivity.id_user);




                    GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBI5CwQvkWyhNE4TuNSlXBffG6Uqr4wN2k");

                    DistanceMatrix matrix = null,matrixT;
                    try {
                        matrix = DistanceMatrixApi.newRequest(context)
                                .origins(etPointDepart.getText().toString())
                                .destinations(etPointDarrivee.getText().toString())
                                .language("fr-FR")
                                .mode(TravelMode.DRIVING)
                                //.trafficModel(TrafficModel.PESSIMISTIC)
                                .await();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    tvDistance.setText("Las distance en Km est : "+matrix.rows[0].elements[0].distance.toString());
                    tvDuree.setText("La durée du trajet est : "+matrix.rows[0].elements[0].duration.toString());

                    tvDureeTraf.setText("On a un bon trafic aujourd'hui, pas de durée supplémentaire à prévoir");


                    AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                    builder.setTitle("Alerte de confirmation");
                    builder.setMessage("Voulez vous vraiment ajouter le trajet ?");
                    builder.setPositiveButton("Yes",this);

                    builder.setNegativeButton("Non",null);


                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();



                }











                trajetsdb.close();
                break;

            case R.id.btnAnnulerTrajet:
              //  startActivity(new Intent(this.getActivity(),MainActivity.class));
                etPointDepart.setText("");
                etPointDarrivee.setText("");
                etDate.setText("");
                etHeure.setText("");
                etRetard.setText("");
                etContribution.setText("");
                cBox.setChecked(false);
                tvDistance.setText("");
                tvDuree.setText("");

                break;
        }
        
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {

        trajetsdb.open();
        trajetsdb.ajouterTrajet(tr);
        Toast.makeText(this.getActivity(), "Trajet ajouté", Toast.LENGTH_SHORT).show();
        etPointDepart.setText("");
        etPointDarrivee.setText("");
        etDate.setText("");
        etHeure.setText("");
        etRetard.setText("");
        etContribution.setText("");
        cBox.setChecked(false);
        tvDistance.setText("");
        tvDuree.setText("");

        tvDureeTraf.setText("");

        tvDureeTraf.setText("On a un bon trafic aujourd'hui, pas de durée supplémentaire à prévoir");
        trajetsdb.close();


    }
}
